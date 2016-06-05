package com.xiaobai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.xiaobai.activity.MoreDetailsActivity;
import com.xiaobai.adapter.MoreAdapter;
import com.xiaobai.application.R;
import com.xiaobai.dto.MoreDto;
import com.xiaobai.view.MoreDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * 更多
 */
public class MoreFragment extends BaseFragment {
    private View rootView;
    private RecyclerView recyclerView;
    private MoreAdapter adapter;
    private List<MoreDto> moreDtos = new ArrayList<MoreDto>();

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
                .add("Version", "01")
                .build();
        onRequest(101, url, requestBody, "");
    }

    private void initView() {
        adapter = new MoreAdapter(getActivity(), moreDtos);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.id_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new MoreDivider(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MoreAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, MoreDto data) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MoreDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("datas", data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onPostSuccess(int postId, String msg) {
        Log.d("tag", msg + "");
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<List<MoreDto>>() {
        }.getType();
        List<MoreDto> lists = new ArrayList<MoreDto>();
        lists = gson.fromJson(msg, type);
        moreDtos.addAll(lists);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onPostFailure(int postId, String msg) {

    }


}
