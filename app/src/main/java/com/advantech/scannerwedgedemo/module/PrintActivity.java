package com.advantech.scannerwedgedemo.module;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.advantech.scannerwedgedemo.R;
import com.advantech.scannerwedgedemo.baseui.BaseActivity;
import com.advantech.scannerwedgedemo.utils.print.YhInvoke;

import java.util.HashMap;
import java.util.Map;

/**
 * time   : 2024/10/15
 * desc   : Print
 * version: 1.0
 */
public class PrintActivity extends BaseActivity implements YhInvoke.BTCallback {
    private static final String TAG = PrintActivity.class.getSimpleName();

    private static final int PICK_IMAGE_REQUEST = 200;

    private ImageView selectImg;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_print;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        selectImg = findViewById(R.id.select_img);
        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        Button printButton = findViewById(R.id.print);
        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (YhInvoke.isPrintConnected()) {
                    printBT();
                }
            }
        });
        YhInvoke.setBtCallback(this);
        checkPermission();

        if (!YhInvoke.isPrintConnected()) {
            connectBT();
        } else {
//            printBT();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imgUri = data.getData();
            selectImg.setImageURI(imgUri);
        }
    }

    @Override
    protected void initData() {

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT > 30) {
            if (ContextCompat.checkSelfPermission(this,
                    "android.permission.BLUETOOTH_SCAN") != PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this,
                    "android.permission.BLUETOOTH_ADVERTISE") != PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this,
                    "android.permission.BLUETOOTH_CONNECT") != PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        "android.permission.BLUETOOTH_SCAN",
                        "android.permission.BLUETOOTH_ADVERTISE",
                        "android.permission.BLUETOOTH_CONNECT"}, 1);
            } else {
                if (!YhInvoke.isPrintConnected()) connectBT();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!YhInvoke.isPrintConnected()) connectBT();
    }

    private void connectBT() {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("method", "testPrint");
            YhInvoke.execute(this, "", map);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
    }

    private void printBT() {
        String printdata = "[" +
                "{\"img\":{\"src\":\"CRLandImposRes/printLogo/2400034001.bmp\",\"offset\":\"0\",\"width\":\"384\",\"height\":\"98\"}}," +
                "{\"txt-title\":{\"data\":\"                    sales slip\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"              [Customer Connection]\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-splitLine\":{\"data\":\"·-·-·-·-·-·-·-·-·-·-·-·-·-·-·-·-\",\"align\":\"1\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"Merchant Name:KELME\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"Merchant ID:L0406N01\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"Mall name:Advantech\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"Shopping mall number:2YRJ00201\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"Cash register number:01\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"cashier:2yrj00201l0406n0101\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"Serial number:22010001\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"Transaction Date:2023/12/22 12:00:13 \",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"Print date:2023/12/22 12:00:17\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"barCode\":{\"data\":\"0124000340231222010001\",\"align\":\"1\",\"width\":\"280\",\"height\":\"60\"}}," +
                "{\"txt-splitLine\":{\"data\":\"·-·-·-·-·-·-·-·-·-·-·-·-·-·-·-·-\",\"align\":\"1\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"Product          quantity     amount of money(RMB)\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-splitLine\":{\"data\":\"·-·-·-·-·-·-·-·-·-·-·-·-·-·-·-·-\",\"align\":\"1\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"Payment records             amount of money(RMB)\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-splitLine\":{\"data\":\"·-·-·-·-·-·-·-·-·-·-·-·-·-·-·-·-\",\"align\":\"1\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"VIP card number:138****4089\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-splitLine\":{\"data\":\"·-·-·-·-·-·-·-·-·-·-·-·-·-·-·-·-\",\"align\":\"1\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"                 Alipay sales slip\",\"align\":\"1\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"Card number:289339*** **8678\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"Authorization code:\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"Reference number:903562774242\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"Voucher number:000515\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"External order number:0054531141890160Ymc0s\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-title\":{\"data\":\"amount of money:277\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-splitLine\":{\"data\":\"·-·-·-·-·-·-·-·-·-·-·-·-·-·-·-·-\",\"align\":\"1\",\"font\":\"1\"}}," +
                "{\"qrCode\":{\"data\":\"https://www.advantech.com\",\"offset\":\"20\",\"length\":\"350\"}}," +
                "{\"txt-title\":{\"data\":\" \",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"txt-splitLine\":{\"data\":\"·-·-·-·-·-·-·-·-·-·-·-·-·-·-·-·-\",\"align\":\"1\",\"font\":\"1\"}}," +
//                "{\"txt-title\":{\"data\":\"reminder：\\nThis QR code can be used for self-service star collection on the Advantech app/Wanxiang mini program, or for collecting stars at the mall service desk with receipts (valid on the same day)\\nPlease keep this ticket as the only purchase voucher\",\"align\":\"0\",\"font\":\"1\"}}," +
                "{\"cut\":{\"data\":\"\"}}]";
        try {
            Map<String,String> map=new HashMap<>();
            map.put("method", "print");
            map.put("params", printdata);
            YhInvoke.execute(this,"", map);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void printConnected() {
//        printBT();
    }
}
