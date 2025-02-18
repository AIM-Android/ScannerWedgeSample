package com.advantech.scannerwedgedemo.bean;

import android.widget.ImageView;
import android.widget.TextView;

import com.advantech.scannerwedgedemo.R;

import java.util.ArrayList;
import java.util.List;

public class MainPageData {

    public static final String MSR = "MSR";
    public static final String BARCODE = "Barcode";
    public static final String RFID = "RFID";
    public static final String NFC = "NFC";
    public static final String COM = "COM";
    public static final String CAMERA = "Camera";
    public static final String USB = "USB";
    public static final String ETHERNET = "Ethernet";
    public static final String PRINTER = "Printer";
    public static final String CASHDRAWER = "Cashdrawer";
    public static final String LIGHT_BAR = "Light Bar";
    public static final String GPIO = "GPIO";

    private int drawableId;
    private String title;

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MainPageData(int drawableId, String title) {
        this.drawableId = drawableId;
        this.title = title;
    }

    public static List<MainPageData> getConfigDataList() {
        List<MainPageData> dataList = new ArrayList<>();
        dataList.add(new MainPageData(R.drawable.msr, MSR));
        dataList.add(new MainPageData(R.drawable.barcode, BARCODE));
        dataList.add(new MainPageData(R.drawable.rfid, RFID));
        dataList.add(new MainPageData(R.drawable.nfc, NFC));
        dataList.add(new MainPageData(R.drawable.com, COM));
        dataList.add(new MainPageData(R.drawable.camera, CAMERA));
        dataList.add(new MainPageData(R.drawable.usb, USB));
        dataList.add(new MainPageData(R.drawable.ethernet, ETHERNET));
        dataList.add(new MainPageData(R.drawable.printer, PRINTER));
        dataList.add(new MainPageData(R.drawable.cashdrawer, CASHDRAWER));
        dataList.add(new MainPageData(R.drawable.light_bar, LIGHT_BAR));
        dataList.add(new MainPageData(R.drawable.gpio, GPIO));
        return dataList;
    }
}