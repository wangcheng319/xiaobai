package com.xiaobai.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.xiaobai.application.R;
import com.xiaobai.fragment.AddFragment;
import com.xiaobai.fragment.FindFragment;
import com.xiaobai.fragment.MineFragment;
import com.xiaobai.fragment.MoreFragment;
import com.xiaobai.fragment.RankFragment;
import com.xiaobai.utils.StatusBarCompat;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //切换内容
    private FindFragment findFragment;
    private AddFragment addFragment;
    private MineFragment mineFragment;
    private MoreFragment moreFragment;
    private RankFragment rankFragment;
    //切换
    private View tab_find;
    private View tab_more;
    private View tab_add;
    private View tab_rank;
    private View tab_mine;
    private TextView main_title_tv;

    private FragmentManager mFragmentManager;

    private static int[] imgIds = {R.id.home_tab_find_image,
            R.id.home_tab_more_image, R.id.main_add, R.id.home_tab_rank_image, R.id.home_tab_mine_image};

    private static int[] txtIds = {R.id.home_tab_find_text, R.id.home_tab_more_text, R.id.main_add_tv, R.id.home_tab_rank_text, R.id.home_tab_mine_text};

    //     fragment 相对应的正常icon
    private int iconNormal[] = {R.drawable.home_home, R.drawable.home_more,
            R.drawable.home_add_selected, R.drawable.home_rank, R.drawable.home_person};
    // fragment 相对应的按压icon
    private int iconPressed[] = {R.drawable.home_home_selected, R.drawable.home_more_selected,
            R.drawable.home_add_selected, R.drawable.home_rank_selected, R.drawable.home_person_selected};
    //     fragment 相对应的标题
    private String titleArray[] = {"每日精选", "乐趣无限", "", "排行", "我的"};
    private String toWhere;
    private PushAgent mPushAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        StatusBarCompat.compat(this);


        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(true);
        mPushAgent.enable();

        PushAgent.getInstance(this).onAppStart();

        String device_token = UmengRegistrar.getRegistrationId(this);
        Log.e("", device_token + "");
        //开启推送并设置注册的回调处理
        mPushAgent.enable(new IUmengRegisterCallback() {

            @Override
            public void onRegistered(final String registrationId) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        //onRegistered方法的参数registrationId即是device_token
                        Log.d("device_token", registrationId);
                    }
                });
            }
        });

        initView();
    }

    /*初始化控件*/
    private void initView() {
        tab_find = findViewById(R.id.home_tab_find_layout);
        tab_more = findViewById(R.id.home_tab_more_layout);
        tab_add = findViewById(R.id.main_add);
        tab_rank = findViewById(R.id.home_tab_rank_layout);
        tab_mine = findViewById(R.id.home_tab_mine_layout);

        main_title_tv = (TextView) findViewById(R.id.main_title_tv);


        tab_mine.setOnClickListener(this);
        tab_add.setOnClickListener(this);
        tab_find.setOnClickListener(this);
        tab_more.setOnClickListener(this);
        tab_rank.setOnClickListener(this);

        mFragmentManager = getSupportFragmentManager();

        setTabSelection(0);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_tab_find_layout:
                setTabSelection(0);
                break;
            case R.id.home_tab_more_layout:
                setTabSelection(1);
                break;
            case R.id.main_add:
                setTabSelection(2);
                break;
            case R.id.home_tab_rank_layout:
                setTabSelection(3);
                break;
            case R.id.home_tab_mine_layout:
                setTabSelection(4);
                break;


        }

    }


    /**
     * Tab选中状态
     *
     * @param index
     */
    private void setTabSelection(int index) {
        // 设置选中状态显示当前内容视图
        main_title_tv.setText(titleArray[index]);
//        int count = mTabHost.getTabWidget().getChildCount();
        for (int i = 0; i < imgIds.length; i++) {
            if (i == index) {
                ((ImageView) findViewById(imgIds[i]))
                        .setImageResource(iconPressed[i]);
                ((TextView) findViewById(txtIds[i])).setTextColor(getResources()
                        .getColor(R.color.blue));
            } else {
                ((ImageView) findViewById(imgIds[i]))
                        .setImageResource(iconNormal[i]);
                ((TextView) findViewById(txtIds[i])).setTextColor(getResources()
                        .getColor(R.color.pull_view_text));
            }
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (findFragment == null) {
                    findFragment = new FindFragment();

                    transaction.add(R.id.realtabcontent, findFragment);
                } else {
                    transaction.show(findFragment);
                }
                break;
            case 1:
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    transaction.add(R.id.realtabcontent, moreFragment);
                } else {
                    transaction.show(moreFragment);
                }
                break;

            case 2:
                if (addFragment == null) {
                    addFragment = new AddFragment();
                    transaction.add(R.id.realtabcontent, addFragment);
                } else {
                    transaction.show(addFragment);
                }
                break;
            case 3:
                if (rankFragment == null) {
                    rankFragment = new RankFragment();
                    transaction.add(R.id.realtabcontent, rankFragment);
                } else {
                    transaction.show(rankFragment);
                }
                break;
            case 4:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.realtabcontent, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;
            default:
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (findFragment != null) {
            transaction.hide(findFragment);
        }
        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }
        if (addFragment != null) {
            transaction.hide(addFragment);
        }

        if (rankFragment != null) {
            transaction.hide(rankFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }

    }
}
