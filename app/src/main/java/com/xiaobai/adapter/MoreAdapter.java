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
import android.widget.TextView;

import com.xiaobai.application.R;
import com.xiaobai.dto.MoreDto;

import java.util.List;
import java.util.Random;

/**
 * Created by Wangc on 2016/5/25
 * E-MAIL:274281610@QQ.COM
 */
public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mDatas;
    private LayoutInflater mInflater;
    String[] colors = new String[]{"#a04940", "#ee8d7b", "#7065a3", "#85916d", "#c1d8ac", "#8c8684", "#c1d8ac",
            "#c18dac", "#93b69c", "#c85179", "#9dc357", "#FFE4B5", "#7fcce3", "#73b8e2", "#cbb994", "#5b7e91"};

    public MoreAdapter(Context Context, List<String> Datas) {
        this.mContext = Context;
        this.mDatas = Datas;
        mInflater = LayoutInflater.from(Context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_more, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View view) {
            super(view);

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
            tv = (TextView) view.findViewById(R.id.more_name);
            view.setBackgroundDrawable(drawable);
        }
    }
}
