package com.advantech.scannerwedgedemo.module;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.advantech.scannerwedgedemo.R;
import com.advantech.scannerwedgedemo.baseui.BaseActivity;
import com.advantech.scannerwedgedemo.module.receiver.NetworkStateReceiver;
import com.advantech.scannerwedgedemo.utils.IpGetUtil;

public class LANActivity extends BaseActivity {

    private static final String TAG = "LANActivity";

    private String[] ethArray;

    private String mEthIndex;

    private NetworkStateReceiver networkStateReceiver;

    private EditText IPAddressText, MaskText, GatewayText, DNS1Text, DNS2Text;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_lan;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        TextView content = findViewById(R.id.content);
        content.setText("-> Ethernet Demo");
        startService();
        ethArray = getResources().getStringArray(R.array.ethernet_interface);
        Spinner ethernetEthSpinner = findViewById(R.id.ethernet_sp);
        ethernetEthSpinner.setSelection(0);
        ethernetEthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mEthIndex = ethArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        IPAddressText = findViewById(R.id.ip);
        MaskText = findViewById(R.id.mask);
        GatewayText = findViewById(R.id.gateway);
        DNS1Text = findViewById(R.id.dns1);
        DNS2Text = findViewById(R.id.dns2);
        Button setStaticIpButton = findViewById(R.id.set_btn);
        setStaticIpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStaticIp();
            }
        });
        Button dhcpCheckButton = findViewById(R.id.dhcp_btn);
        dhcpCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDhcpIp();
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        networkStateReceiver = new NetworkStateReceiver();
        registerReceiver(networkStateReceiver, filter);

        networkStateReceiver.setNetworkStateListener(new NetworkStateReceiver.NetworkStateListener() {
            @Override
            public void getNetworkState(int state) {
                String ip = IpGetUtil.getIpAddress(LANActivity.this);
                showToast(state > NetworkStateReceiver.NETSTATUS_INAVAILABLE
                        ? getString(R.string.ip_address, ip)
                        : getString(R.string.ip_address, "没有网络"));
            }
        });
    }

    private void startService() {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.advantech.peripheralmanager", "com.advantech.peripheralmanager.service.EthernetManagerService");
        intent.setComponent(componentName);
        startService(intent);
    }

    private void stopService() {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.advantech.peripheralmanager", "com.advantech.peripheralmanager.service.EthernetManagerService");
        intent.setComponent(componentName);
        stopService(intent);
    }

    private void setStaticIp() {
        if (TextUtils.isEmpty(IPAddressText.getText())
                || TextUtils.isEmpty(MaskText.getText())
                || TextUtils.isEmpty(GatewayText.getText())
                || TextUtils.isEmpty(DNS1Text.getText())
                || TextUtils.isEmpty(DNS2Text.getText())) {
            showToast("IP address info is null.");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.advantech.aim75.STATIC");
        intent.putExtra("ipAddress", IPAddressText.getText().toString());
        intent.putExtra("netmask", MaskText.getText().toString());
        intent.putExtra("gateway", GatewayText.getText().toString());
        intent.putExtra("dns1", DNS1Text.getText().toString());
        intent.putExtra("dns2", DNS2Text.getText().toString());
        sendBroadcast(intent);
    }

    private void setDhcpIp() {
        Intent intent = new Intent();
        intent.setAction("com.advantech.aim75.DHCP");
        sendBroadcast(intent);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "unregisterReceiver");
        unregisterReceiver(networkStateReceiver);
        stopService();
    }
}
