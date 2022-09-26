[简体中文](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/README_ZH.md)

This project is a sample code written for the use of the ScannerWedge App, mainly to guide developers on how to trigger the ScannerWedge App in their own app to conduct a barcode scanning and how to receive barcode scanning results from the ScannerWedge App 

# usage
## 1. Install ScannerWedge App
ScannerWedge app integrates the original barcode SDK provided by various hardware manufacturers. You only need to install and run the app to easily realize the scanning function of multiple barcode modules.

## 2. Run the ScannerWedge App and set it up 
### 2.1  Select Module
ScannerWedge app currently supports Newland EM 2096 and Honeywell n3680 barcode modules, and will support more modules in the future. Please switch the barcode module through the scanner drop-down menu on the app scanner page
![](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/images/scanner.png)

### 2.2 Select Output Mode
ScannerWedge app has two output methods for the scanned barcode data. Users can choose according to their own application scenarios and needs. Please switch the data output mode through the output method drop-down menu on the app advanced page.  The SannerWedgeSample App receives data through broadcast, so you need to select the broadcast output mode.
![](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/images/output_method.png)

### 2.3  Confirm and set the configuration 
Please click the "Confirm" button on the ScannerWedge App page to set the configuration information 
![](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/images/confirm.png)

## 3. Install and run the ScannerWedgeSample App
Install and run the ScannerWedgeSample App. When you click the "Trigger once Scan" button, scanning starts. After scanning the barcode successfully, you can see the scanned barcode data on the page

# other
For more information about the ScannerWedge App, please refer to:   
[User Manual  for ScannerWedge App](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/doc/ScannerWedge_quick_start_guide_v1.0.pdf)  
[ScannerWedge release package download](https://github.com/AIM-Android/ScannerWedgeSample/blob/master/release/v1.0/ScannerWedge_20220316_V1.0.7z)
