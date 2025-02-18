package com.advantech.scannerwedgedemo.module;

import android.os.Bundle;
import android.widget.TextView;

import com.advantech.scannerwedgedemo.R;
import com.advantech.scannerwedgedemo.baseui.BaseActivity;

public class LightBarActivity extends BaseActivity {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_light_bar;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        TextView content = findViewById(R.id.content);
        content.setText("-> LightBar Demo");
    }

    @Override
    protected void initData() {

    }
}
