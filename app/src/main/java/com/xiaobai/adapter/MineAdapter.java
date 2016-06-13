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
import com.xiaobai.dto.MineDto;
import com.xiaobai.utils.Utils;

import java.util.List;

/**
 * Created by wangc on 2016/6/13.
 */
public class MineAdapter extends BaseAdapter {
    private Context context;
    private List<MineDto> datas;
    private LayoutInflater inflater;

    public MineAdapter(Context context, List<MineDto> datas) {
        this.context = context;
        this.datas = datas;
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
            convertView = inflater.inflate(R.layout.layout_mine_item, null);
            holder = new ViewHolder();
            holder.time = (TextView) convertView.findViewById(R.id.mine_time);
            holder.photo = (ImageView) convertView.findViewById(R.id.mine_photo);
            holder.likeCount = (TextView) convertView.findViewById(R.id.mine_good);
            holder.commentCount = (TextView) convertView.findViewById(R.id.mine_comment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MineDto item = datas.get(position);
        holder.time.setText(item.CreateTime);
        holder.likeCount.setText(item.likeCount);
        holder.commentCount.setText(item.commentCount);

        Glide.with(context)
                .load(item.url.get(0))
                .placeholder(Utils.makeDrable())
                .crossFade()
                .centerCrop()
                .into(holder.photo);
        return convertView;

    }

    class ViewHolder {
        public TextView time;
        public ImageView photo;

        public TextView likeCount;
        public TextView commentCount;
    }
}
