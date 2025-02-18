package com.advantech.scannerwedgedemo.module;

import android.os.Bundle;
import android.widget.TextView;

import com.advantech.scannerwedgedemo.R;
import com.advantech.scannerwedgedemo.baseui.BaseActivity;

public class CashDrawerActivity extends BaseActivity {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_cashdrawer;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        TextView content = findViewById(R.id.content);
        content.setText("-> CashDrawer Demo");
    }

    @Override
    protected void initData() {

    }
}
