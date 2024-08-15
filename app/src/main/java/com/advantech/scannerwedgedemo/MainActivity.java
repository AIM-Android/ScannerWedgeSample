package com.advantech.scannerwedgedemo;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.advantech.scannerwedgedemo.baseui.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_settings, new SettingsFragment())
                    .commit();
        }
    }

    @Override
    protected void initData() {

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}