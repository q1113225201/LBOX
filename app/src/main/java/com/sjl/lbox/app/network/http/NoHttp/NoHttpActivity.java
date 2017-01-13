package com.sjl.lbox.app.network.http.NoHttp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.network.http.NoHttp.http.HttpRequestCallback;
import com.sjl.lbox.app.network.http.NoHttp.util.NoHttpUtil;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.BitmapUtil;

import java.util.HashMap;
import java.util.Map;

public class NoHttpActivity extends BaseActivity implements View.OnClickListener {

    private Button btnNoHttpPost;
    private Button btnNoHttpImage;
    private TextView tvNoHttpPostResult;
    private ImageView ivNoHttpResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_http);

        initView();
    }

    private void initView() {
        btnNoHttpPost = (Button) findViewById(R.id.btnNoHttpPost);
        btnNoHttpImage = (Button) findViewById(R.id.btnNoHttpImage);
        tvNoHttpPostResult = (TextView) findViewById(R.id.tvNoHttpPostResult);
        ivNoHttpResult = (ImageView) findViewById(R.id.ivNoHttpResult);

        btnNoHttpPost.setOnClickListener(this);
        btnNoHttpImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNoHttpPost:
                doNoHttpPost();
                break;
            case R.id.btnNoHttpImage:
                doNoHttpImage();
                break;
        }
    }

    private void doNoHttpImage() {
        String url = "http://pic10.nipic.com/20100929/3949593_105925014128_2.jpg";
        int what = 1;
        NoHttpUtil.sendImageRequest(url, what, new HttpRequestCallback<Bitmap>() {

            @Override
            public void onSucceed(Bitmap data) {
                ivNoHttpResult.setImageBitmap(data);
            }

            @Override
            public void onFailed(String error) {
                ivNoHttpResult.setImageBitmap(BitmapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.logo, 200, 200));
            }
        });
    }

    private void doNoHttpPost() {
        String url = "http://op.juhe.cn/robot/index";
        int what = 0;
        Map<String, String> postMap = new HashMap<String, String>();
        postMap.put("info", "测试");
        postMap.put("key", "09c309a2e401d78bb768d4cdb585c79c");
        NoHttpUtil.sendPostRequest(url, what, postMap, new HttpRequestCallback<String>() {
            @Override
            public void onSucceed(String data) {
                tvNoHttpPostResult.setText(data);
            }

            @Override
            public void onFailed(String error) {
                tvNoHttpPostResult.setText("error:" + error);
            }
        });
    }
}
