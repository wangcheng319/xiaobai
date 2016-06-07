package com.xiaobai.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.xiaobai.app.App;
import com.xiaobai.application.R;
import com.xiaobai.fragment.AddFragment;
import com.xiaobai.fragment.FindFragment;
import com.xiaobai.fragment.MineFragment;
import com.xiaobai.fragment.MoreFragment;
import com.xiaobai.fragment.RankFragment;
import com.xiaobai.imagepicker.AndroidImagePicker;
import com.xiaobai.imagepicker.ImageItem;
import com.xiaobai.imagepicker.ImagePresenter;
import com.xiaobai.imagepicker.ImagesGridActivity;
import com.xiaobai.imagepicker.UilImagePresenter;
import com.xiaobai.imagepicker.Util;
import com.xiaobai.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 *
 */
public class MainActivity extends BaseActivity implements View.OnClickListener,
        AndroidImagePicker.OnPictureTakeCompleteListener, AndroidImagePicker.OnImageCropCompleteListener,
        AndroidImagePicker.OnImagePickCompleteListener {
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
    private SystemBarTintManager tintManager;
    //图片选择
    private final int REQ_IMAGE = 1433;
    private ImageView ivCrop;
    ImagePresenter presenter = new UilImagePresenter();
    //private ImageView ivShow;
    GridView mGridView;
    SelectAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        StatusBarCompat.compat(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.main));
            tintManager.setStatusBarTintEnabled(true);
        }


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
        //图片选择
        AndroidImagePicker.getInstance().addOnImageCropCompleteListener(this);

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
//                setTabSelection(2);
                Intent intent = new Intent();
                int requestCode = REQ_IMAGE;
                AndroidImagePicker.getInstance().setSelectMode(AndroidImagePicker.Select_Mode.MODE_MULTI);
                AndroidImagePicker.getInstance().setShouldShowCamera(true);
                intent.setClass(this, ImagesGridActivity.class);
                startActivityForResult(intent, requestCode);
                break;
            case R.id.home_tab_rank_layout:
                setTabSelection(3);
                break;
            case R.id.home_tab_mine_layout:
//                setTabSelection(4);
                startActivity(new Intent(this, LoginActivity.class));
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

    @Override
    public void onPostSuccess(int postId, String msg) {

    }

    @Override
    public void onPostFailure(int postId, String msg) {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_IMAGE) {
                List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();

                //获取图片成功，跳转到编辑页面
                Intent intent = new Intent(MainActivity.this, PhotoEditActivity.class);
                intent.putExtra("datas", (Serializable) imageList);
                startActivity(intent);
            }
        }
    }

    /**
     * eventbus
     */
    public class MsgEvent {
        public final List<ImageItem> imageList;

        public MsgEvent(List<ImageItem> imageList) {
            this.imageList = imageList;
        }
    }

    @Override
    public void onPictureTakeComplete(String picturePath) {
//        ivCrop.setVisibility(View.GONE);
        List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();
        imageList.clear();
        ImageItem item = new ImageItem(picturePath, "", 0);
        imageList.add(item);
//        mAdapter.clear();
//        mAdapter.addAll(imageList);

    }

    @Override
    public void onImageCropComplete(Bitmap bmp, float ratio) {
//        ivCrop.setVisibility(View.VISIBLE);
//        ivCrop.setImageBitmap(bmp);
    }

    @Override
    public void onImagePickComplete(List<ImageItem> items) {
//        ivCrop.setVisibility(View.GONE);

        //List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();
//        mAdapter.clear();
//        mAdapter.addAll(items);
    }


    class SelectAdapter extends ArrayAdapter<ImageItem> {

        //private int mResourceId;
        public SelectAdapter(Context context) {
            super(context, 0);
            //this.mResourceId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageItem item = getItem(position);
            LayoutInflater inflater = getLayoutInflater();
            //View view = inflater.inflate(mResourceId, null);
            int width = (Utils.screenWidth_ - Util.dp2px(MainActivity.this, 10 * 2)) / 3;

            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setBackgroundColor(Color.GRAY);
            GridView.LayoutParams params = new AbsListView.LayoutParams(width, width);
            imageView.setLayoutParams(params);


            presenter.onPresentImage(imageView, item.path, width);

            return imageView;
        }

    }

    @Override
    protected void onResume() {
        AndroidImagePicker.getInstance().setOnPictureTakeCompleteListener(this);//watching Picture taking
        AndroidImagePicker.getInstance().setOnImagePickCompleteListener(this);
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AndroidImagePicker.getInstance().deleteOnImagePickCompleteListener(this);
        AndroidImagePicker.getInstance().removeOnImageCropCompleteListener(this);
        AndroidImagePicker.getInstance().deleteOnPictureTakeCompleteListener(this);

    }
}
