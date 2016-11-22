package com.sjl.lbox.app.FloatWindow.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.sjl.lbox.R;
import com.sjl.lbox.app.FloatWindow.manager.FloatWindowManager;
import com.sjl.lbox.util.AppUtil;
import com.sjl.lbox.util.ToastUtil;

/**
 * Created by SJL on 2016/11/21.
 */

public class FloatWindowSmall extends LinearLayout {
    private Context context;

    private int viewWidth;

    private int viewHeight;

    public WindowManager.LayoutParams layoutParams;

    public FloatWindowSmall(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.float_window_small, this);

        View view = findViewById(R.id.floatWindowSmallParent);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.x = AppUtil.getScreen(context).x - viewWidth / 2;
        layoutParams.y = AppUtil.getScreen(context).y / 2 - viewHeight / 2;
        //设置显示类型为phone
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //显示图片格式
        layoutParams.format = PixelFormat.RGBA_8888;
        //设置交互
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //设置对齐方式为左上
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.width = viewWidth;
        layoutParams.height = viewHeight;
        initView();
    }

    private void initView() {
        findViewById(R.id.ivFloatWindowSmall).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭小悬浮窗
                FloatWindowManager.getInstance(context).removeFloatWindowSmall();
                //显示大悬浮窗
                FloatWindowManager.getInstance(context).showFloatWindowBig(context);
            }
        });
    }
}
