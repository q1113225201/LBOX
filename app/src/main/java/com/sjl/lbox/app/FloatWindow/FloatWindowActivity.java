package com.sjl.lbox.app.FloatWindow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sjl.lbox.R;
import com.sjl.lbox.app.FloatWindow.service.FloatWindowService;
import com.sjl.lbox.base.BaseActivity;

/**
 * 悬浮窗演示
 *
 * 需要权限<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
 * @author SJL
 * @date 2016/11/21 23:53
 */
public class FloatWindowActivity extends BaseActivity implements View.OnClickListener {

    private Button btnStartService;
    private Button btnStopService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_window);

        initView();
    }

    private void initView() {
        btnStartService = (Button) findViewById(R.id.btnStartService);
        btnStartService.setOnClickListener(this);
        btnStopService = (Button) findViewById(R.id.btnStopService);
        btnStopService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStartService:
                startService(new Intent(mContext, FloatWindowService.class));
                break;
            case R.id.btnStopService:
                stopService(new Intent(mContext,FloatWindowService.class));
                break;
        }
    }
}
