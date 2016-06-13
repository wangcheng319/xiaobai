package com.xiaobai.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.xiaobai.adapter.MineAdapter;
import com.xiaobai.app.App;
import com.xiaobai.application.R;
import com.xiaobai.dto.HtoDto;
import com.xiaobai.dto.MineDto;
import com.xiaobai.view.MyListView;

import java.util.ArrayList;
import java.util.List;


/**
 * 我的
 */
public class MineFragment extends BaseFragment {
    private View rootView;
    private PullToZoomScrollViewEx scrollView;
    private View headView;
    private View zoomView;
    private View contentView;

    private MyListView listview;
    private int pageNo = 1;
    private int pageSize = 20;
    private MineAdapter mineAdapter;
    private List<MineDto> datas = new ArrayList<MineDto>();


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
        getPublishDynamic();
        getMyFollow();
        getFollowMe();

        // 注意初始化顺序，不要弄乱，否则抛出运行时空指针
        PullToZoomScrollViewEx scrollView = (PullToZoomScrollViewEx) rootView.findViewById(R.id.mine_scroll);
        loadViewForPullToZoomScrollView(scrollView);
        setPullToZoomViewLayoutParams(scrollView);
    }


    /**
     * 获取关注我的
     */
    private void getFollowMe() {
        RequestBody formBody = new FormEncodingBuilder()
                .add("CmdId", "queryUserFollowers ")
                .add("Goal", "user")
                .add("Version", "01")
                .build();
        onRequest(101, url, formBody, App.token);

    }

    /**
     * 获取我关注的
     */
    private void getMyFollow() {
        RequestBody formBody = new FormEncodingBuilder()
                .add("CmdId", "queryUserLeaders ")
                .add("Goal", "user")
                .add("Version", "01")
                .build();
        onRequest(102, url, formBody, App.token);

    }

    /**
     * 获取发布的动态
     */
    private void getPublishDynamic() {

        RequestBody formBody = new FormEncodingBuilder()
                .add("CmdId", "queryUserRecord ")
                .add("Goal", "record")
                .add("c_pageCount", pageSize + "")
                .add("c_currentPage", pageNo + "")
                .add("Version", "01")
                .build();
        onRequest(103, url, formBody, App.token);

    }

    private void loadViewForPullToZoomScrollView(PullToZoomScrollViewEx scrollView) {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.view_mine_zoomview, null);
        zoomView = LayoutInflater.from(getActivity()).inflate(R.layout.view_mine_zoomview, null);
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.view_mine_content, null);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);


        mineAdapter = new MineAdapter(getActivity(), datas);
        listview = (MyListView) contentView.findViewById(R.id.mine_list);
//        listview.setAdapter(mineAdapter);

        List<String> da = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            da.add("第" + i + "");
        }
        ArrayAdapter<String> myArrayAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, da);
        listview.setAdapter(myArrayAdapter);
    }

    // 设置头部的View的宽高。
    private void setPullToZoomViewLayoutParams(PullToZoomScrollViewEx scrollView) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth,
                (int) (9.0F * (mScreenWidth / 16.0F)));
        scrollView.setHeaderLayoutParams(localObject);
    }

    @Override
    public void onPostSuccess(int postId, String msg) {
        switch (postId) {
            case 101:
                Log.d("101", msg);
                break;
            case 102:
                Log.d("102", msg);
                break;
            case 103:
                Log.d("103", msg);
//                Gson gson = new Gson();
//                java.lang.reflect.Type type2 = new TypeToken<List<MineDto>>() {
//                }.getType();
//                datas = gson.fromJson(msg, type2);
//                mineAdapter.notifyDataSetChanged();
                break;
        }

    }

    @Override
    public void onPostFailure(int postId, String msg) {

    }

}
