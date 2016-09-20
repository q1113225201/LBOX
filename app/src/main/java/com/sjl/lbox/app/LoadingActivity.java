package com.sjl.lbox.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 引导页
 *
 * @author SJL
 * @date 2016/8/6 14:16
 */
public class LoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        initView();
    }

    private void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
