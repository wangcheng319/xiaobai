package com.xiaobai.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiaobai.application.R;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText phone;
    private EditText verycode;
    private EditText passwd;
    private EditText passwd2;


    private Button getVerycode;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        phone = (EditText) findViewById(R.id.register_phone);
        verycode = (EditText) findViewById(R.id.register_veryf_num);
        passwd = (EditText) findViewById(R.id.register_passwd);
        passwd2 = (EditText) findViewById(R.id.register_passwd2);

        getVerycode = (Button) findViewById(R.id.register_veryf);
        register = (Button) findViewById(R.id.register);

        getVerycode.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onPostSuccess(int postId, String msg) {

    }

    @Override
    public void onPostFailure(int postId, String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_veryf:
                break;
            case R.id.register:
                break;
        }

    }
}
