package com.xiaobai.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    // 填写从短信SDK应用后台注册得到的APPKEY
    //此APPKEY仅供测试使用，且不定期失效，请到mob.com后台申请正式APPKEY
    private static String APPKEY = "135d671a4ffdc";

    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "2cddd5fb4a9439e904501e0527d16a27";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
//        initMsg();
    }

//    private void initMsg() {
//        SMSSDK.initSDK(this, "135d671a4ffdc", "2cddd5fb4a9439e904501e0527d16a27");
//        EventHandler eh = new EventHandler() {
//
//            @Override
//            public void afterEvent(int event, int result, Object data) {
//
//                if (result == SMSSDK.RESULT_COMPLETE) {
//                    //回调完成
//                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                        //提交验证码成功
//                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                        //获取验证码成功
//                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
//                        //返回支持发送验证码的国家列表
//                    }
//                } else {
//                    ((Throwable) data).printStackTrace();
//                }
//            }
//        };
//        SMSSDK.registerEventHandler(eh); //注册短信回调
//    }

    private void initView() {
        TextView textView = (TextView) findViewById(R.id.main_title_tv);
        textView.setText("注册");
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
                // 打开注册页面
//                SMSSDK.getSupportedCountries();
//                SMSSDK.getVerificationCode("+86", "15659926163");
                break;
            case R.id.register:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SMSSDK.unregisterAllEventHandler();

    }
}
