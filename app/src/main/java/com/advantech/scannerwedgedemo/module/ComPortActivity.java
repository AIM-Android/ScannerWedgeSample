package com.advantech.scannerwedgedemo.module;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

public class ComPortActivity extends BaseActivity {

    private static final String TAG = "ComPortActivity";

    private static final int BAUDRATE = 115200;
    private static final String PORT = "/dev/ttyMSM1";

    private FileDescriptor fd;

    private InputStream mInputStream;
    private OutputStream mOutputStream;

    private ReadingThread thread;

    private Button controlButton;
    private EditText comDataEdt;
    private TextView receiveTv;

    private String[] baudrateArray;
    private String[] portArray;
    private String mBaudrate;
    private String mPort;

    private boolean isOpen;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_com_port;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        baudrateArray = getResources().getStringArray(R.array.com_baudrate);
//        portArray = getResources().getStringArray(R.array.com_interface);

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
        Spinner portSpinner = findViewById(R.id.port_sp);
        SerialPortFinder finder = new SerialPortFinder();
        portArray = finder.getAllDevicesPath();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, portArray);
        portSpinner.setAdapter(adapter);
        portSpinner.setSelection(0);
        portSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mPort = "/dev/" + portArray[position];
                mPort = portArray[position];
                Log.d(TAG, "port : " + mPort);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        receiveTv = findViewById(R.id.receive_tv);

        comDataEdt = findViewById(R.id.com_data_edt);
        controlButton = findViewById(R.id.control_btn);
        controlButton.setOnClickListener(v -> {
            if ("open".equals(controlButton.getText())) {
                if (openFileDescriptor()) {
                    controlButton.setText("close");
                    baudrateSpinner.setEnabled(false);
                    portSpinner.setEnabled(false);
                    mInputStream = new FileInputStream(fd);
                    mOutputStream = new FileOutputStream(fd);
                    isOpen = true;
                    startReceivedThread();
                }
            } else {
                closeFileDescriptor();
                baudrateSpinner.setEnabled(true);
                portSpinner.setEnabled(true);
                isOpen = false;
                if (thread != null) {
                    thread.interrupt();
                    thread = null;
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
                    mOutputStream.write(new String(text).getBytes());
                } catch (IOException e) {
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
                    if (mInputStream == null) {
                        return;
                    }
                    int size = mInputStream.read(buffer);
                    if (size > 0) {
                        onDataReceived(buffer, size);
                    }
                } catch (IOException e) {
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

    private boolean openFileDescriptor() {
        if (fd != null && fd.valid()) {
            showToast("the serial port has been opened.");
            return true;
        }

        FileDescriptor fileDescriptor = open(new File(mPort).getAbsolutePath(), Integer.parseInt(mBaudrate), 0);
        if (fileDescriptor != null && fileDescriptor.valid()) {
            fd = fileDescriptor;
            showToast("the serial port successfully opened");
            return true;
        } else {
            showToast("the serial port opening failed");
            return false;
        }
    }

    private void closeFileDescriptor() {
        if (fd != null && fd.valid()) {
            close(fd);
            fd = null;
            controlButton.setText("open");
            comDataEdt.setText("");
            receiveTv.setText("");
            showToast("the serial port successfully closed");
        }
    }

    @Override
    protected void onDestroy() {
        closeFileDescriptor();
        super.onDestroy();
    }

    private native FileDescriptor open(String path, int baudrate, int flags);

    private native void close(FileDescriptor fd);

    static {
        System.loadLibrary("ComPort");
    }
}