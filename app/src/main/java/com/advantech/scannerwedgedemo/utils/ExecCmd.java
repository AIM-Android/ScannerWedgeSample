package com.advantech.scannerwedgedemo.utils;

import android.util.Log;

import java.io.DataOutputStream;

public class ExecCmd {
    private static final String TAG = "ExecCmd";

    public static int suexecShell(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec("sh");
            DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            int ret = process.waitFor();
            Log.d(TAG, "cmd:" + cmd + "   ret : " + ret);
            return ret;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return -1;
        }
    }
}
