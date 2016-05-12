package com.xiaobai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaobai.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Wangc on 2016/5/11.
 */
public class FindAdapter extends RecyclerView.Adapter<FindAdapter.MyViewHolder> implements View.OnClickListener{
    private Context mContext;
    private List<String> mDatas;
    private int pos ;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public static interface  OnRecyclerViewItemClickListener{
        void  onItemClick(View view,String data);
    }

    public void  setmOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnItemClickListener = listener;
    }


    public FindAdapter(Context context, List<String> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
    }



    @Override
    public FindAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_find_item, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(FindAdapter.MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position));
        holder.itemView.setTag(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onClick(View v) {
        mOnItemClickListener.onItemClick(v, (String) v.getTag());
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.find_item_name);
        }
    }
}
