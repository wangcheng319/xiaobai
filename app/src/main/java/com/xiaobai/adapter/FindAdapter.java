package com.xiaobai.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.xiaobai.application.R;
import com.xiaobai.dto.HtoDto;
import com.xiaobai.utils.Utils;

import java.sql.Time;
import java.util.List;
import java.util.Random;

/**
 * Created by Wangc on 2016/5/14.
 */
public class FindAdapter extends BaseAdapter {
    private Context mContext;
    private List<HtoDto> mDatas;
    private LayoutInflater mInflater;

    public FindAdapter(Context context, List<HtoDto> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mDatas == null || mDatas.get(position) == null) {
            return convertView;
        }

        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_find_item, null);
            holder = new ViewHolder();
            holder.time = (TextView) convertView.findViewById(R.id.find_item_time);
            holder.name = (TextView) convertView.findViewById(R.id.find_item_name);
            holder.photo = (ImageView) convertView.findViewById(R.id.find_item_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HtoDto item = mDatas.get(position);

        holder.name.setText(item.NickName);
        holder.time.setText(item.CreateTime);

        Glide.with(mContext)
                .load(item.urls.get(0))
                .placeholder(Utils.makeDrable())
                .crossFade()
                .centerCrop()
                .into(holder.photo);
        return convertView;

    }

    class ViewHolder {
        public TextView time;
        public TextView name;
        public ImageView photo;
    }
}
