package com.xiaobai.activity;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.xiaobai.fragment.AddFragment;
import com.xiaobai.fragment.FindFragment;
import com.xiaobai.fragment.MineFragment;
import com.xiaobai.fragment.MoreFragment;
import com.xiaobai.fragment.RankFragment;
import com.xiaobai.myapplication.R;

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

    private FragmentManager mFragmentManager;


    // fragment 相对应的正常icon
//    private int iconNormal[] = { R.drawable.ind_tui_up, R.drawable.ind_tou_up,
//            R.drawable.ind_zi_up, R.drawable.ind_geng_up };
//    // fragment 相对应的按压icon
//    private int iconPressed[] = { R.drawable.ind_tui_dow, R.drawable.ind_tou_dow,
//            R.drawable.ind_zi_dow, R.drawable.ind_geng_dow };
    // fragment 相对应的标题
    private String titleArray[] = { "惠民推荐", "我要投资", "我的资产", "更多" };
    private String toWhere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
    }

    /*初始化控件*/
    private void initView() {
        tab_find = findViewById(R.id.home_tab_find_layout);
        tab_more = findViewById(R.id.home_tab_more_layout);
        tab_add = findViewById(R.id.main_add);
        tab_rank = findViewById(R.id.home_tab_rank_layout);
        tab_mine = findViewById(R.id.home_tab_mine_layout);


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
//        home_title_string.setText(titleArray[index]);
//		int count = mTabHost.getTabWidget().getChildCount();
//        for (int i = 0; i < imgIds.length; i++) {
//            if (i == index) {
//                ((ImageView) findViewById(imgIds[i]))
//                        .setImageResource(iconPressed[i]);
//                ((TextView) findViewById(txtIds[i])).setTextColor(getResources()
//                        .getColor(R.color.home_tab_text_pressed));
//            } else {
//                ((ImageView) findViewById(imgIds[i]))
//                        .setImageResource(iconNormal[i]);
//                ((TextView) findViewById(txtIds[i])).setTextColor(getResources()
//                        .getColor(R.color.home_tab_text_normal));
//            }
//        }
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
        if(findFragment != null) {
            transaction.hide(findFragment);
        }
        if(moreFragment != null) {
            transaction.hide(moreFragment);
        }
        if (addFragment !=null){
            transaction.hide(addFragment);
        }

        if(rankFragment != null) {
            transaction.hide(rankFragment);
        }
        if(mineFragment != null) {
            transaction.hide(mineFragment);
        }

    }
}
