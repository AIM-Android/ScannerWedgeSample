package com.advantech.scannerwedgedemo.module;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.advantech.scannerwedgedemo.R;
import com.advantech.scannerwedgedemo.baseui.BaseActivity;
import com.advantech.scannerwedgedemo.module.receiver.NetworkStateReceiver;
import com.advantech.scannerwedgedemo.utils.IpGetUtil;

public class LANActivity extends BaseActivity {

    private static final String TAG = "LANActivity";

    private String[] ethArray;

    private String mEthIndex;

    private NetworkStateReceiver networkStateReceiver;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_lan;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ethArray = getResources().getStringArray(R.array.com_baudrate);
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
        EditText IPAddressText = findViewById(R.id.ip);
        EditText MaskText = findViewById(R.id.mask);
        EditText GatewayText = findViewById(R.id.gateway);
        Button setStaticIpButton = findViewById(R.id.set_btn);
        setStaticIpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IpGetUtil.setEthernetIP(LANActivity.this, "STATIC",
                        "192.168.2.168", "255.255.255.0",
                        "192.168.2.1", "4.4.4.4", "114.114.114.114");
            }
        });
        Button dhcpCheckButton = findViewById(R.id.dhcp_btn);
        dhcpCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IpGetUtil.setEthernetIP(LANActivity.this, "DHCP",
                        "", "", "", "", "");
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

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
    }
}