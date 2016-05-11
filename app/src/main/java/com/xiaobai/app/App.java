package com.xiaobai.app;

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by wangc on 2016/5/11.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient client =
                OkHttpUtils.getInstance().getOkHttpClient();

        Request request = new Request.Builder().addHeader("","").build();

    }
}
