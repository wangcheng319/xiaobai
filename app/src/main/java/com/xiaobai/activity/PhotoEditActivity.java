package com.xiaobai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.xiaobai.application.R;
import com.xiaobai.imagepicker.ImageItem;
import com.xiaobai.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import us.pinguo.edit.sdk.PGEditActivity;
import us.pinguo.edit.sdk.base.PGEditResult;
import us.pinguo.edit.sdk.base.PGEditSDK;

public class PhotoEditActivity extends BaseActivity {
    private Button back;
    private TextView title;
    private TextView right;
    private ViewPager viewPager;

    private List<ImageItem> imageList = new ArrayList<ImageItem>();
    private String uploadToken;
    private List<ImageView> imageViews = new ArrayList<ImageView>();
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_edit);
        imageList = (List<ImageItem>) getIntent().getSerializableExtra("datas");
        initView();
    }

    private void initView() {
        back = (Button) findViewById(R.id.main_title_left);
        title = (TextView) findViewById(R.id.main_title_tv);
        right = (TextView) findViewById(R.id.main_title_right);
        viewPager = (ViewPager) findViewById(R.id.vp_photo_edit);

        back.setVisibility(View.VISIBLE);
        back.setText("取消");
        title.setText("编辑");
        right.setVisibility(View.VISIBLE);
        right.setText("下一步");
        back.setOnClickListener(this);
        right.setOnClickListener(this);

        for (int i = 0; i < imageList.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageViews.add(imageView);
            Glide.with(this)
                    .load(imageList.get(i).path)
                    .placeholder(Utils.makeDrable())
                    .crossFade()
                    .centerCrop()
                    .into(imageView);
        }

        PhotoEditAdapter adapter = new PhotoEditAdapter(imageViews);
        viewPager.setAdapter(adapter);
        //获取token
        getToken();
    }

    /**
     * 获取图片上传的token
     */
    private void getToken() {
        RequestBody requestBody = new FormEncodingBuilder()
                .add("CmdId", "uploadToken")
                .add("Goal", "qiniu")
                .add("Version", "01")
                .add("userId", String.valueOf(20))
                .build();

        com.squareup.okhttp.OkHttpClient client = new com.squareup.okhttp.OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    uploadToken = json.getString("uploadToken");


                    //上传图片到七牛
                    UploadManager uploadManager = new UploadManager();
                    for (int i = 0; i < imageList.size(); i++) {
                        File file = new File(imageList.get(i).path);
                        uploadManager.put(file, imageList.get(i).path, uploadToken,
                                new UpCompletionHandler() {
                                    @Override
                                    public void complete(String key, ResponseInfo info, JSONObject res) {
                                        //  res 包含hash、key等信息，具体字段取决于上传策略的设置。
                                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                                    }
                                }, null);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public void onPostSuccess(int postId, String msg) {

    }

    @Override
    public void onPostFailure(int postId, String msg) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.main_title_left:
                finish();
                break;
            case R.id.main_title_right:

                Intent intent = new Intent(PhotoEditActivity.this, UpLoadActivity.class);
                intent.putExtra("datas", (Serializable) imageList);
                startActivity(intent);
                break;
        }
    }

    public class PhotoEditAdapter extends PagerAdapter {
        private List<ImageView> mListViews;

        public PhotoEditAdapter(List<ImageView> mListViews) {
            this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));//删除页卡
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {  //这个方法用来实例化页卡
            container.addView(mListViews.get(position), 0);//添加页卡
            imageView = mListViews.get(position);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击图片进行编辑
                    String folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                            .getAbsolutePath() + File.separator;
                    String outFilePath = folderPath + System.currentTimeMillis() + ".jpg";

                    if (imageList.get(position).path.toLowerCase().endsWith("png")) {
                        outFilePath = outFilePath.replaceAll("jpg", "png");
                    }
                    PGEditSDK.instance().startEdit(PhotoEditActivity.this,PGEditActivity.class, imageList.get(position).path, outFilePath);

                }
            });
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();//返回页卡的数量
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;//官方提示这样写
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PGEditSDK.PG_EDIT_SDK_REQUEST_CODE
                && resultCode == Activity.RESULT_OK) {

            PGEditResult editResult = PGEditSDK.instance().handleEditResult(data);

            imageView.setImageBitmap(editResult.getThumbNail());

            Toast.makeText(this, "Photo saved to:" + editResult.getReturnPhotoPath(), Toast.LENGTH_LONG).show();
//            enterReEditState();
        }

        if (requestCode == PGEditSDK.PG_EDIT_SDK_REQUEST_CODE
                && resultCode == PGEditSDK.PG_EDIT_SDK_RESULT_CODE_CANCEL) {
            Toast.makeText(this, "Edit cancelled!", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == PGEditSDK.PG_EDIT_SDK_REQUEST_CODE
                && resultCode == PGEditSDK.PG_EDIT_SDK_RESULT_CODE_NOT_CHANGED) {
            Toast.makeText(this, "Photo do not change!", Toast.LENGTH_SHORT).show();
        }
    }
}
