package com.xiaobai.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaobai.application.R;

public class FindDetailsActivity extends AppCompatActivity {
    private TextView title;
    private Button back;
    private RecyclerView recyclerView;
    private CardView headerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_details);

        initView();

    }

    private void initView() {
        title = (TextView) findViewById(R.id.main_title_tv);
        back = (Button) findViewById(R.id.main_title_left);
        recyclerView = (RecyclerView) findViewById(R.id.details_list);


        title.setText("详情");
        back.setVisibility(View.VISIBLE);
    }
}
