package com.xiaobai.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.xiaobai.application.R;


/**
 * 我的
 */
public class MineFragment extends BaseFragment {
    private View rootView;
    private PullToZoomScrollViewEx scrollView;


    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        scrollView = (PullToZoomScrollViewEx) rootView.findViewById(R.id.mine_scroll);

        View zoomView = LayoutInflater.from(getActivity()).inflate(R.layout.view_mine_header, null, false);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.view_mine_content, null, false);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);

//        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
//        int mScreenHeight = localDisplayMetrics.heightPixels;
//        int mScreenWidth = localDisplayMetrics.widthPixels;
//        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
//        scrollView.setHeaderLayoutParams(localObject);

    }

    @Override
    public void onPostSuccess(int postId, String msg) {

    }

    @Override
    public void onPostFailure(int postId, String msg) {

    }

}
