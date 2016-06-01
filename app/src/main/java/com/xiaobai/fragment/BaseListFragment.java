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
import com.xiaobai.listview.IXViewListener;
import com.xiaobai.listview.XListView;
import com.xiaobai.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BaseListFragment extends BaseFragment implements IXViewListener {
    protected boolean isLoading = false;

    public BaseListFragment() {
        // Required empty public constructor
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

    @Override
    public void onPostSuccess(int postId, String msg) {
        isLoading = false;

        if(getXList()!=null){
            getXList().stopRefresh();
            getXList().stopLoadMore();
            getXList().setRefreshTime(getTime());
        }

    }

    @Override
    public void onPostFailure(int postId, String msg) {
        isLoading = false;

        if(getXList()!=null){
            getXList().stopRefresh();
            getXList().stopLoadMore();
            getXList().setRefreshTime(getTime());
        }
    }

    protected String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA)
                .format(new Date());
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        isLoading = true;

    }

    @Override
    public void onLoadMore() {
        if (isLoading) {
            return;
        }
        isLoading = true;
    }

    public XListView getXList() {
        return null;
    }

}
