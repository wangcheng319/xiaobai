package com.xiaobai.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import okhttp3.internal.framed.Header;

/**
 * Created by wangc on 2016/5/19.
 */
public abstract class BaseFragment extends Fragment {
    public static final String url = "http://139.196.203.173:8080/qianyuApp/requestservices.action";//正式
//        public static final String url = "http://192.168.31.200:8080/qianyuApp/requestservices.action";

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

    public BaseFragment() {

    }

    public void onRequest(final int postId, final String url, RequestBody requestBody, String header) {
        if (!Utils.isConnectByNet(getActivity()) && !Utils.isConnectByMobile(getActivity()) && !Utils.isConnectByWifi(getActivity())) {
            Toast.makeText(getActivity(), getString(R.string.network), Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();
        if (requestBody == null) {
            requestBody = new FormEncodingBuilder().build();
        }

        final Dialog progressDialog = new Dialog(getActivity(), R.style.progress_dialog);
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
                .addHeader("token", header)
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


}
