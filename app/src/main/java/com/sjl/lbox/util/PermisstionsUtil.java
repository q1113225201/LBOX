package com.sjl.lbox.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.sjl.lbox.listener.BaseListener;

/**
 * Created by yanfa on 2016/8/4.
 */
@TargetApi(Build.VERSION_CODES.M)
public class PermisstionsUtil {
    private static String tag = PermisstionsUtil.class.getSimpleName();
    //拍照
    public static String CAMERA = Manifest.permission.CAMERA;
    public static int CAMERA_CODE = 0x1101;
    //电话
    public static String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static int CALL_PHONE_CODE = 0x1102;
    //读外部设备
    public static String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static int READ_EXTERNAL_STORAGE_CODE = 0x1103;
    //写外部设备
    public static String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static int WRITE_EXTERNAL_STORAGE_CODE = 0x1104;
    //写外部设备
    public static String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static int READ_CONTACTS_CODE = 0x1105;
    //定位
    public static String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static int ACCESS_FINE_LOCATION_CODE = 0x1106;

    private static Context mContext;
    private static int currentRequestCode;

    private static Boolean checkVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 检查权限
     *
     * @param context
     * @param permission
     * @param permissionCode
     * @return
     */
    public static void checkSelfPermission(Context context, String permission, int permissionCode, String msg, PermissionResult permissionResult) {
        mContext = context;
        PermisstionsUtil.currentRequestCode = permissionCode;
        PermisstionsUtil.permissionResult = permissionResult;
        if (checkVersion()) {
            //方式一
//            if (mContext.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {//无权限
//                if (((Activity)mContext).shouldShowRequestPermissionRationale(permission)) {
//                    //解释需要权限的原因
//                    showExplain(msg, permission, permissionCode);
//                } else {
//                    // 不解释直接请求权限
//                    ((Activity)mContext).requestPermissions(new String[]{permission}, permissionCode);
//                }
//            } else {
//                //有权限
//                if (PermisstionsUtil.permissionResult != null) {
//                    PermisstionsUtil.permissionResult.granted(permissionCode);
//                }
//            }
            //方式二
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {//无权限
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, permission)) {
                    //解释需要权限的原因
                    showExplain(msg, permission, permissionCode);
                } else {
                    // 不解释直接请求权限
                    ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, permissionCode);
                }
            } else {
                //有权限
                if (PermisstionsUtil.permissionResult != null) {
                    PermisstionsUtil.permissionResult.granted(permissionCode);
                }
            }
        }
    }

    /**
     * 向用户解释为什么需要该权限
     *
     * @param msg
     * @param permission
     * @param permissionCode
     */
    private static void showExplain(String msg, final String permission, final int permissionCode) {
        DialogUtil.showConfirm(mContext, "权限说明", msg, null, null, new BaseListener() {
            @Override
            public void baseListener(View v, String msg) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{permission}, permissionCode);
            }
        }, null, false);
    }

    /**
     * 请求权限回调（须在Activity权限回调中使用）
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (checkVersion()) {
            boolean isAllow = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (PermisstionsUtil.currentRequestCode == requestCode) {
                if (isAllow) {
                    if (permissionResult != null) {
                        permissionResult.granted(requestCode);
                    }
                } else {
                    ToastUtil.showToast(mContext, "权限被禁止，请手动开启。");
                }
            }
        }
    }

    private static PermissionResult permissionResult;

    public interface PermissionResult {
        //权限允许
        void granted(int requestCode);

        //权限拒绝
        void denied(int requestCode);
    }
}
