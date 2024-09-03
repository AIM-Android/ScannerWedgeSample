package com.advantech.scannerwedgedemo.module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.advantech.scannerwedgedemo.baseui.BaseActivity;
import com.advantech.scannerwedgedemo.R;

public class BarcodeScannerActivity extends BaseActivity {

    private static final String TAG = "BarcodeScannerActivity";

    private static final String ACTION_TRANSFER_DATA = "com.advantech.scannerwedge.TRANSFER_DATA";
    private static final String ACTION_TRIGGER_SCAN = "com.advantech.scannerwedge.TRIGGER_SCAN";

    private TextView textView;
    private EditText editText;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_barcode_scanner;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Button trigger = findViewById(R.id.bar_btn_trigger);
        trigger.setOnClickListener(v -> {
            Intent intent = new Intent(ACTION_TRIGGER_SCAN);
            intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(intent);
        });

        editText = findViewById(R.id.barcode_edt);

        textView = findViewById(R.id.bar_textview);
        textView.setOnLongClickListener(v -> {
            showToast("clear success");
            textView.setText("");
            editText.setText("");
            return true;
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_TRANSFER_DATA);
        registerReceiver(broadcastReceiver, filter);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String barcodeData = intent.getStringExtra("barcode_data");
            Log.d(TAG, "barcodeData : " + barcodeData);
            if (barcodeData != null) {
                textView.append(barcodeData + "\n");
            }
        }
    };

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}