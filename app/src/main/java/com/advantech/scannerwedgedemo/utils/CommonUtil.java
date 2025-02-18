package com.advantech.scannerwedgedemo.utils;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import java.lang.reflect.Method;

public class CommonUtil {

    private static final String TAG = CommonUtil.class.getSimpleName();

    public static String getProperty(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, defaultValue));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return value;
        }
    }

    public static boolean getProperty(String key, boolean defaultValue) {
        boolean value = defaultValue;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("getBoolean", String.class, boolean.class);
            value = (boolean) (get.invoke(c, key, defaultValue));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return value;
        }
    }

    public static void reboot(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            powerManager.reboot(null);
        } else {
            Log.e(TAG, "The POWER_SERVICE get error.");
        }
    }
}
