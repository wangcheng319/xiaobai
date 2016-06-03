package com.xiaobai.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.xiaobai.application.R;

import okhttp3.internal.framed.Header;

/**
 * 更多
 */
public class MoreFragment extends BaseFragment {
    private View rootView;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_more, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initView();
    }

    @Override
    public void onResume() {
        super.onResume();

        RequestBody requestBody = new FormEncodingBuilder()
                .add("CmdId", "queryCatgroy")
                .add("Goal", "record")
                .build();
        onRequest(101, url, requestBody, "");
    }

    private void initView() {

    }

    @Override
    public void onPostSuccess(int postId, String msg) {
        Log.d("tag", msg + "");

    }

    @Override
    public void onPostFailure(int postId, String msg) {

    }


}
