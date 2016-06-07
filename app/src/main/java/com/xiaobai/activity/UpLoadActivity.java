package com.xiaobai.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.xiaobai.adapter.UpLoadAdapter;
import com.xiaobai.application.R;
import com.xiaobai.imagepicker.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class UpLoadActivity extends BaseActivity {

    private Button back;
    private TextView title;
    private TextView right;
    private Button upload;
    private GridView gridView;
    private UpLoadAdapter adapter;

    private List<ImageItem> imageList = new ArrayList<ImageItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load);

        imageList = (List<ImageItem>) getIntent().getSerializableExtra("datas");
        initView();
    }

    private void initView() {

        back = (Button) findViewById(R.id.main_title_left);
        title = (TextView) findViewById(R.id.main_title_tv);
        right = (TextView) findViewById(R.id.main_title_right);
        upload = (Button) findViewById(R.id.upload);
        gridView = (GridView) findViewById(R.id.gv_upload);

        back.setVisibility(View.VISIBLE);
        right.setVisibility(View.VISIBLE);

        back.setText("返回");
        title.setText("上传");
        right.setText("取消");

        back.setOnClickListener(this);
        right.setOnClickListener(this);
        upload.setOnClickListener(this);

        adapter = new UpLoadAdapter(this, imageList);
        gridView.setAdapter(adapter);

    }

    @Override
    public void onPostSuccess(int postId, String msg) {

    }

    @Override
    public void onPostFailure(int postId, String msg) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.main_title_left:
                finish();
                break;
            case R.id.main_title_right:
                break;
            case R.id.upload:
                upLoad();
                break;

        }
    }

    private void upLoad() {
//        CmdId =  publishRecord
//        Goal = record
//        c_type =     //待定
//                c_content =  //内容
//                        c_label =   //标签的code,获取分类信息的接口返回
//                                c_url = //七牛返回的url（多条记录的url通过#拼接）

    }
}
