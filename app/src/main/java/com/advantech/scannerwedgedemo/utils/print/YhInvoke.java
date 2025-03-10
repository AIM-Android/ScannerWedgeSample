package com.advantech.scannerwedgedemo.utils.print;

import android.app.Activity;
import android.view.Gravity;

import com.advantech.scannerwedgedemo.utils.ToastUtil;

import org.json.JSONArray;

import java.util.Map;

import print.Print;


public class YhInvoke {
    public static final String TAG = "YhInvoke";
    private Activity activity = null;
    private static YhPrintMain print;
    private static BTCallback btCallback;

    public static boolean isPrintConnected() {
        return print != null && print.isPrintConnected();
    }

    public static void setBtCallback(BTCallback btCallback) {
        YhInvoke.btCallback = btCallback;
    }

    public static boolean execute(Activity activity, String action, Map<String,String> map) {
        if (print == null) {
            print = new YhPrintMain();
        }

        try {
            String method = map.get("method");
            switch (method) {
                case "testPrint":
                    if (!print.isPrintConnected()) {
                        if (print.connectBluetoothOnAppStart(activity)) {
                            if (btCallback != null) {
                                btCallback.printConnected();
                            }
                        }
                    } else {
                        Print.BeepBuzzer((byte)1,(byte)10,(byte)1);
                        if (btCallback != null) {
                            btCallback.printConnected();
                        }
                        ToastUtil.show(activity, "Bluetooth connected.", Gravity.BOTTOM, 0);
                    }
                    break;
                case "print":
                    JSONArray printDataArray = new JSONArray(map.get("params"));
                    if (!print.isPrintConnected()) {
                        print.connectBluetooth(activity);
                    }
                    if (print.isPrintConnected()) {
                        boolean printResult = print.printBill(activity, printDataArray);
                        if (printResult) {
                            ToastUtil.show(activity, "Printing successful.", Gravity.BOTTOM, 0);
                        } else {
                            ToastUtil.show(activity, "Printing failed.", Gravity.BOTTOM, 0);
                        }
                    } else {
                        ToastUtil.show(activity, "Printer connection failed.", Gravity.BOTTOM, 0);
                    }
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public interface BTCallback {
        void printConnected();
    }
}