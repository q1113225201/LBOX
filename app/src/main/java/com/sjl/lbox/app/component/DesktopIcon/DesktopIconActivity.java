package com.sjl.lbox.app.component.DesktopIcon;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

import java.util.List;

/**
 * 桌面图标替换
 * <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>
 * 配置activity-alias
 * @author SJL
 * @date 2017/8/30
 */
public class DesktopIconActivity extends BaseActivity {
    private static final String TAG = "DesktopIconActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop_icon);

        initView();
    }

    private void initView() {
        findViewById(R.id.btnReplace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceIcon();
            }
        });
    }

    /**
     * 原始图标和替换图标文字进行切换
     */
    private void replaceIcon() {
        //原始
        ComponentName componentName1 = new ComponentName(mContext,"com.sjl.lbox.original");
        //替换
        ComponentName componentName2 = new ComponentName(mContext,"com.sjl.lbox.replace");
        PackageManager packageManager = getPackageManager();
        int componentEnable = packageManager.getComponentEnabledSetting(componentName1);
        if(componentEnable==PackageManager.COMPONENT_ENABLED_STATE_ENABLED||componentEnable==PackageManager.COMPONENT_ENABLED_STATE_DEFAULT){
            //原始alias有效或默认
            //原始alias失效，替换alias生效
            packageManager.setComponentEnabledSetting(componentName1,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
            packageManager.setComponentEnabledSetting(componentName2,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
        }else{
            //替换alias失效，原始alias生效
            packageManager.setComponentEnabledSetting(componentName2,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
            packageManager.setComponentEnabledSetting(componentName1,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
        }
        //重启应用，但在实际使用中好像没什么用，还是要过一会才起效
        //替换之后，桌面需要过段时间才能反应过来，在此期间想再打开系统会提示未安装应用
        ActivityManager activityManager = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,0);
        for (ResolveInfo resolveInfo : list){
            if(resolveInfo.activityInfo!=null){
                Log.i(TAG,resolveInfo.activityInfo.packageName);
                activityManager.killBackgroundProcesses(resolveInfo.activityInfo.packageName);
            }
        }
    }
}
