package com.xiaobai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaobai.application.R;
import com.xiaobai.dto.HtoDto;
import com.xiaobai.imagepicker.ImageItem;
import com.xiaobai.utils.Utils;

import java.util.List;

/**
 * Created by wangc on 2016/6/7.
 */
public class UpLoadAdapter extends BaseAdapter {
    private List<ImageItem> datas;
    private Context mContext;
    private LayoutInflater inflate;


    public UpLoadAdapter(Context mContext, List<ImageItem> datas) {
        this.mContext = mContext;
        this.datas = datas;
        inflate = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (datas == null || datas.get(position) == null) {
            return convertView;
        }

        ViewHolder holder;
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.layout_upload_item, null);
            holder = new ViewHolder();
            holder.photo = (ImageView) convertView.findViewById(R.id.gridview_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageItem item = datas.get(position);

        Glide.with(mContext)
                .load(item.path)
                .placeholder(Utils.makeDrable())
                .crossFade()
                .centerCrop()
                .into(holder.photo);

        return convertView;

    }

    class ViewHolder {
        public ImageView photo;
    }
}
