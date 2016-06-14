package com.xiaobai.activity;

import android.content.Intent;
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
        main_title_left.setBackgroundResource(R.drawable.ic_back);
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
            case R.id.setting_info:
                startActivity(new Intent(this,PersonalInfoActivity.class));
                break;
            case R.id.setting_passwd:
                startActivity(new Intent(this,ChangePasswdActivity.class));
                break;
            case R.id.setting_water:
                finish();
                break;
            case R.id.setting_cache:
                finish();
                break;
            case R.id.setting_about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.setting_feedback:
                startActivity(new Intent(this,FeedBackActivity.class));
                break;
            case R.id.setting_share:
                finish();
                break;
            case R.id.setting_logout:
                finish();
                break;
        }
    }
}
