package com.advantech.scannerwedgedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.advantech.scannerwedgedemo.R;

import java.util.ArrayList;

public class BlockAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<String> dataList;

    public void setDataList(ArrayList<String> dataList) {
        this.dataList = dataList;
    }

    public BlockAdapter(Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_block, null);
            holder = new ViewHolder();
            holder.blockItem = convertView.findViewById(R.id.block_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String bean = dataList.get(position);
        if (bean != null) {
            holder.blockItem.setText(bean);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView blockItem;
    }
}
