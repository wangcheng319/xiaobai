package com.xiaobai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaobai.dto.MoreDto;
import com.xiaobai.myapplication.R;

import java.util.List;

/**
 * Created by Wangc on 2016/5/25
 * E-MAIL:274281610@QQ.COM
 */
public class MoreAdapter extends BaseAdapter {
    private Context mContext;
    private List<MoreDto> mDatas;
    private LayoutInflater mInflater;

    public MoreAdapter(Context Context, List<MoreDto> Datas) {
        this.mContext = Context;
        this.mDatas = Datas;
        mInflater = LayoutInflater.from(Context);
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
        if (mDatas == null || getItem(position) == null) {
            return convertView;
        }

        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.view_more_list_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.more_gridview_tv);
            holder.imageView = (ImageView) convertView.findViewById(R.id.more_gridview_iv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MoreDto item = mDatas.get(position);

        return convertView;
    }


}

class ViewHolder {
    public TextView title;
    public ImageView imageView;

}
