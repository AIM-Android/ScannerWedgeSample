[English](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/README.md)
# ScannerWedge

ScannerWedge App将多种硬件厂商提供的原始Barcode SDK集成到了一起。你只需要安装并运行该app就可以很轻松的实现多种Barcode模块的扫描功能。

## 模块选择/切换
ScannerWedge App目前支持NewLand EM 2096和Honeywell N3680两种Barcode模块以后还会支持更多模块。可以通过App Scanner页面的Scanner下拉菜单来切换Barcode模块
![](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/images/scanner.png)

## 输出方式选择/切换
ScannerWedge App对扫描到的条形码数据有两种输出方式，用户可以根据自己应用场景和需求做出选择。可以通过App Advanced页面的Output method下拉菜单来切换数据输出模式
![](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/images/output_method.png)

### 键盘方式
键盘模式是将扫描的数据以模拟键盘输入的形式发送到系统。在此模式下，扫描的数据将直接填充到焦点所在的编辑框中

### 广播方式
广播模式是将扫描的数据以广播的形式发送出去。如果你想得到数据，你需要监听指定广播来获取数据。

# ScannerWedgeSample
ScannerWedgeSample 用于指导你如何接收ScannerWedge App 通过广播模式发送出来的扫描结果。主要代码如下：

## 第一步：实现广播接收器，并处理数据

实现一个广播接收器，处理来自于"com.advantech.scannerwedge.TRANSFER_DATA"的广播。在接收器中接收key值为"barcodeData"的额外参数，该参数就是Barcode模块扫描到的数据。
````java
private class BarCodeDataBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String barcodeData = intent.getStringExtra("barcodeData");
        // TODO Add logical processing data
    }
}
````

## 第二步：注册广播接收器

创建广播接收器实例，并注册广播接收器，接收来自"com.advantech.scannerwedge.TRANSFER_DATA"的广播。
````java
IntentFilter filter = new IntentFilter();
filter.addAction("com.advantech.scannerwedge.TRANSFER_DATA");
BarCodeDataBroadcastReceiver barCodeDataBroadcastReceiver = new BarCodeDataBroadcastReceiver();
registerReceiver(barCodeDataBroadcastReceiver, filter);
````
通过上面两步你就可以轻松的接收到来自于ScannerWedge app扫描到的条形码数据了。

## 停止接收Barcode数据

当你不需要接收Barcode数据的时候只需要反注册掉之前的广播接收器即可
````java
unregisterReceiver(barCodeDataBroadcastReceiver);
````
