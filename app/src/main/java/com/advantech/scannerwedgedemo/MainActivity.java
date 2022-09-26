package com.advantech.scannerwedgedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ScannerWedgeSample";
    private static final String ACTION_TRANSFER_DATA = "com.advantech.scannerwedge.TRANSFER_DATA";
    private static final String ACTION_TRIGGER_SCAN = "com.advantech.scannerwedge.TRIGGER_SCAN";


    private TextView textView;
    BarCodeDataBroadcastReceiver barCodeDataBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_TRANSFER_DATA);
        barCodeDataBroadcastReceiver = new BarCodeDataBroadcastReceiver();
        registerReceiver(barCodeDataBroadcastReceiver, filter);

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                textView.setText("");
                return true;
            }
        });

        Button mBtnTrigger = findViewById(R.id.btn_trigger);
        mBtnTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTION_TRIGGER_SCAN);
                intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private class BarCodeDataBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String barcodeData = intent.getStringExtra("barcode_data");
            if (barcodeData != null) {
                textView.append(barcodeData + "\n");
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(barCodeDataBroadcastReceiver);
        super.onDestroy();
    }

}
