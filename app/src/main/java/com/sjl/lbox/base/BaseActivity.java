package com.sjl.lbox.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.sjl.lbox.util.PermisstionUtil;

/**
 * Activity基类
 *
 * @author SJL
 * @date 2016/8/6 14:19
 */
public class BaseActivity extends Activity {

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mContext = this;
        requestPermission();
    }

    private void requestPermission() {
        PermisstionUtil.requestPermissions(mContext, new String[]{PermisstionUtil.STORAGE}, PermisstionUtil.STORAGE_CODE, "SD卡读写权限", null);
//        PermisstionsUtil.checkSelfPermission(mContext, PermisstionsUtil.WRITE_EXTERNAL_STORAGE, PermisstionsUtil.WRITE_EXTERNAL_STORAGE_CODE, "SD卡读写权限。", null);
//        PermisstionsUtil.checkSelfPermission(mContext, PermisstionsUtil.READ_EXTERNAL_STORAGE, PermisstionsUtil.READ_EXTERNAL_STORAGE_CODE, "SD卡读写权限", null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermisstionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PermisstionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
