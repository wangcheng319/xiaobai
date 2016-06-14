//package com.xiaobai.adapter;
//
//import android.support.v4.view.PagerAdapter;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
//import com.xiaobai.application.R;
//
//import java.util.List;
//
///**
// * Created by wangc on 2016/6/7.
// */
//public class PhotoEditAdapter extends PagerAdapter {
//    private List<ImageView> mListViews;
//
//    public PhotoEditAdapter(List<ImageView> mListViews) {
//        this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(mListViews.get(position));//删除页卡
//    }
//
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
//        container.addView(mListViews.get(position), 0);//添加页卡
//        ImageView imageView = mListViews.get(position);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        return mListViews.get(position);
//    }
//
//    @Override
//    public int getCount() {
//        return mListViews.size();//返回页卡的数量
//    }
//
//    @Override
//    public boolean isViewFromObject(View arg0, Object arg1) {
//        return arg0 == arg1;//官方提示这样写
//    }
//}
