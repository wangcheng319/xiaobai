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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaobai.application.R;
import com.xiaobai.dto.HtoDto;
import com.xiaobai.dto.MoreDetailsDto;
import com.xiaobai.dto.MoreDto;

import java.util.List;
import java.util.Random;

/**
 * Created by Wangc on 2016/6/5
 * E-MAIL:274281610@QQ.COM
 */
public class MoreDetailsAdapter extends BaseAdapter {
    private Context mContext;
    private List<MoreDetailsDto> mDatas;
    private LayoutInflater mInflater;
    String[] colors = new String[]{"#a04940", "#ee8d7b", "#7065a3", "#85916d", "#c1d8ac", "#8c8684", "#c1d8ac",
            "#c18dac", "#93b69c", "#c85179", "#9dc357", "#FFE4B5", "#7fcce3", "#73b8e2", "#cbb994", "#5b7e91"};

    public MoreDetailsAdapter(Context context, List<MoreDetailsDto> datas) {
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
            convertView = mInflater.inflate(R.layout.layout_more_details_item, null);
            holder = new ViewHolder();
            holder.time = (TextView) convertView.findViewById(R.id.more_details_item_time);
            holder.name = (TextView) convertView.findViewById(R.id.more_details_item_name);
            holder.photo = (ImageView) convertView.findViewById(R.id.more_details_item_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MoreDetailsDto item = mDatas.get(position);

        holder.name.setText(item.NickName);
        holder.time.setText(item.CreateTime);
        //生成一个随机色块
        final int random = new Random().nextInt(16);
        Drawable drawable = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                canvas.drawColor(Color.parseColor(colors[random]));
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };
        Glide.with(mContext)
                .load(item.urls.get(0))
                .placeholder(drawable)
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
