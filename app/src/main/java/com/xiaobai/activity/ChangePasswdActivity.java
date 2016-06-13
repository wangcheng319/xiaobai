package com.xiaobai.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiaobai.application.R;

/**
 * 修改密码
 */
public class ChangePasswdActivity extends BaseActivity {
    private TextView main_title_tv;
    private View main_title_left;
    private View main_title_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwd);
        initView();
    }

    private void initView() {
        main_title_tv = (TextView) findViewById(R.id.main_title_tv);
        main_title_left = findViewById(R.id.main_title_left);
        main_title_right = findViewById(R.id.main_title_right);

        main_title_tv.setText("修改密码");
        main_title_left.setBackgroundResource(R.drawable.ic_back);
    }

    @Override
    public void onPostSuccess(int postId, String msg) {

    }

    @Override
    public void onPostFailure(int postId, String msg) {

    }
}
