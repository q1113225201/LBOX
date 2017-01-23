package com.sjl.lbox.app.ui.CustomView.magnifier;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.magnifier.view.Magnifier;
import com.sjl.lbox.base.BaseActivity;

/**
 * 放大镜展示
 *
 * @author SJL
 * @date 2017/1/23
 */
public class MagnifierActivity extends BaseActivity {
    private Magnifier magnifier;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnifier);

        initView();
    }

    private void initView() {
        magnifier = (Magnifier) findViewById(R.id.magnifier);
        iv = (ImageView) findViewById(R.id.iv);
        magnifier.setBitmap(((BitmapDrawable)iv.getDrawable()).getBitmap());
//        magnifier.setBitmap(takeScreenShot(this));
        magnifier.setVisibility(View.VISIBLE);
    }

    // 获取指定Activity的截屏，保存到png文件
    private static Bitmap takeScreenShot(Activity activity) {

        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        System.out.println(statusBarHeight);

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        // 去掉标题栏
        Bitmap b = Bitmap.createBitmap(bitmap, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }
}
