# EthernetManager



EthernetManager 是一个设置静态IP和DHCP的一个service，其中对外提供了intent接口便于用户开发



#### EthernetManagerService 接口说明

```java
/**
 * ethernet manager
 */
private static final String ACTION_STATIC = "com.advantech.aim75.STATIC";	// 设置静态IP
private static final String ACTION_DHCP = "com.advantech.aim75.DHCP";		// 切换DHCP

/**
 * result key
 */
private static final String RESULT_ETHERNET_MANAGER = "result_ethernet_manager";	// 将设置静态IP和DHCP后的result返回给调用端

/**
 * action
 */
private static final String ACTION_RESULT = "com.advantech.aim75.result";	// action result
```



#### 使用说明

```java
// 在使用时需要发送广播并且携带IP信息
// 设置静态IP
Intent intent = new Intent();
intent.setAction("com.advantech.aim75.STATIC");
intent.putExtra("ipAddress", IPAddressText.getText().toString());
intent.putExtra("netmask", MaskText.getText().toString());
intent.putExtra("gateway", GatewayText.getText().toString());
intent.putExtra("dns1", DNS1Text.getText().toString());
intent.putExtra("dns2", DNS2Text.getText().toString());
sendBroadcast(intent);

// 设置DHCP
Intent intent = new Intent();
intent.setAction("com.advantech.aim75.DHCP");
sendBroadcast(intent);

// 如果需要监听设置是否成功，则需要创建广播接受器，并且注册 "com.advantech.aim75.result" action

IntentFilter filter = new IntentFilter();
filter.addAction("com.advantech.aim75.result");
registerReceiver(broadcastReceiver, filter);

private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "action : " + action);
        boolean result = false;
        if ("com.advantech.aim75.result".equals(action)) {
            boolean result = intent.getBooleanExtra("result_ethernet_manager", false);
            Log.d(TAG, "result : " + result);
        }
    }
};
```



#### ScannerWedgeSample LAN Module



如果要使用LAN模块则需要先安装EthernetManager_v1.0.1_release.apk，然后使用如下命令启动service

```bash
adb shell am startservice -n com.advantech.ethernetmanager/com.advantech.ethernetmanager.EthernetManagerService
```

