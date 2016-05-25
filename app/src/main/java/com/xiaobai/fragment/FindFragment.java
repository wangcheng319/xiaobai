package com.xiaobai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.xiaobai.activity.FindDetailsActivity;
import com.xiaobai.adapter.FindAdapter1;
import com.xiaobai.dto.HtoDto;
import com.xiaobai.listview.IXViewListener;
import com.xiaobai.listview.XListView;
import com.xiaobai.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 发现
 */
public class FindFragment extends BaseFragment implements IXViewListener {


    private View rootView;
    private List<HtoDto> mDatas;
    public String url = "http://janhuu.imwork.net:30319/qianyuApp/requestservices.action";

    private XListView listView;


    public FindFragment() {
        // Required empty public constructor
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                Gson gson = new Gson();
                java.lang.reflect.Type type2 = new TypeToken<List<HtoDto>>() {
                }.getType();

                mDatas = gson.fromJson(msg.obj.toString(), type2);
                listView.setAdapter(new FindAdapter1(getActivity(), mDatas));
            }
            return false;
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_find, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RequestBody formBody = new FormEncodingBuilder()
                .add("CmdId", "queryHotRecord")
                .add("Goal", "record")
                .add("c_pageCount", "10")
                .add("c_currentPage", "1")
                .add("Version", "01")
                .build();
        onRequest(101, url, formBody);
        initView();
    }

    @Override
    public void onPostSuccess(int postId, String msg) {

        JSONObject jsonObject = null;
        String data = null;
        Log.i("",msg);
        try {
            jsonObject = new JSONObject(msg);
            data = jsonObject.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Message mesage = handler.obtainMessage();
        mesage.what = 0;
        mesage.obj = data;
        handler.sendMessage(mesage);

    }

    @Override
    public void onPostFailure(int postId, String msg) {


    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private void initView() {
        listView = (XListView) rootView.findViewById(R.id.find_list);
        listView.setPullRefreshEnable(true); // 允许下拉刷新
        listView.setPullLoadEnable(false); // 禁止加载更多
        listView.setAutoLoadEnable(true); // 禁止自动加载
        listView.setXListViewListener(this);// 加载监听


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FindDetailsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("data", mDatas.get(position));
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRefresh() {
        RequestBody formBody = new FormEncodingBuilder()
                .add("CmdId", "queryHotRecord")
                .add("Goal", "record")
                .add("c_pageCount", "10")
                .add("c_currentPage", "1")
                .add("Version", "01")
                .build();
        onRequest(101, url, formBody);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
