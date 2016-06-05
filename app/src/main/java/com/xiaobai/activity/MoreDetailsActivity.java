package com.xiaobai.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.xiaobai.adapter.MoreDetailsAdapter;
import com.xiaobai.application.R;
import com.xiaobai.dto.MoreDetailsDto;
import com.xiaobai.dto.MoreDto;
import com.xiaobai.listview.XListView;

import java.util.ArrayList;
import java.util.List;

public class MoreDetailsActivity extends BaseListActivity {
    private MoreDto item;
    private TextView title;
    private ImageView title_left;


    private List<MoreDetailsDto> mDatas = new ArrayList<MoreDetailsDto>();
    private XListView listView;
    private MoreDetailsAdapter mAdapter;
    private int pageNo = 1;
    private int pageSize = 10;
    private RequestBody formBody;
    private List<MoreDto> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);
        item = (MoreDto) getIntent().getSerializableExtra("datas");
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.main_title_tv);
        title_left = (ImageView) findViewById(R.id.main_title_left);

        title.setText(item.Name);
        title_left.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        formBody = new FormEncodingBuilder()
                .add("CmdId", "queryRecordByCatgory ")
                .add("Goal", "record")
                .add("c_catgoryCode", item.Code + "")
                .add("Version", "01")
                .build();
        onRequest(101, url, formBody, "");
    }

    @Override
    public void onPostSuccess(int postId, String result) {
        super.onPostSuccess(postId, result);
    }

    @Override
    public void onPostFailure(int postId, String msg) {
        super.onPostFailure(postId, msg);
    }
}
