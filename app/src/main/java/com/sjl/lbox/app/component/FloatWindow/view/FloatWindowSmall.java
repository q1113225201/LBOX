package com.sjl.lbox.app.component.FloatWindow.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.sjl.lbox.R;
import com.sjl.lbox.app.component.FloatWindow.manager.FloatWindowManager;
import com.sjl.lbox.util.AppUtil;
import com.sjl.lbox.util.LogUtil;

/**
 * Created by SJL on 2016/11/21.
 */

public class FloatWindowSmall extends LinearLayout {

    private final static String tag = FloatWindowSmall.class.getSimpleName();

    private Context context;

    private WindowManager windowManager;

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
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
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
    }

    private float startX = -1;
    private float startY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                LogUtil.i(tag,String.format("ACTION_DOWN:%f,%f",startX,startY));
                break;
            case MotionEvent.ACTION_MOVE:
                layoutParams.x = (int) (event.getRawX() - startX);
                layoutParams.y = (int) (event.getRawY() - startY);
                windowManager.updateViewLayout(FloatWindowSmall.this, layoutParams);
                LogUtil.i(tag,String.format("ACTION_MOVE:%d,%d",layoutParams.x,layoutParams.y));
                break;
            case MotionEvent.ACTION_UP:
                if (event.getX() == startX && event.getY() == startY) {
                    //点击事件
                    //关闭小悬浮窗
                    FloatWindowManager.getInstance(context).removeFloatWindowSmall();
                    //显示大悬浮窗
                    FloatWindowManager.getInstance(context).showFloatWindowBig(context);
                }
                LogUtil.i(tag,String.format("ACTION_MOVE:%d,%d",layoutParams.x,layoutParams.y));
                break;
        }
        return super.onTouchEvent(event);
    }
}
