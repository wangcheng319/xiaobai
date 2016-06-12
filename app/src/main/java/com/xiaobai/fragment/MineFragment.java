package com.xiaobai.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        loadViewForCode();
        scrollView = (PullToZoomScrollViewEx) rootView.findViewById(R.id.mine_scrollview);
    }

    private void loadViewForCode() {

        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.view_more_list_item, null, false);
        View zoomView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_login, null, false);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.view_title, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
    }

    @Override
    public void onPostSuccess(int postId, String msg) {

    }

    @Override
    public void onPostFailure(int postId, String msg) {

    }

}
