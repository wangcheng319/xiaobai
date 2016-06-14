package com.xiaobai.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.xiaobai.application.R;
import com.xiaobai.dto.FindDetailsDto;
import com.xiaobai.dto.HtoDto;
import com.xiaobai.utils.Utils;

import java.util.List;

/**
 * Created by wangc on 2016/6/8.
 */
public class FindDetailsAdapter extends BaseAdapter {
    private List<String> datas;
    private Context context;
    private LayoutInflater inflater;

    public FindDetailsAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
        this.inflater = LayoutInflater.from(context);
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

        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_find_details_item, null);
            holder = new ViewHolder();
            holder.photo = (ImageView) convertView.findViewById(R.id.find_detals_photo);
            holder.cardView = (CardView) convertView.findViewById(R.id.cardview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String url = datas.get(position);

        return convertView;

    }

    class ViewHolder {
        public ImageView photo;
        public CardView cardView;
    }
}
