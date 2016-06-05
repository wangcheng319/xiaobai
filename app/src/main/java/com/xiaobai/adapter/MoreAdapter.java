package com.xiaobai.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaobai.application.R;
import com.xiaobai.dto.MoreDto;

import java.util.List;
import java.util.Random;

/**
 * Created by Wangc on 2016/5/25
 * E-MAIL:274281610@QQ.COM
 */
public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<MoreDto> mDatas;
    private LayoutInflater mInflater;
    String[] colors = new String[]{"#a04940", "#ee8d7b", "#7065a3", "#85916d", "#c1d8ac", "#8c8684", "#c1d8ac",
            "#c18dac", "#93b69c", "#c85179", "#9dc357", "#FFE4B5", "#7fcce3", "#73b8e2", "#cbb994", "#5b7e91"};

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (MoreDto)v.getTag());
        }
    }

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, MoreDto data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public MoreAdapter(Context Context, List<MoreDto> Datas) {
        this.mContext = Context;
        this.mDatas = Datas;
        mInflater = LayoutInflater.from(Context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                mContext).inflate(R.layout.item_more, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MoreDto item = mDatas.get(position);
        holder.tv_name.setText(item.Name);

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
                .load(item.CatgroyImage)
                .placeholder(drawable)
                .crossFade()
                .centerCrop()
                .into(holder.imageView);

        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.more_name);
            imageView = (ImageView) view.findViewById(R.id.more_image);
        }
    }
}
