package com.xiaobai.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiaobai.application.R;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private TextView main_title_tv;
    private View main_title_left;
    private View main_title_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        main_title_tv = (TextView) findViewById(R.id.main_title_tv);
        main_title_left = findViewById(R.id.main_title_left);
        main_title_right = findViewById(R.id.main_title_right);

        main_title_tv.setText("设置");
    }

    @Override
    public void onPostSuccess(int postId, String msg) {

    }

    @Override
    public void onPostFailure(int postId, String msg) {

    }
}
