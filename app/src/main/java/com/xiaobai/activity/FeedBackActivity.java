package com.xiaobai.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaobai.application.R;

public class FeedBackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
    }

    @Override
    public void onPostSuccess(int postId, String msg) {

    }

    @Override
    public void onPostFailure(int postId, String msg) {

    }
}
