package com.advantech.scannerwedgedemo.module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.advantech.scannerwedgedemo.baseui.BaseActivity;
import com.advantech.scannerwedgedemo.R;

public class BarcodeScannerActivity extends BaseActivity {

    private static final String TAG = "BarcodeScannerActivity";

    private EditText editText;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_barcode_scanner;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        TextView content = findViewById(R.id.content);
        content.setText("-> Barcode Demo");
        editText = findViewById(R.id.barcode_edt);

        Button clear = findViewById(R.id.clear_btn);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}