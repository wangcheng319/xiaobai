package com.xiaobai.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
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
import com.xiaobai.dto.HtoDto;
import com.xiaobai.dto.MoreDetailsDto;
import com.xiaobai.dto.MoreDto;
import com.xiaobai.utils.Utils;

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

        final ViewHolder holder;
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

        final MoreDetailsDto item = mDatas.get(position);

        holder.name.setText(item.NickName);
        holder.time.setText(item.CreateTime);


        RequestQueue mQueue = Volley.newRequestQueue(mContext);
        ImageRequest imageRequest = new ImageRequest(
                item.urls.get(0),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
//                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(imageRequest);
        return convertView;

    }

    class ViewHolder {
        public TextView time;
        public TextView name;
        public ImageView photo;
    }
}
