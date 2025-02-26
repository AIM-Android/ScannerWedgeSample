package com.advantech.scannerwedgedemo.module;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.advantech.scannerwedgedemo.R;
import com.advantech.scannerwedgedemo.adapter.BlockAdapter;
import com.advantech.scannerwedgedemo.baseui.BaseActivity;
import com.advantech.scannerwedgedemo.utils.M1CardUtil;
import com.advantech.scannerwedgedemo.utils.StringUtil;

import java.io.IOException;
import java.util.ArrayList;

public class NFCActivity extends BaseActivity {

    private static final String TAG = "NFCActivity";

    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;

    private TextView SNTextView, ATQATextView, SAKTextView, MAXTransceiveLengthTextView, supportArgumentTextView;
    private ListView listView;

    private BlockAdapter adapter;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_nfc;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        TextView content = findViewById(R.id.content);
        content.setText("-> NFC Demo");
        SNTextView = findViewById(R.id.sn_tv);
        ATQATextView = findViewById(R.id.atqa_tv);
        SAKTextView = findViewById(R.id.sak_tv);
        MAXTransceiveLengthTextView = findViewById(R.id.len_tv);
        supportArgumentTextView = findViewById(R.id.techlist_tv);

        listView = findViewById(R.id.listview);

        Button clear = findViewById(R.id.clear_btn);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SNTextView.setText("");
                ATQATextView.setText("");
                SAKTextView.setText("");
                MAXTransceiveLengthTextView.setText("");
                supportArgumentTextView.setText("");
                if (adapter != null) {
                    adapter.setDataList(null);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void initData() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            showToast("Not support NFC.");
            return;
        }
        Intent intent = new Intent(this, getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mPendingIntent = getPendingIntentInstance(intent);

        IntentFilter filter = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);

        mFilters = new IntentFilter[] {filter};
    }

    private PendingIntent getPendingIntentInstance(Intent intent) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
        } else {
            return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleNfcIntent(intent);
    }

    private void handleNfcIntent(Intent intent) {
        String action = intent.getAction();
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        NfcA nfcA = NfcA.get(tag);
        SNTextView.setText("SN : " + StringUtil.bytes2HexStr(tag.getId()).replaceAll("(.{2})(?!$)", "$1:"));
        ATQATextView.setText("ATQA : " + "0x" + StringUtil.bytes2HexStr(nfcA.getAtqa()));
        SAKTextView.setText("SAK : " + "0x" + String.format("%02X", nfcA.getSak()));
        MAXTransceiveLengthTextView.setText("MAXTransceiveLength : " + nfcA.getMaxTransceiveLength());
        supportArgumentTextView.setText("Support argument\n" + tag);
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            try {
                processTag(tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void processTag(Tag tag) {
        MifareClassic mifareClassic = MifareClassic.get(tag);
        if (mifareClassic == null) {
            showToast("Not Mifare card.");
            return;
        }
        ArrayList<String> result = null;
        try {
//            Log.d(TAG, "size : " + M1CardUtil.readBlock(mifareClassic, null).size());
            result = M1CardUtil.readBlock(mifareClassic);
            Log.d(TAG, "result : " + result);
            update(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void update(ArrayList<String> result) {
        if (result == null || result.size() == 0) {
            return;
        }
        adapter.setDataList(result);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null) {
            if (!mNfcAdapter.isEnabled()) {
                showToast("Please enable the NFC function first in the system settings.");
                return;
            }
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
