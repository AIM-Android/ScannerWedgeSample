package com.advantech.scannerwedgedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.advantech.scannerwedgedemo.R;

import java.util.List;

public class USBDeviceAdapter extends BaseAdapter {

    private final Context context;
    private List<String> dataList;

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public USBDeviceAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList == null ? null : dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_usb_device, null);
            holder = new ViewHolder();
            holder.usbItem = convertView.findViewById(R.id.usb_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String bean = dataList.get(position);
        if (bean != null) {
            holder.usbItem.setText(bean);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView usbItem;
    }
}
