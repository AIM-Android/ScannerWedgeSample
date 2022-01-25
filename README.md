[简体中文](https://github.com/AIM-Android/ScannerWedgeSample/blob/main/README_ZH.md)

# ScannerWedge

ScannerWedge app integrates the original barcode SDK provided by various hardware manufacturers. You only need to install and run the app to easily realize the scanning function of multiple barcode modules.

## Module selection / switching
ScannerWedge app currently supports Newland EM 2096 and Honeywell n3680 barcode modules, and will support more modules in the future. You can switch the barcode module through the scanner drop-down menu on the app scanner page
![](https://github.com/AIM-Android/ScannerWedgeSample/blob/main/images/scanner.png)

## Output mode selection / switching
ScannerWedge app has two output methods for the scanned barcode data. Users can choose according to their own application scenarios and needs. You can switch the data output mode through the output method drop-down menu on the app advanced page.
![](https://github.com/AIM-Android/ScannerWedgeSample/blob/main/images/output_method.png)

### Keyboard mode
Keyboard mode is to send the scanned data to the system in the form of analog keyboard input.  In this mode, the scanned data will be directly filled into the edit box where the focus is located

### Broadcast mode
Broadcast mode is to send the scanned data in the form of broadcast. If you want to get data, you need to listen to the specified broadcast to get data.

# ScannerWedgeSample
ScannerWedgeSample is used to guide you how to receive the scanning results sent by ScannerWedge app through broadcast mode. The main codes are as follows：

## Step 1: implement the broadcast receiver and process the data

Implement a broadcast receiver to process the broadcast from "com.advantech.scannerwedge.TRANSFER_DATA". Receive additional parameters with the key value of "barcodedata" in the receiver, which is the data scanned by the barcode module. 
````java
private class BarCodeDataBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String barcodeData = intent.getStringExtra("barcodeData");
        // TODO Add logical processing data
    }
}
````

## Step 2: register the broadcast receiver

Create a broadcast receiver instance, register the broadcast receiver, and receive the broadcast from "com.advantech.scannerwedge.TRANSFER_DATA".
````java
IntentFilter filter = new IntentFilter();
filter.addAction("com.advantech.scannerwedge.TRANSFER_DATA");
BarCodeDataBroadcastReceiver barCodeDataBroadcastReceiver = new BarCodeDataBroadcastReceiver();
registerReceiver(barCodeDataBroadcastReceiver, filter);
````
Through the above two steps, you can easily receive the barcode data scanned from the ScannerWedge app.

## Stop receiving barcode data

When you don't need to receive barcode data, you just need to de register the previous broadcast receiver.
````java
unregisterReceiver(barCodeDataBroadcastReceiver);
````