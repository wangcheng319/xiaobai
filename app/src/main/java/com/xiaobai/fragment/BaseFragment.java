package com.xiaobai.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.squareup.okhttp.internal.http.HttpMethod;
import com.xiaobai.application.R;
import com.xiaobai.utils.Utils;

import java.io.IOException;

/**
 * Created by wangc on 2016/5/19.
 */
public abstract class BaseFragment extends Fragment {
    public  static  final String url = "http://janhuu.imwork.net:30319/qianyuApp/requestservices.action";

    public BaseFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void onRequest(final int postId, final String url, RequestBody requestBody) {
        if (!Utils.isConnectByNet(getActivity()) && !Utils.isConnectByMobile(getActivity()) && !Utils.isConnectByWifi(getActivity())) {
            Toast.makeText(getActivity(), getString(R.string.network), Toast.LENGTH_SHORT).show();
            return;
        }

//        if (methodType == null) {
//            return;
//        }

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


}
