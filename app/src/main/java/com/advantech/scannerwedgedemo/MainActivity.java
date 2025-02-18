package com.advantech.scannerwedgedemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

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

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        GridView gridView = findViewById(R.id.gridview);
        MainPageAdapter adapter = new MainPageAdapter(this, MainPageData.getConfigDataList());
        adapter.setListener(listener);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

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