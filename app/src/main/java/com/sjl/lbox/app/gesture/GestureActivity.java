package com.sjl.lbox.app.gesture;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.view.gesture.GestureView;

/**
 * 手势密码演示
 *
 * @author SJL
 * @date 2016/10/16 21:28
 */
public class GestureActivity extends BaseActivity {

    private static final int SETTING = 1;
    private static final int OPEN = 2;
    private Button btnSetting;
    private Button btnOpen;
    private TextView tvResult;
    private GestureView gestureView;

    private int status;
    private String settingGesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);

        initView();
    }

    private void initView() {
        btnSetting = (Button) findViewById(R.id.btnSetting);
        btnOpen = (Button) findViewById(R.id.btnOpen);
        tvResult = (TextView) findViewById(R.id.tvResult);
        gestureView = (GestureView) findViewById(R.id.gestureView);
        gestureView.setOnDrawFinishedListener(new GestureView.OnDrawFinishedListener() {
            @Override
            public boolean onDrawFinished(final GestureView gestureView, String selectNum) {
                if (status == SETTING) {
                    if (settingGesture == null) {
                        settingGesture = selectNum;
                        tvResult.setText("再次输入");
                    } else {
                        if (selectNum.equals(settingGesture)) {
                            tvResult.setText("手势密码设置成功");
                            gestureView.success();
                        } else {
                            tvResult.setText("两次手势密码不一致");
                            gestureView.error();
                        }
                    }
                }else if(status == OPEN){
                    if (settingGesture == null) {
                        tvResult.setText("请先设置手势密码");
                    } else {
                        if (selectNum.equals(settingGesture)) {
                            tvResult.setText("手势密码正确");
                            gestureView.success();
                        } else {
                            tvResult.setText("手势密码错误");
                            gestureView.error();
                        }
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gestureView.reset();
                    }
                },1000);
                return false;
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = SETTING;
                settingGesture = null;
                tvResult.setText("设置手势密码");
            }
        });
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = OPEN;
                tvResult.setText("请输入手势密码");
            }
        });
        btnSetting.performClick();
    }
}
