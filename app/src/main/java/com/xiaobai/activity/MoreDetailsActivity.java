package com.xiaobai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.xiaobai.adapter.FindAdapter;
import com.xiaobai.adapter.MoreDetailsAdapter;
import com.xiaobai.app.App;
import com.xiaobai.application.R;
import com.xiaobai.dto.HtoDto;
import com.xiaobai.dto.MoreDetailsDto;
import com.xiaobai.dto.MoreDto;
import com.xiaobai.listview.XListView;

import java.util.ArrayList;
import java.util.List;

public class MoreDetailsActivity extends BaseListActivity {
    private MoreDto item;
    private TextView title;
    private Button title_left;


    private List<MoreDetailsDto> mDatas = new ArrayList<MoreDetailsDto>();
    private XListView listView;
    private MoreDetailsAdapter mAdapter;
    private int pageNo = 1;
    private int pageSize = 10;
    private RequestBody formBody;
    private List<MoreDetailsDto> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);
        item = (MoreDto) getIntent().getSerializableExtra("datas");
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.main_title_tv);
        title_left = (Button) findViewById(R.id.main_title_left);

        title.setText(item.Name);
        title_left.setVisibility(View.VISIBLE);


        listView = (XListView) findViewById(R.id.more_detail_list);
        listView.setPullRefreshEnable(true); // 允许下拉刷新
        listView.setPullLoadEnable(false); // 禁止加载更多
        listView.setAutoLoadEnable(false); // 禁止自动加载
        listView.setXListViewListener(this);// 加载监听

        mAdapter = new MoreDetailsAdapter(MoreDetailsActivity.this, mDatas);
        listView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageNo = 1;
        formBody = new FormEncodingBuilder()
                .add("CmdId", "queryRecordByCatgory ")
                .add("Goal", "record")
                .add("c_catgoryCode", item.Code + "")
                .add("c_pageCount", pageSize + "")
                .add("c_currentPage", pageNo + "")
                .add("Version", "01")
                .build();
        onRequest(101, url, formBody, App.token);
    }

    @Override
    public void onPostSuccess(int postId, String result) {
        super.onPostSuccess(postId, result);

        setLoadingFinish();
        Gson gson = new Gson();
        java.lang.reflect.Type type2 = new TypeToken<List<MoreDetailsDto>>() {
        }.getType();

        if (pageNo == 1) {
            mDatas.clear();
        }
        lists = gson.fromJson(result, type2);
        mDatas.addAll(lists);
        mAdapter.notifyDataSetChanged();
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
        super.onPostFailure(postId, msg);
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


    @Override
    public void onRefresh() {
        pageNo = 1;
        mDatas.clear();
        formBody = new FormEncodingBuilder()
                .add("CmdId", "queryRecordByCatgory ")
                .add("Goal", "record")
                .add("c_catgoryCode", item.Code + "")
                .add("c_pageCount", pageSize + "")
                .add("c_currentPage", pageNo + "")
                .add("Version", "01")
                .build();
        onRequest(101, url, formBody, App.token);
    }

    @Override
    public void onLoadMore() {
        formBody = new FormEncodingBuilder()
                .add("CmdId", "queryRecordByCatgory ")
                .add("Goal", "record")
                .add("c_catgoryCode", item.Code + "")
                .add("c_pageCount", pageSize + "")
                .add("c_currentPage", pageNo + "")
                .add("Version", "01")
                .build();
        onRequest(101, url, formBody, App.token);
    }
}
