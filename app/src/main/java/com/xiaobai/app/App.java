package com.xiaobai.app;

import android.app.Application;


import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by wangc on 2016/5/11.
 */
public class App extends Application {
    private PushAgent mPushAgent;
    @Override
    public void onCreate() {
        super.onCreate();
//        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG);
        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(true);

        String device_token = UmengRegistrar.getRegistrationId(this);

    }
}
