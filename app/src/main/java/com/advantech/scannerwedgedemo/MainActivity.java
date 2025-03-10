package com.advantech.scannerwedgedemo;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.advantech.scannerwedgedemo.adapter.MainPageAdapter;
import com.advantech.scannerwedgedemo.baseui.BaseActivity;
import com.advantech.scannerwedgedemo.bean.MainPageData;
import com.advantech.scannerwedgedemo.module.BarcodeScannerActivity;
import com.advantech.scannerwedgedemo.module.CameraActivity;
import com.advantech.scannerwedgedemo.module.CashDrawerActivity;
import com.advantech.scannerwedgedemo.module.ComPortActivity;
import com.advantech.scannerwedgedemo.module.GPIOActivity;
import com.advantech.scannerwedgedemo.module.LANActivity;
import com.advantech.scannerwedgedemo.module.LightBarActivity;
import com.advantech.scannerwedgedemo.module.MSRActivity;
import com.advantech.scannerwedgedemo.module.NFCActivity;
import com.advantech.scannerwedgedemo.module.PrintActivity;
import com.advantech.scannerwedgedemo.module.USBDeviceActivity;
import com.advantech.scannerwedgedemo.module.UhfRfidActivity;
import com.advantech.scannerwedgedemo.utils.print.YhInvoke;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected int getLayoutResID() {
        return R.layout.main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        checkPermission();

        GridView gridView = findViewById(R.id.gridview);
        MainPageAdapter adapter = new MainPageAdapter(this, MainPageData.getConfigDataList());
        adapter.setListener(listener);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0) {
            if (grantResults[0] == 0 && grantResults[1] == 0 && grantResults[2] == 0) {
                if (!YhInvoke.isPrintConnected()) connectBT();
            } else {
                finish();
                Log.e(TAG, "Bluetooth permission request failed.");
            }
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT > 30) {
            if (ContextCompat.checkSelfPermission(this,
                    "android.permission.BLUETOOTH_SCAN") != PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this,
                    "android.permission.BLUETOOTH_ADVERTISE") != PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this,
                    "android.permission.BLUETOOTH_CONNECT") != PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    ||ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        "android.permission.BLUETOOTH_SCAN",
                        "android.permission.BLUETOOTH_ADVERTISE",
                        "android.permission.BLUETOOTH_CONNECT",
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                if (!YhInvoke.isPrintConnected()) connectBT();
            }
        }
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

    private MainPageAdapter.OnClickListener listener = new MainPageAdapter.OnClickListener() {
        @Override
        public void onClick(String tag) {
            switch (tag) {
                case MainPageData.MSR:
                    startActivity(new Intent(MainActivity.this, MSRActivity.class));
                    break;
                case MainPageData.BARCODE:
                    startActivity(new Intent(MainActivity.this, BarcodeScannerActivity.class));
                    break;
                case MainPageData.RFID:
                    startActivity(new Intent(MainActivity.this, UhfRfidActivity.class));
                    break;
                case MainPageData.NFC:
                    startActivity(new Intent(MainActivity.this, NFCActivity.class));
                    break;
                case MainPageData.COM:
                    startActivity(new Intent(MainActivity.this, ComPortActivity.class));
                    break;
                case MainPageData.CAMERA:
                    startActivity(new Intent(MainActivity.this, CameraActivity.class));
                    break;
                case MainPageData.USB:
                    startActivity(new Intent(MainActivity.this, USBDeviceActivity.class));
                    break;
                case MainPageData.ETHERNET:
                    startActivity(new Intent(MainActivity.this, LANActivity.class));
                    break;
                case MainPageData.PRINTER:
                    startActivity(new Intent(MainActivity.this, PrintActivity.class));
                    break;
                case MainPageData.CASHDRAWER:
                    startActivity(new Intent(MainActivity.this, CashDrawerActivity.class));
                    break;
                case MainPageData.LIGHT_BAR:
                    startActivity(new Intent(MainActivity.this, LightBarActivity.class));
                    break;
                case MainPageData.GPIO:
                    startActivity(new Intent(MainActivity.this, GPIOActivity.class));
                    break;
                default:
                    break;
            }
        }
    };
}