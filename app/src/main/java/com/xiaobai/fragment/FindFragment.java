package com.xiaobai.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.xiaobai.adapter.FindAdapter;
import com.xiaobai.dto.HtoDto;
import com.xiaobai.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 发现
 */
public class FindFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View rootView;
    private RecyclerView mList;
    private FindAdapter findAdapter;
    private List<HtoDto> mDatas;
    public String url = "http://janhuu.imwork.net:30319/qianyuApp/requestservices.action";


    public FindFragment() {
        // Required empty public constructor
    }

    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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

        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        setNetWork();
    }

    private void setNetWork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        post(url);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 网络设置
     */

    public void post(String url) throws IOException, JSONException {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
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

        Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            Log.e("return", result);
            System.out.print(result);
            Gson gson = new Gson();

            JSONObject jsonObject = new JSONObject(result);
            String data = jsonObject.getString("data");
            java.lang.reflect.Type type2 = new TypeToken<List<HtoDto>>() {
            }.getType();
            mDatas = gson.fromJson(data,type2);
            findAdapter = new FindAdapter(getActivity(), mDatas);
            mList.setAdapter(findAdapter);

        } else {
            throw new IOException("Unexpected code " + response);
        }

    }

    private void initView() {

        mList = (RecyclerView) rootView.findViewById(R.id.find_list);
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
        findAdapter = new FindAdapter(getActivity(), mDatas);
        mList.setAdapter(findAdapter);
    }

}
