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
import com.xiaobai.dto.RankDto;
import com.xiaobai.utils.Utils;

import java.util.List;
import java.util.Random;

/**
 * Created by Wangc on 2016/6/4
 * E-MAIL:274281610@QQ.COM
 */
public class RankAdapter extends BaseAdapter {
    private List<RankDto> datas;
    private Context mContext;
    private LayoutInflater inflater;

    public RankAdapter(Context mContext, List<RankDto> datas) {
        this.mContext = mContext;
        this.datas = datas;
        inflater = LayoutInflater.from(mContext);
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
            convertView = inflater.inflate(R.layout.item_rank, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.rank_item_name);
            holder.time = (TextView) convertView.findViewById(R.id.rank_item_time);
            holder.good = (TextView) convertView.findViewById(R.id.rank_item_good);

            holder.photo = (ImageView) convertView.findViewById(R.id.rank_item_photo);
            holder.image1 = (ImageView) convertView.findViewById(R.id.rank_item_image1);
            holder.image2 = (ImageView) convertView.findViewById(R.id.rank_item_image2);
            holder.image3 = (ImageView) convertView.findViewById(R.id.rank_item_image3);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.image1.setBackgroundDrawable(Utils.makeDrable());
        holder.image2.setBackgroundDrawable(Utils.makeDrable());
        holder.image3.setBackgroundDrawable(Utils.makeDrable());

        RankDto item = (RankDto) getItem(position);

        holder.name.setText(item.NickName);
        holder.time.setText(item.CreateTime);
        holder.good.setText(item.likeCount + "");


        Glide.with(mContext)
                .load(item.avatar)
                .crossFade()
                .centerCrop()
                .into(holder.photo);

        if (item.urls != null && item.urls.size() == 1) {
            Glide.with(mContext)
                    .load(item.urls.get(0))
                    .placeholder(Utils.makeDrable())
                    .crossFade()
                    .centerCrop()
                    .into(holder.image1);

        } else if (item.urls != null && item.urls.size() == 2) {
            Glide.with(mContext)
                    .load(item.urls.get(0))
                    .placeholder(Utils.makeDrable())
                    .crossFade()
                    .centerCrop()
                    .into(holder.image1);

            Glide.with(mContext)
                    .load(item.urls.get(1))
                    .placeholder(Utils.makeDrable())
                    .crossFade()
                    .centerCrop()
                    .into(holder.image2);

        } else if (item.urls != null && item.urls.size() == 3) {
            Glide.with(mContext)
                    .load(item.urls.get(0))
                    .placeholder(Utils.makeDrable())
                    .crossFade()
                    .centerCrop()
                    .into(holder.image1);

            Glide.with(mContext)
                    .load(item.urls.get(1))
                    .placeholder(Utils.makeDrable())
                    .crossFade()
                    .centerCrop()
                    .into(holder.image2);
            Glide.with(mContext)
                    .load(item.urls.get(2))
                    .placeholder(Utils.makeDrable())
                    .crossFade()
                    .centerCrop()
                    .into(holder.image3);

        }
        return convertView;
    }

    class ViewHolder {
        public TextView name;
        public TextView time;
        public TextView good;

        public ImageView photo;
        public ImageView image1;
        public ImageView image2;
        public ImageView image3;

    }
}
