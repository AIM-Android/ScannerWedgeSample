package com.advantech.scannerwedgedemo.module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.advantech.scannerwedgedemo.baseui.BaseActivity;
import com.advantech.scannerwedgedemo.R;
import com.advantech.scannerwedgedemo.adapter.USBDeviceAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class USBDeviceActivity extends BaseActivity {

    private static final String TAG = "USBDeviceActivity";

    private static final String ACTION_USB_STATE = "android.hardware.usb.action.USB_STATE";
    private static final String ACTION_USB_VOLUME_STATE_CHANGED = "android.os.storage.action.VOLUME_STATE_CHANGED";
    private static final String USB_MOUNTED = "android.os.storage.extra.VOLUME_STATE";

    private ListView listView;
    private USBDeviceAdapter adapter;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_usb_device;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        listView = findViewById(R.id.listview);

        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(ACTION_USB_STATE);
        filter.addAction(ACTION_USB_VOLUME_STATE_CHANGED);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void initData() {
        List<String> usbDeviceItemList = new ArrayList<>();
        UsbManager usbManager = (UsbManager) getSystemService("usb");
        HashMap<String, UsbDevice> hashMap = usbManager.getDeviceList();
        for (Map.Entry<String, UsbDevice> entry : hashMap.entrySet()) {
            UsbDevice usbDevice = entry.getValue();
            String name = usbDevice.getDeviceName();
            String vid = String.valueOf(usbDevice.getVendorId());
            String pid = String.valueOf(usbDevice.getProductId());
            String manufacturer = usbDevice.getManufacturerName();
            String[] parts = name.split("/");
            if (parts.length < 5) {
                Log.e(TAG, "split error");
                return;
            }
            String item = "Bus " + parts[4] + ", Device " + parts[5] + ", ID " + vid + ":" + pid + ", " + manufacturer;
            usbDeviceItemList.add(item);
        }
        if (adapter == null) {
            adapter = new USBDeviceAdapter(this);
        }
        adapter.setDataList(usbDeviceItemList);
        listView.setAdapter(adapter);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case UsbManager.ACTION_USB_DEVICE_ATTACHED:
                case UsbManager.ACTION_USB_DEVICE_DETACHED:
                case ACTION_USB_STATE:
                case ACTION_USB_VOLUME_STATE_CHANGED:
                    initData();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}