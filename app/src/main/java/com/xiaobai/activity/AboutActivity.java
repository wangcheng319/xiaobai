package com.xiaobai.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaobai.application.R;
import com.xiaobai.utils.Utils;

public class AboutActivity extends BaseActivity {
    private RelativeLayout main;

    private TextView main_title_tv;
    private View main_title_left;
    private View main_title_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        main = (RelativeLayout) findViewById(R.id.main);
        main.setBackgroundDrawable(Utils.makeDrable());
        initView();
    }

    private void initView() {
        main_title_tv = (TextView) findViewById(R.id.main_title_tv);
        main_title_left = findViewById(R.id.main_title_left);
        main_title_right = findViewById(R.id.main_title_right);

        main_title_tv.setText("关于小白");
        main_title_left.setBackgroundResource(R.drawable.ic_back);
    }

    @Override
    public void onPostSuccess(int postId, String msg) {

    }

    @Override
    public void onPostFailure(int postId, String msg) {

    }
}
