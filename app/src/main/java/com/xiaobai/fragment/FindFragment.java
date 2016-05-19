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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.xiaobai.adapter.FindAdapter;
import com.xiaobai.adapter.FindAdapter1;
import com.xiaobai.dto.HtoDto;
import com.xiaobai.listview.IXViewListener;
import com.xiaobai.listview.XListView;
import com.xiaobai.myapplication.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 发现
 */
public class FindFragment extends Fragment implements IXViewListener {

    /**
     * .add("CmdId", "queryHotRecord")
     * .add("Goal", "record")
     * .add("c_pageCount", "10")
     * .add("c_currentPage", "1")
     * .add("Version", "01")
     */

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View rootView;
    private RecyclerView mList;
    private FindAdapter1 findAdapter1;
    private List<HtoDto> mDatas = new ArrayList<HtoDto>();
    public String url = "http://janhuu.imwork.net:30319/qianyuApp/requestservices.action";

    private XListView listView;


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
        setNetWork();
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setNetWork() {

        OkHttpUtils.getInstance()
                .post()
                .url(url)
                .addParams("CmdId", "queryHotRecord")
                .addParams("Goal", "record")
                .addParams("c_pageCount", "10")
                .addParams("c_currentPage", "1")
                .addParams("Version", "01")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();


                        Log.e("res", response);
                        Gson gson = new Gson();
                        String data = null;

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            data = jsonObject.getString("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        java.lang.reflect.Type type2 = new TypeToken<List<HtoDto>>() {
                        }.getType();

                        mDatas = gson.fromJson(data, type2);

                    }
                });

    }


    private void initView() {


        listView = (XListView) rootView.findViewById(R.id.find_list);
        findAdapter1 = new FindAdapter1(getActivity(), mDatas);
        listView.setAdapter(findAdapter1);

        listView.setPullRefreshEnable(true); // 允许下拉刷新
        listView.setPullLoadEnable(false); // 禁止加载更多
        listView.setAutoLoadEnable(false); // 禁止自动加载
        listView.setXListViewListener(this);// 加载监听

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
