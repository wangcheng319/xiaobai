package com.xiaobai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaobai.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Wangc on 2016/5/11.
 */
public class FindAdapter extends RecyclerView.Adapter<FindAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mDatas;

    public FindAdapter(Context context, List<String> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public FindAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_find_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(FindAdapter.MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.find_item_name);
        }
    }
}