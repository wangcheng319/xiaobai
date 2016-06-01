package com.xiaobai.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.xiaobai.application.R;
import com.xiaobai.dto.User;
import com.xiaobai.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText login_num;//电话
    private EditText login_passwd;//密码
    private Button login;
    private Button register;
    private Button find_passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

    }

    private void initView() {
        TextView textView = (TextView) findViewById(R.id.main_title_tv);
        textView.setText("登录");
        login_num = (EditText) findViewById(R.id.login_num);
        login_passwd = (EditText) findViewById(R.id.login_passwd);
        login = (Button) findViewById(R.id.login);
        login_passwd = (EditText) findViewById(R.id.login_passwd);
        register = (Button) findViewById(R.id.register);

        //setOnclickerListen
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        login_passwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.login_passwd:

                break;
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));

                break;
        }

    }

    /**
     * 登录
     */
    private void login() {

        String phone = login_num.getText().toString().trim();
        String passwd = login_passwd.getText().toString().trim();

        String b = android.os.Build.VERSION.RELEASE;//android版本号
        String phoneName = android.os.Build.MODEL;//手机厂家


        phone = "15659926163";
        passwd = "123456";

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passwd)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody formBody = new FormEncodingBuilder()
                .add("CmdId", "login")
                .add("Goal", "user")
                .add("c_phone", phone)
                .add("c_password", Utils.md5(Utils.md5(phone) + Utils.md5(passwd)))
                .add("Version", "01")
                .add("c_device", "device_system：" + "android" + "system_version：" + b + phoneName)
                .build();
        onRequest(101, url, formBody);

    }

    @Override
    public void onPostSuccess(int postId, String msg) {
        Log.d("mmmmmmmmmm", msg);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();

        try {
            User user = gson.fromJson(jsonObject.getString("data"), User.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostFailure(int postId, String msg) {

    }
}
