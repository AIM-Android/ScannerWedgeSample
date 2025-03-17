package com.advantech.scannerwedgedemo.module;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.advantech.scannerwedgedemo.R;
import com.advantech.scannerwedgedemo.baseui.BaseActivity;
import com.advantech.scannerwedgedemo.bean.QrcodeData;
import com.advantech.scannerwedgedemo.bean.TextData;
import com.advantech.scannerwedgedemo.utils.FileUtil;
import com.advantech.scannerwedgedemo.utils.print.YhInvoke;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

import print.Print;

/**
 * time   : 2024/10/15
 * desc   : Print
 * version: 1.0
 */
public class PrintActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = PrintActivity.class.getSimpleName();

    private static final int PICK_IMAGE_REQUEST = 200;

    private EditText stringEdt, barCodeEdt;
    private Spinner alignSp, sizeSp, zoomSp, positionSp;

    private Align[] alignArray;
    private Size[] sizeArray;

    private String mAlign, mSize;

    private Gson gson;

    public enum Align {
        Left(0, "left"),
        Center(1, "center"),
        Right(2, "right");

        private final int key;
        private final String value;

        Align(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    };

    public enum Size {
        None(1, "none"),
        DoubleWidth(2, "double width"),
        DoubleHeight(3, "double height"),
        DoubleXY(4, "double width and height");

        private final int key;
        private final String value;

        Size(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    };

    public enum Zoom {
        SetZoom_0(0, "Set zoom 0"),
        SetZoom_1(1, "Set zoom 1"),
        SetZoom_2(2, "Set zoom 2");

        private final int key;
        private final String value;

        Zoom(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    };

    public enum Position {
        SetPosition_0(0, "Set position 0"),
        SetPosition_1(1, "Set position 1"),
        SetPosition_2(2, "Set position 2");

        private final int key;
        private final String value;

        Position(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    };

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_print;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        TextView content = findViewById(R.id.content);
        content.setText("-> Print Demo");

        Button clear = findViewById(R.id.clear_btn);
        clear.setOnClickListener(this);

        Button printButton = findViewById(R.id.print);
        printButton.setOnClickListener(this);

        stringEdt = findViewById(R.id.stringdata_edt);
        stringEdt.setText("Advantech");
        barCodeEdt = findViewById(R.id.barcode_edt);

        alignSp = findViewById(R.id.align_sp);
        alignArray = Align.values();
        ArrayAdapter<Align> alignAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, alignArray);
        alignSp.setAdapter(alignAdapter);
        alignSp.setSelection(0);
        alignSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mAlign = String.valueOf(alignArray[position].getKey());
                Log.d(TAG, "align : " + mAlign);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sizeSp = findViewById(R.id.charsize_sp);
        sizeArray = Size.values();
        ArrayAdapter<Size> sizeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, sizeArray);
        sizeSp.setAdapter(sizeAdapter);
        sizeSp.setSelection(0);
        sizeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSize = String.valueOf(sizeArray[position].getKey());
                Log.d(TAG, "size : " + mSize);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        zoomSp = findViewById(R.id.setzoom_sp);
        zoomSp.setBackgroundColor(Color.parseColor(getString(R.color.color_D7D6D6)));
        ArrayAdapter<Zoom> zoomAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Zoom.values());
        zoomSp.setAdapter(zoomAdapter);
        zoomSp.setSelection(1);
        zoomSp.setEnabled(false);

        positionSp = findViewById(R.id.position_sp);
        positionSp.setBackgroundColor(Color.parseColor(getString(R.color.color_D7D6D6)));
        ArrayAdapter<Position> positionAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Position.values());
        positionSp.setAdapter(positionAdapter);
        positionSp.setSelection(0);
        positionSp.setEnabled(false);

        barCodeEdt.setText("https://www.advantech.com");

        Button cutPaper = findViewById(R.id.cut_pager_btn);
        cutPaper.setOnClickListener(this);

        if (!YhInvoke.isPrintConnected()) {
            connectBT();
        }

        gson = new GsonBuilder().create();
    }

    private void connectBT() {
        try {
            Map<String,String> map=new HashMap<>();
            map.put("method","testPrint");
            YhInvoke.execute(this,"", map);
        } catch (Exception e) {
            Log.e(TAG, "connectBT : " + e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        if (R.id.print == view.getId()) {
            if (TextUtils.isEmpty(stringEdt.getText())) {
                showToast("Text input cannot be empty.");
                return;
            }

            if (TextUtils.isEmpty(barCodeEdt.getText())) {
                showToast("The QR code barcode cannot be empty.");
                return;
            }

            if (TextUtils.isEmpty(mAlign)) {
                showToast("The alignment method cannot be empty.");
                return;
            }

            if (TextUtils.isEmpty(mSize)) {
                showToast("Font size cannot be empty.");
                return;
            }
            print();
        } else if (R.id.cut_pager_btn == view.getId()) {
            try {
                Print.CutPaper(1, 10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (R.id.clear_btn == view.getId()) {
            stringEdt.setText("");
            barCodeEdt.setText("");
        } else {
            Log.e(TAG, "onclick error.");
        }
    }

    private void print() {
        TextData textData = new TextData();
        TextData.TextTitle textTitle = new TextData.TextTitle();
        textTitle.setData(stringEdt.getText().toString());
        textTitle.setAlign(Integer.parseInt(mAlign));
        textTitle.setFont(Integer.parseInt(mSize));
        textData.setTxt(textTitle);

        QrcodeData qrcodeData = new QrcodeData();
        QrcodeData.Qrcode qrcode = new QrcodeData.Qrcode();
        qrcode.setData(barCodeEdt.getText().toString());
        qrcodeData.setQrCode(qrcode);

        JsonArray jsonArray = new JsonArray();
        JsonElement textElement = gson.toJsonTree(textData);
        JsonElement qrcodeElement = gson.toJsonTree(qrcodeData);

        jsonArray.add(textElement);
        jsonArray.add(qrcodeElement);
        printBT(jsonArray.toString());
    }

    @Override
    protected void initData() {

    }

    private void printBT(String data) {
        try {
            Map<String,String> map = new HashMap<>();
            map.put("method", "print");
            map.put("params", data);
            YhInvoke.execute(this, "", map);
        } catch (Exception e) {
            Log.e(TAG, "printBT : " + e.getMessage());
        }
    }
}
