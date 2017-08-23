package com.sjl.lbox.app.component.WallpaperService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.PermisstionUtil;
import com.sjl.lbox.util.ToastUtil;

public class WallpaperActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        initView();
    }

    private void initView() {
        findViewById(R.id.btnChooseWallpaper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
                startActivity(Intent.createChooser(intent, "选择壁纸"));
            }
        });

        PermisstionUtil.requestPermissions(mContext, new PermisstionUtil.OnPermissionResult() {
            @Override
            public void granted(int requestCode) {

            }

            @Override
            public void denied(int requestCode) {
                ToastUtil.showToast(mContext,"拍照权限被禁止");
            }
        },"正在请求拍照权限",10,PermisstionUtil.CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
