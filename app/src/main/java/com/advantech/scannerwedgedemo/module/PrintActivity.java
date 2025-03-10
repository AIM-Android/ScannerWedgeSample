package com.advantech.scannerwedgedemo.module;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText stringEdt, barCodeEdt, alignEdt, sizeEdt, zoomEdt, positionEdt;
    private Gson gson;

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
        barCodeEdt = findViewById(R.id.barcode_edt);
        alignEdt = findViewById(R.id.align_edt);
        sizeEdt = findViewById(R.id.charsize_edt);
        zoomEdt = findViewById(R.id.setzoom_edt);
        positionEdt = findViewById(R.id.position_edt);

        zoomEdt.setEnabled(false);
        positionEdt.setEnabled(false);
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

            if (TextUtils.isEmpty(alignEdt.getText())) {
                showToast("The alignment method cannot be empty.");
                return;
            }

            if (TextUtils.isEmpty(sizeEdt.getText())) {
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
        } else {
            Log.e(TAG, "onclick error.");
        }
    }

    private void print() {
        TextData textData = new TextData();
        TextData.TextTitle textTitle = new TextData.TextTitle();
        textTitle.setData(stringEdt.getText().toString());
        textTitle.setAlign(Integer.parseInt(alignEdt.getText().toString()));
        textTitle.setFont(Integer.parseInt(sizeEdt.getText().toString()));
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
