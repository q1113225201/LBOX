package com.sjl.lbox.app.ui.FloatWindow;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.FloatWindow.service.FloatWindowService;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.listener.BaseListener;
import com.sjl.lbox.util.DialogUtil;

/**
 * 悬浮窗演示
 * <p>
 * 需要权限<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
 *
 * @author SJL
 * @date 2016/11/21 23:53
 */
public class FloatWindowActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_ACTION_USAGE_ACCESS_SETTINGS = 10;
    private static final int REQUEST_ACTION_MANAGE_OVERLAY_PERMISSION = 11;

    private Button btnStartService;
    private Button btnStopService;
    private Button btnAuthorizationUsage;
    private Button btnAuthorizationFloatWindow;

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
        btnAuthorizationUsage = (Button) findViewById(R.id.btnAuthorizationUsage);
        btnAuthorizationUsage.setOnClickListener(this);
        btnAuthorizationFloatWindow = (Button) findViewById(R.id.btnAuthorizationFloatWindow);
        btnAuthorizationFloatWindow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartService:
                //开启悬浮窗服务
                //检查是否有查看使用情况的权限
                if (checkUsagePermission()) {
                    //有，开启悬浮窗服务
                    startFloatWindowService();
                } else {
                    //无，去开启
                    openUsagePermission();
                }
                break;
            case R.id.btnStopService:
                //停止悬浮窗服务
                stopService(new Intent(mContext, FloatWindowService.class));
                break;
            case R.id.btnAuthorizationUsage:
                //授权查看应用使用情况权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                } else {
                    Toast.makeText(this, "只有4.4以上手机需要查看应用使用情况权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnAuthorizationFloatWindow:
                //授权悬浮窗
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION));
                } else {
                    Toast.makeText(this, "只有6.0以上手机需要开启悬浮窗权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 查看使用情况授权
     *
     * @return
     */
    private boolean checkUsagePermission() {
        AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = AppOpsManager.MODE_ALLOWED;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    /**
     * 打开查看应用使用情况授权
     */
    private void openUsagePermission() {
        /*new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("请开启查看应用使用情况的权限")
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //In some cases, a matching Activity may not exist, so ensure you safeguard against this.
                        startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),REQUEST_ACTION_USAGE_ACCESS_SETTINGS);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();*/
        DialogUtil.showConfirm(mContext, "提示", "请开启查看应用使用情况的权限", "去开启", "取消", new BaseListener() {
            @Override
            public void baseListener(View v, String msg) {
                //In some cases, a matching Activity may not exist, so ensure you safeguard against this.
                startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), REQUEST_ACTION_USAGE_ACCESS_SETTINGS);
            }
        }, null, true);
    }

    /**
     * 开启悬浮窗服务
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void startFloatWindowService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //版本在6.0以上判断是否有创建悬浮窗的权限
            //有，开启悬浮窗服务
            //无，去开启创建悬浮窗的权限
            if (Settings.canDrawOverlays(this)) {
                startService(new Intent(this, FloatWindowService.class));
                Toast.makeText(this, "开启悬浮窗服务成功", Toast.LENGTH_SHORT).show();
            } else {
                openOverlayPermission();
            }
        } else {
            startService(new Intent(this, FloatWindowService.class));
            Toast.makeText(this, "开启悬浮窗服务成功", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开悬浮窗权限
     */
    private void openOverlayPermission() {
        /*new AlertDialog.Builder(this)
                .setTitle("权限请求")
                .setMessage("显示悬浮窗需要开启悬浮窗显示权限，是否去开启？")
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        startActivityForResult(intent, REQUEST_ACTION_MANAGE_OVERLAY_PERMISSION);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();*/
        DialogUtil.showConfirm(mContext, "提示", "显示悬浮窗需要开启悬浮窗显示权限，是否去开启？", "去开启", "取消", new BaseListener() {
            @Override
            public void baseListener(View v, String msg) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivityForResult(intent, REQUEST_ACTION_MANAGE_OVERLAY_PERMISSION);
            }
        }, new BaseListener() {
            @Override
            public void baseListener(View v, String msg) {

            }
        }, true);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_ACTION_USAGE_ACCESS_SETTINGS == requestCode) {
            if (checkUsagePermission()) {
                startFloatWindowService();
            }
        } else if (REQUEST_ACTION_MANAGE_OVERLAY_PERMISSION == requestCode) {
            startFloatWindowService();
        }
    }
}
