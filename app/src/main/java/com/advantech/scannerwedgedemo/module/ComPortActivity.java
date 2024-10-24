package com.advantech.scannerwedgedemo.module;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.advantech.peripheralmanager.IComPortManager;
import com.advantech.scannerwedgedemo.baseui.BaseActivity;
import com.advantech.scannerwedgedemo.R;
import com.advantech.scannerwedgedemo.utils.SerialPortFinder;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ComPortActivity extends BaseActivity {

    private static final String TAG = "ComPortActivity";

    private ReadingThread thread;

    private Button controlButton;
    private EditText comDataEdt;
    private TextView receiveTv;

    private String[] baudrateArray;
    private String[] portArray;
    private String mBaudrate;
    private String mPort;

    private boolean isOpen;
    private boolean isConnected;

    private IComPortManager mService;
    private Spinner portSpinner;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            isConnected = true;
            mService = IComPortManager.Stub.asInterface(iBinder);
            initData();
            controlButton.setEnabled(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isConnected = false;
            mService = null;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.advantech.peripheralmanager",
                "com.advantech.peripheralmanager.ComPortManagerService"));
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isConnected = false;
        unbindService(serviceConnection);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_com_port;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        baudrateArray = getResources().getStringArray(R.array.com_baudrate);
        Spinner baudrateSpinner = findViewById(R.id.baudrate_sp);
        baudrateSpinner.setSelection(0);
        baudrateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBaudrate = baudrateArray[position];
                Log.d(TAG, "baudrate : " + mBaudrate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        portSpinner = findViewById(R.id.port_sp);
        receiveTv = findViewById(R.id.receive_tv);
        comDataEdt = findViewById(R.id.com_data_edt);
        controlButton = findViewById(R.id.control_btn);
        controlButton.setEnabled(false);
        controlButton.setOnClickListener(v -> {
            if ("open".equals(controlButton.getText())) {
                try {
                    if (mService.open(mPort, Integer.parseInt(mBaudrate)) == 0) {
                        showToast("the serial port successfully opened");
                        controlButton.setText("close");
                        baudrateSpinner.setEnabled(false);
                        portSpinner.setEnabled(false);
                        isOpen = true;
                        startReceivedThread();
                    } else {
                        showToast("the serial port opening failed");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    mService.close();
                    controlButton.setText("open");
                    comDataEdt.setText("");
                    receiveTv.setText("");
                    showToast("the serial port successfully closed");
                    baudrateSpinner.setEnabled(true);
                    portSpinner.setEnabled(true);
                    isOpen = false;
                    if (thread != null) {
                        thread.interrupt();
                        thread = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Button send = findViewById(R.id.send_btn);
        send.setOnClickListener(v -> {
            sendData();
        });
        Button clear = findViewById(R.id.clear_btn);
        clear.setOnClickListener(v -> {
            receiveTv.setText("");
        });
    }

    @Override
    protected void initData() {
        List<String> serialPorts =  new ArrayList<>();
        try {
            serialPorts = mService.listPorts();
        } catch (Exception e) {
            e.printStackTrace();
        }

        portArray = serialPorts.toArray(new String[serialPorts.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, portArray);
        portSpinner.setAdapter(adapter);
        portSpinner.setSelection(0);
        portSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPort = portArray[position];
                Log.d(TAG, "port : " + mPort);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void sendData() {
        if (!isOpen) {
            showToast("please open this serial port");
            return;
        }
        if (TextUtils.isEmpty(comDataEdt.getText())) {
            showToast("the data is empty.");
            return;
        }
        if (!comDataEdt.getText().toString().toUpperCase().matches("[0-9a-zA-Z]+")) {
            showToast("DATA FORMAT ERROR.");
            return;
        }

        String data = comDataEdt.getText().toString();
        final char[] text = new char[data.length()];
        for (int index = 0; index < data.length(); index++) {
            text[index] = data.charAt(index);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mService.write(new String(text).getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void startReceivedThread() {
        if (thread == null) {
            thread = new ReadingThread();
        }
        if (!thread.isAlive()) {
            thread.start();
        }
    }

    private class ReadingThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted() && isOpen) {
                try {
                    byte[] buffer = new byte[64];
                    buffer = mService.read();
                    if (buffer != null || buffer.length > 0) {
                        onDataReceived(buffer, buffer.length);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onDataReceived(byte[] buffer, int size) {
        Log.d(TAG, "size : " + buffer.length);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String data = new String(buffer, 0, size);
                receiveTv.append(data);
            }
        });
    }

    @Override
    protected void onDestroy() {
        try {
            if (mService != null) {
                mService.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}