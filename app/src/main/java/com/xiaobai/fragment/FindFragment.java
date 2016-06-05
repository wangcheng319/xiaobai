package com.xiaobai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.xiaobai.activity.FindDetailsActivity;
import com.xiaobai.adapter.FindAdapter;
import com.xiaobai.application.R;
import com.xiaobai.dto.HtoDto;
import com.xiaobai.listview.XListView;

import java.util.ArrayList;
import java.util.List;


/**
 * 发现
 */
public class FindFragment extends BaseListFragment {


    private View rootView;
    private List<HtoDto> mDatas = new ArrayList<HtoDto>();
    private XListView listView;
    private FindAdapter findAdapter;
    private int pageNo = 1;
    private int pageSize = 10;
    private RequestBody formBody;
    private List<HtoDto> lists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_find, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNo = 1;
        formBody = new FormEncodingBuilder()
                .add("CmdId", "queryHotRecord")
                .add("Goal", "record")
                .add("c_pageCount", pageSize + "")
                .add("c_currentPage", pageNo + "")
                .add("Version", "01")
                .build();
        onRequest(101, url, formBody, "");
    }

    @Override
    public void onPostSuccess(int postId, String msg) {

        setLoadingFinish();
        Gson gson = new Gson();
        java.lang.reflect.Type type2 = new TypeToken<List<HtoDto>>() {
        }.getType();

        if (pageNo == 1) {
            mDatas.clear();
        }
        lists = gson.fromJson(msg, type2);
        mDatas.addAll(lists);
        findAdapter.notifyDataSetChanged();
        //判断是否加载下一页
        if (listView != null && (lists.size() - 1) / pageSize < pageNo) {
            listView.setPullLoadEnable(true);
            pageNo++;
        } else {
            listView.setPullLoadEnable(false);
        }
        lists.clear();
    }


    @Override
    public void onPostFailure(int postId, String msg) {
        setLoadingFinish();
        if (listView != null && (lists.size() - 1) / pageSize < pageNo) {
            listView.setPullLoadEnable(true);
            pageNo++;
        } else {
            listView.setPullLoadEnable(false);
        }

    }

    @Override
    public XListView getXList() {
        return listView;
    }

    /**
     * 加载结束
     */
    private void setLoadingFinish() {
        listView.stopLoadMore();
        listView.stopRefresh();
    }


    private void initView() {
        listView = (XListView) rootView.findViewById(R.id.find_list);
        listView.setPullRefreshEnable(true); // 允许下拉刷新
        listView.setPullLoadEnable(false); // 禁止加载更多
        listView.setAutoLoadEnable(false); // 禁止自动加载
        listView.setXListViewListener(this);// 加载监听

        findAdapter = new FindAdapter(getActivity(), mDatas);
        listView.setAdapter(findAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FindDetailsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("data", mDatas.get(position));
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        formBody = new FormEncodingBuilder()
                .add("CmdId", "queryHotRecord")
                .add("Goal", "record")
                .add("c_pageCount", pageSize + "")
                .add("c_currentPage", pageNo + "")
                .add("Version", "01")
                .build();
        onRequest(101, url, formBody, "");
    }

    @Override
    public void onLoadMore() {
        formBody = new FormEncodingBuilder()
                .add("CmdId", "queryHotRecord")
                .add("Goal", "record")
                .add("c_pageCount", pageSize + "")
                .add("c_currentPage", pageNo + "")
                .add("Version", "01")
                .build();
        onRequest(101, url, formBody, "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
