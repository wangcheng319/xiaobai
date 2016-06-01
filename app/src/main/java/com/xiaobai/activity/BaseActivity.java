package com.xiaobai.activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.xiaobai.application.R;
import com.xiaobai.utils.Utils;

import java.io.IOException;

/**
 * Created by wangc on 2016/5/19.
 */
public abstract class BaseActivity extends AppCompatActivity {
    //    public static final String url = "http://139.196.203.173:8080/qianyuApp/requestservices.action";
    public static final String url = "http://192.168.31.200:8080/qianyuApp/requestservices.action";
    private SystemBarTintManager tintManager;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.main));
            tintManager.setStatusBarTintEnabled(true);
        }

    }

    public void onRequest(final int postId, final String url, RequestBody requestBody) {
        if (!Utils.isConnectByNet(this) && !Utils.isConnectByMobile(this) && !Utils.isConnectByWifi(this)) {
            Toast.makeText(this, getString(R.string.network), Toast.LENGTH_SHORT).show();
            return;
        }

//        if (methodType == null) {
//            return;
//        }

        OkHttpClient client = new OkHttpClient();
        if (requestBody == null) {
            requestBody = new FormEncodingBuilder().build();
        }

        final Dialog progressDialog = new Dialog(this, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.dialog_waiting);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setTextSize(12);
        msg.setText("卖力加载中...");


        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                onPostFailure(postId, e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (progressDialog.isShowing()) {
                    progressDialog.cancel();
                }
                onPostSuccess(postId, response.body().string());
            }
        });
    }

    /**
     * 请求成功
     *
     * @param postId
     * @param msg
     */
    public abstract void onPostSuccess(int postId, String msg);

    /**
     * 请求失败
     *
     * @param postId
     * @param msg
     */
    public abstract void onPostFailure(int postId, String msg);


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
