package com.sjl.lbox.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.sjl.lbox.app.lib.RxJava.RxBus;
import com.sjl.lbox.util.LogUtil;
import com.sjl.lbox.util.PermisstionUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Activity基类
 *
 * @author SJL
 * @date 2016/8/6 14:19
 */
public class BaseActivity extends Activity {
    private static final String TAG = "BaseActivity";
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mContext = this;
        requestPermission();
        EventBus.getDefault().register(this);
        RxBus.getInstance().subscribe(this, String.class, new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                if ("destory".equalsIgnoreCase(s)) {
                    finish();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                LogUtil.e(TAG, "RxBus accept error:" + throwable.getMessage());
            }
        });
    }

    private void requestPermission() {
        PermisstionUtil.requestPermissions(mContext, new String[]{PermisstionUtil.STORAGE}, PermisstionUtil.STORAGE_CODE, "SD卡读写权限", null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermisstionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Subscribe
    public void destory(String action) {
        if ("destory".equals(action)) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        RxBus.getInstance().unSubscribe(this);
    }
}
