[English](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/README.md)

该项目是为ScannerWedge App的使用而写的一个范例代码，主要在于指导开发者来如何在自己的app中触发ScannerWedge App进行一次条形码扫描以及如何接收来自于ScannerWedge App的条形码扫描结果

# 用法
## 1. 安装ScannerWedge App
ScannerWedge App将多种硬件厂商提供的原始的条形码扫描仪SDK集成到了一起。你只需要安装并运行该app就可以很轻松的实现多种条形码扫描仪模块的扫描功能。

## 2. 运行ScannerWedge App并进行设置
### 2.1 选择模块
ScannerWedge App目前支持NewLand EM 2096和Honeywell N3680两种Barcode模块以后还会支持更多模块。请在App Scanner页面的Scanner下拉菜单来选择对应的Barcode模块
![](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/images/scanner.png)

### 2.2 选择输出方式
ScannerWedge App对扫描到的条形码数据有键盘方式和广播方式两种输出方式，用户可以根据自己应用场景和需求做出选择。请在App Advanced页面的Output method下拉菜单来切换数据输出模式。SannerWedgeSample App是通过广播接收数据的，所以这里需要选择为广播输出模式
![](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/images/output_method.png)

###  2.3 确认并设置配置
请点击ScannerWedge App页面的“确认”按键将配置信息设置下去
![](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/images/confirm.png)

## 3. 安装并运行ScannerWedgeSample App
安装并且运行ScannerWedgeSample App，当点击“Trigger once Scan“按键时，就开始扫描，当扫描条形码成功后就可以在页面看到扫描到的条形码数据

# 其他
有关ScannerWedge App的更多资料请参考:  
[ScannerWedge App使用说明](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/doc/ScannerWedge_quick_start_guide_v1.0.pdf)  
[ScannerWedge发布包下载](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/release/v1.0/ScannerWedge_20220316_V1.0.7z)
