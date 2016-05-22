package com.xiaobai.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaobai.dto.HtoDto;
import com.xiaobai.myapplication.R;

public class FindDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_details);

        HtoDto htoDto = (HtoDto) getIntent().getSerializableExtra("data");

        ImageView imageView = (ImageView) findViewById(R.id.iv);

        Glide.with(FindDetailsActivity.this).load(htoDto.urls.get(0)).into(imageView);
    }
}
