package com.xiaobai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.xiaobai.activity.FindDetailsActivity;
import com.xiaobai.adapter.FindAdapter1;
import com.xiaobai.dto.HtoDto;
import com.xiaobai.listview.IXViewListener;
import com.xiaobai.listview.XListView;
import com.xiaobai.myapplication.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 发现
 */
public class FindFragment extends Fragment implements IXViewListener {


    private View rootView;
    private RecyclerView mList;
    private FindAdapter1 findAdapter1;
    private List<HtoDto> mDatas;
    public String url = "http://janhuu.imwork.net:30319/qianyuApp/requestservices.action";

    private ListView listView;
    List<HtoDto> lists;


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

                lists = gson.fromJson(msg.obj.toString(), type2);
                listView.setAdapter(new FindAdapter1(getActivity(), lists));
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    setNetWork();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        initView();
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private void setNetWork() throws IOException {

        OkHttpClient client = new OkHttpClient();
//        OkHttpUtils
//                .post()
//                .url(url)
//                .addParams("CmdId", "queryHotRecord")
//                .addParams("Goal", "record")
//                .addParams("c_pageCount", "10")
//                .addParams("c_currentPage", "1")
//                .addParams("Version", "01")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
//
//
//                        Log.e("okhttp+res", response);
//                        Gson gson = new Gson();
//                        String data = null;
//
//                        JSONObject jsonObject = null;
//                        try {
//                            jsonObject = new JSONObject(response);
//                            data = jsonObject.getString("data");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        java.lang.reflect.Type type2 = new TypeToken<List<HtoDto>>() {
//                        }.getType();
//
//                        mDatas = gson.fromJson(data, type2);
//
//                    }
//                });


        RequestBody formBody = new FormEncodingBuilder()
                .add("CmdId", "queryHotRecord")
                .add("Goal", "record")
                .add("c_pageCount", "10")
                .add("c_currentPage", "1")
                .add("Version", "01")
                .build();


        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();


        Response response = client.newCall(request).execute();
        Log.d("req",request.toString()+"");
        if (response.isSuccessful()) {
            String res = response.body().string();
            Log.e("res", res);
            String data = null;

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(res);
                data = jsonObject.getString("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Message mesage = handler.obtainMessage();
            mesage.what = 0;
            mesage.obj = data;
            handler.sendMessage(mesage);
        } else {
            throw new IOException("Unexpected code " + response);
        }

    }

    private void initView() {
        listView = (ListView) rootView.findViewById(R.id.find_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FindDetailsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("data",lists.get(position));
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
