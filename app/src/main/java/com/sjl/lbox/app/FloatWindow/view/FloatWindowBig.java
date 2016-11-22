package com.sjl.lbox.app.FloatWindow.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.FloatWindow.manager.FloatWindowManager;
import com.sjl.lbox.util.AppUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by SJL on 2016/11/21.
 */

public class FloatWindowBig extends LinearLayout {
    private Context context;

    private int viewWidth;

    private int viewHeight;

    public WindowManager.LayoutParams layoutParams;

    public FloatWindowBig(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.float_window_big, this);

        View view = findViewById(R.id.floatWindowBigParent);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.x = AppUtil.getScreen(context).x - viewWidth;
        layoutParams.y = AppUtil.getScreen(context).y/2 - viewHeight / 2;
        layoutParams.width = viewWidth;
        layoutParams.height = viewHeight;
        //对齐方式
        layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        //交互设置
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //设置显示类型为phone
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //显示图片格式
        layoutParams.format = PixelFormat.RGBA_8888;
        initView();
    }

    private void initView() {
        findViewById(R.id.btnFloatWindowBigBack).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭大悬浮窗
                FloatWindowManager.getInstance(context).removeFloatWindowBig();
                //显示小悬浮窗
                FloatWindowManager.getInstance(context).showFloatWindowSmall(context);
            }
        });
        ((TextView) findViewById(R.id.tvFloatWindowBigTime)).setText(new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date()));
    }

    public void update(){
        ((TextView) findViewById(R.id.tvFloatWindowBigTime)).setText(new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date()));
    }
}
