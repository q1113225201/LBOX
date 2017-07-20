package com.sjl.lbox.app.component.AccessibilityService;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.listener.BaseListener;
import com.sjl.lbox.util.DialogUtil;

/**
 * 无障碍服务
 *
 * @author SJL
 * @date 2017/1/18
 */
public class AccessibilityServiceActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_ACTION_MANAGE_OVERLAY_PERMISSION = 11;
    private Button btnStartService;
    private Button btnFloatWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility_service);

        initView();
    }

    private void initView() {
        btnStartService = (Button) findViewById(R.id.btnStartService);
        btnStartService.setOnClickListener(this);
        btnFloatWindow = (Button) findViewById(R.id.btnFloatWindow);
        btnFloatWindow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStartService:
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            break;
            case R.id.btnFloatWindow:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //版本在6.0以上判断是否有创建悬浮窗的权限
                    //有，开启悬浮窗服务
                    //无，去开启创建悬浮窗的权限
                    if (Settings.canDrawOverlays(this)) {
                        startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                    } else {
                        DialogUtil.showConfirm(mContext, "提示", "显示悬浮窗需要开启悬浮窗显示权限，是否去开启？", "去开启", "取消", new BaseListener() {
                            @Override
                            public void baseListener(View v, String msg) {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                                startActivityForResult(intent,REQUEST_ACTION_MANAGE_OVERLAY_PERMISSION);
                            }
                        }, new BaseListener() {
                            @Override
                            public void baseListener(View v, String msg) {

                            }
                        }, true);
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_ACTION_MANAGE_OVERLAY_PERMISSION == requestCode) {
            btnFloatWindow.performClick();
        }
    }
}
