package com.xiaobai.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.xiaobai.adapter.FindDetailsAdapter;
import com.xiaobai.app.App;
import com.xiaobai.application.R;
import com.xiaobai.dto.FindDetailsDto;
import com.xiaobai.dto.HtoDto;
import com.xiaobai.listview.XListView;
import com.xiaobai.utils.Utils;
import com.xiaobai.view.MyListView;

import java.util.ArrayList;
import java.util.List;

public class FindDetailsActivity extends BaseListActivity {
    private TextView title;
    private Button back;
    //    private RecyclerView recyclerView;
    private MyListView listview;
    private CardView cardview;
    private HtoDto item;
    private FindDetailsAdapter adapter;
    private List<String> datas = new ArrayList<String>();
    private LinearLayout content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_details);
        item = (HtoDto) getIntent().getSerializableExtra("data");
        initView();
        //获取详情
        getDetails();

    }

    /**
     * 获取详情
     */
    private void getDetails() {

        RequestBody formBody = new FormEncodingBuilder()
                .add("CmdId", "queryRecordDetails")
                .add("Goal", "record")
                .add("c_recordId", String.valueOf(item.Id))
                .add("Version", "01")
                .build();
        onRequest(101, url, formBody, App.token);

    }

    private void initView() {
        title = (TextView) findViewById(R.id.main_title_tv);
        back = (Button) findViewById(R.id.main_title_left);
//        listview = (MyListView) findViewById(R.id.details_list);

        title.setText("详情");
        back.setVisibility(View.VISIBLE);

//        LayoutInflater inflater = LayoutInflater.from(FindDetailsActivity.this);
//        View header = inflater.inflate(R.layout.layout_find_details_header, null);
//        listview.addHeaderView(header);
//        adapter = new FindDetailsAdapter(FindDetailsActivity.this, datas);
//        listview.setAdapter(adapter);
//        listview.setDividerHeight(0);
        content = (LinearLayout) findViewById(R.id.content);

    }

    @Override
    public void onPostSuccess(int postId, String result) {
        super.onPostSuccess(postId, result);
        Log.e("", result);
        Gson gson = new Gson();
        FindDetailsDto item = gson.fromJson(result, FindDetailsDto.class);

        setDatas(item);
    }

    private void setDatas(FindDetailsDto item) {
//        datas.addAll(item.url);
//        adapter.notifyDataSetChanged();

        for (int i = 0; i < item.url.size(); i++) {
            ImageView imageView = new ImageView(FindDetailsActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(16, 16, 16, 16);
            imageView.setLayoutParams(params);
            Glide.with(FindDetailsActivity.this)
                    .load(item.url.get(i))
                    .placeholder(Utils.makeDrable())
                    .crossFade()
                    .centerCrop()
                    .into(imageView);
            content.addView(imageView);
        }
    }


    @Override
    public void onPostFailure(int postId, String msg) {
        super.onPostFailure(postId, msg);
    }
}
