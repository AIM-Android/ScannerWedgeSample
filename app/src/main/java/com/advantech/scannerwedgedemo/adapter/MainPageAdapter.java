package com.advantech.scannerwedgedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.advantech.scannerwedgedemo.R;
import com.advantech.scannerwedgedemo.bean.MainPageData;
import com.advantech.scannerwedgedemo.utils.CommonUtil;

import java.util.List;

public class MainPageAdapter extends BaseAdapter {

    private final Context context;
    private List<MainPageData> dataList;
    private OnClickListener listener;

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setDataList(List<MainPageData> dataList) {
        this.dataList = dataList;
    }

    public MainPageAdapter(Context context, List<MainPageData> dataList) {
        this.context = context;
        this.dataList = dataList;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_main, null);
            holder = new ViewHolder();
            holder.layout = convertView.findViewById(R.id.item_main_layout);
            holder.imageResource = convertView.findViewById(R.id.image_resource);
            holder.title = convertView.findViewById(R.id.title_string);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MainPageData bean = dataList.get(position);
        if (bean != null) {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClick(bean.getTitle());
                    }
                }
            });
            if (CommonUtil.getProperty("ro.product.model", "").contains("AIM")) {
                if (MainPageData.CASHDRAWER.equals(bean.getTitle())
                        || MainPageData.LIGHT_BAR.equals(bean.getTitle())
                        || MainPageData.GPIO.equals(bean.getTitle())) {
                    holder.layout.setEnabled(false);
                    holder.layout.setBackground(context.getDrawable(R.drawable.disable_background));
                }
                if (!CommonUtil.getProperty("ro.product.model", "").contains("AIM-37PLUS")
                        && MainPageData.PRINTER.equals(bean.getTitle())) {
                    holder.layout.setEnabled(false);
                    holder.layout.setBackground(context.getDrawable(R.drawable.disable_background));
                }
            }
            holder.imageResource.setImageResource(bean.getDrawableId());
            holder.title.setText(bean.getTitle());
        }
        return convertView;
    }

    private static class ViewHolder {
        LinearLayout layout;
        ImageView imageResource;
        TextView title;
    }
    public interface OnClickListener {
        void onClick(String tag);
    }
}
