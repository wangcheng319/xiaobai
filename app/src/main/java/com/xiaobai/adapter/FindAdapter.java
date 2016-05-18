package com.xiaobai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xiaobai.dto.HtoDto;
import com.xiaobai.myapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wangc on 2016/5/11.
 */
public class FindAdapter extends RecyclerView.Adapter<FindAdapter.MyViewHolder>  {
    private Context mContext;
    private List<HtoDto> mDatas;
    private int pos ;


    public FindAdapter(Context context, List<HtoDto> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
    }



    @Override
    public FindAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_find_item, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FindAdapter.MyViewHolder holder, int position) {
        HtoDto item = mDatas.get(position);
        holder.tv_name.setText(item.NickName);
        holder.tv_time.setText(item.CreateTime);
        Glide.with(mContext).load(item.urls.get(0)).into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_time;
        TextView tv_name;
        ImageView photo;

        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.find_item_name);
            tv_time = (TextView) view.findViewById(R.id.find_item_time);
            photo = (ImageView) view.findViewById(R.id.find_item_photo);
        }
    }
}
