package com.advantech.scannerwedgedemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class IpGetUtil {
    private static final String TAG = "IpGetUtil";

    //获取以太网的IP地址
    public static String getIpAddress(Context context) {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Network network = mConnectivityManager.getActiveNetwork();
            LinkProperties linkProperties = mConnectivityManager.getLinkProperties(network);
            for (LinkAddress linkAddress : linkProperties.getLinkAddresses()) {
                InetAddress address = linkAddress.getAddress();
                if (address instanceof Inet4Address) {
                    return address.getHostAddress();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getIpAddress error" + e.getMessage(), e);
        }
        // IPv6 address will not be shown like WifiInfo internally does.
        return "";
    }
}
