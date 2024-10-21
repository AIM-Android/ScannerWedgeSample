package com.advantech.scannerwedgedemo.module;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.advantech.scannerwedgedemo.baseui.BaseActivity;
import com.advantech.scannerwedgedemo.R;

public class UhfRfidActivity extends BaseActivity {

    private static final String TAG = "UhfRfidActivity";

    private static final String ACTION_SCAN = "com.advantech.uhf.rfid.NFC_SCAN";
    private static final String ACTION_TRANSFER_DATA = "com.advantech.uhf.rfid.TRANSFER_DATA";

    private TextView textView;
    private EditText editText;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_uhf_rfid;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        startService();
        Button trigger = findViewById(R.id.rfid_btn_trigger);
        trigger.setOnClickListener(v -> {
            Intent intent = new Intent(ACTION_SCAN);
            intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(intent);
        });

        editText = findViewById(R.id.rfid_edt);

        textView = findViewById(R.id.rfid_textview);
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

    private void startService() {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.advantech.peripheralmanager", "com.advantech.peripheralmanager.service.FT230XService");
        intent.setComponent(componentName);
        startService(intent);
    }

    private void stopService() {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.advantech.peripheralmanager", "com.advantech.peripheralmanager.service.FT230XService");
        intent.setComponent(componentName);
        stopService(intent);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String barcodeData = intent.getStringExtra("scan_data");
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
        stopService();
    }
}