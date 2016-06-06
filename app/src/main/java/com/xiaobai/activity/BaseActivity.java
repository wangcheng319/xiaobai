package com.xiaobai.activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by wangc on 2016/5/19.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String url = "http://139.196.203.173:8080/qianyuApp/requestservices.action";//正式
    //      public static final String url = "http://192.168.31.200:8080/qianyuApp/requestservices.action";
    private SystemBarTintManager tintManager;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                onPostSuccess(msg.arg1, String.valueOf(msg.obj));
            } else {
                onPostFailure(msg.arg1, String.valueOf(msg.obj));
            }
            return true;
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //设置状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.main));
            tintManager.setStatusBarTintEnabled(true);
        }

        //返回
        findViewById(R.id.main_title_left).setOnClickListener(this);
    }

    public void onRequest(final int postId, final String url, RequestBody requestBody, String header) {
        if (!Utils.isConnectByNet(this) && !Utils.isConnectByMobile(this) && !Utils.isConnectByWifi(this)) {
            Toast.makeText(this, getString(R.string.network), Toast.LENGTH_SHORT).show();
            return;
        }

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
        progressDialog.show();


        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                onPostFailure(postId, e.getMessage());
                if (progressDialog.isShowing()) {
                    progressDialog.cancel();
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (progressDialog.isShowing()) {
                    progressDialog.cancel();
                }
                Log.e("token", response.header("token") + "");
                String res = response.body().string();
                JSONObject jsonObject = null;
                String data = null;
                Message mesage = handler.obtainMessage();

                Log.e("res", res);
                try {
                    jsonObject = new JSONObject(res);
                    if (jsonObject.getString("errCode").equals("0")) {
                        data = jsonObject.getString("data");
                        mesage.what = 0;
                        mesage.obj = data;
                        mesage.arg1 = postId;
                        handler.sendMessage(mesage);
                    } else {
                        mesage.what = 1;
                        mesage.arg1 = postId;
                        mesage.obj = jsonObject.getString("errCode" + jsonObject.getString("errMsg"));
                        handler.sendMessage(mesage);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_title_left:
                finish();
                break;
        }
    }
}
