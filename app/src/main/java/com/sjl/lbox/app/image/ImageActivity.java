package com.sjl.lbox.app.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ImageUtil;
import com.sjl.lbox.util.PermisstionsUtil;
import com.sjl.lbox.util.PictureUtil;
import com.sjl.lbox.util.ToastUtil;

/**
 * 图片处理
 *
 * @author SJL
 * @date 2016/8/11 23:43
 */
public class ImageActivity extends BaseActivity {

    private ImageView iv;
    private Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initView();
    }

    private void initView() {
        iv = (ImageView) findViewById(R.id.iv);
        btnClear = (Button) findViewById(R.id.btnClear);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermisstionsUtil.checkSelfPermission(mContext, PermisstionsUtil.CAMERA, PermisstionsUtil.CAMERA_CODE, "图片选择需要拍照权限。", new PermisstionsUtil.PermissionResult() {
                    @Override
                    public void granted(int requestCode) {
                        choosePicture();
                    }

                    @Override
                    public void denied(int requestCode) {
                        ToastUtil.showToast(mContext, "拍照权限被拒绝");
                    }
                });
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureUtil.clearCache();
            }
        });
        //初始化
        PictureUtil.getInstance(this);
    }

    private void choosePicture() {
        PictureUtil.showPop(new PictureUtil.OnPopDismiss() {
            @Override
            public void dismiss(Boolean flag) {

            }
        });
        requestPermission();
    }

    private void requestPermission() {
        PermisstionsUtil.checkSelfPermission(mContext, PermisstionsUtil.CAMERA, PermisstionsUtil.CAMERA_CODE, "图片选择需要拍照权限。", null);
        PermisstionsUtil.checkSelfPermission(mContext, PermisstionsUtil.WRITE_EXTERNAL_STORAGE, PermisstionsUtil.WRITE_EXTERNAL_STORAGE_CODE, "SD卡读写权限。", null);
        PermisstionsUtil.checkSelfPermission(mContext, PermisstionsUtil.READ_EXTERNAL_STORAGE, PermisstionsUtil.READ_EXTERNAL_STORAGE_CODE, "SD卡读写权限", null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            PictureUtil.onActivityResult(requestCode, resultCode, data, new PictureUtil.BitmapLoadCallBack() {
                @Override
                public void bitmapLoadSuccess(Bitmap bitmap, Uri uri) throws Exception {
                    iv.setImageBitmap(ImageUtil.getCircleBitmap(bitmap));
                }

                @Override
                public void bitmapLoadFailure(String error) throws Exception {
                    ToastUtil.showToast(mContext, error);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
