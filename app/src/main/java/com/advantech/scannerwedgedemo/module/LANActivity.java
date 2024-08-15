package com.advantech.scannerwedgedemo.module;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.advantech.scannerwedgedemo.R;
import com.advantech.scannerwedgedemo.baseui.BaseActivity;

public class LANActivity extends BaseActivity {

    private static final String TAG = "LANActivity";

    private String[] ethArray;

    private String mEthIndex;

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

            }
        });
        Button dhcpCheckButton = findViewById(R.id.dhcp_btn);
        dhcpCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected void initData() {

    }
}
