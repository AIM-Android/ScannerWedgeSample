package com.advantech.scannerwedgedemo.module;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.advantech.scannerwedgedemo.R;
import com.advantech.scannerwedgedemo.baseui.BaseActivity;

/**
 * time   : 2024/10/15
 * desc   : MSR
 * version: 1.0
 */
public class MSRActivity extends BaseActivity {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_msr;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        TextView content = findViewById(R.id.content);
        content.setText("-> MSR Demo");
        EditText msrEdt = findViewById(R.id.msr_edt);
        Button clear = findViewById(R.id.clear_btn);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msrEdt.setText("");
            }
        });
    }

    @Override
    protected void initData() {

    }
}
