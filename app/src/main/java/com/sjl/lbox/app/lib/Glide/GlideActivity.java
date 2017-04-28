package com.sjl.lbox.app.lib.Glide;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * Glide图片加载
 *
 * @author SJL
 * @date 2017/4/28
 */
public class GlideActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivGlide;
    private Button btnLoadImg;
    private Button btnLoadGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        initView();
    }

    private void initView() {
        ivGlide = (ImageView) findViewById(R.id.ivGlide);
        btnLoadImg = (Button) findViewById(R.id.btnLoadImg);
        btnLoadImg.setOnClickListener(this);
        btnLoadGif = (Button) findViewById(R.id.btnLoadGif);
        btnLoadGif.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadImg:
                Glide.with(mContext)
                        .load("http://www.baidu.com")//要加载的图片
                        .placeholder(R.drawable.loading)//加载中显示动画
                        .error(R.drawable.network_error)//加载失败动画
                        .crossFade()//图片加载动画
                        .into(ivGlide);
                break;
            case R.id.btnLoadGif:
                Glide.with(mContext)
                        .load("http://www.baidu.com")
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.network_error)
                        .into(ivGlide);
                break;
        }
    }
}
