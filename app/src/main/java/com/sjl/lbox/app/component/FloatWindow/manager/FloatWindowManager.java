package com.sjl.lbox.app.component.FloatWindow.manager;


import android.content.Context;
import android.view.WindowManager;

import com.sjl.lbox.app.component.FloatWindow.view.FloatWindowBig;
import com.sjl.lbox.app.component.FloatWindow.view.FloatWindowSmall;

/**
 * 悬浮窗管理类
 *
 * @author SJL
 * @date 2016/11/21 22:48
 */
public class FloatWindowManager {

    private WindowManager windowManager;

    private static FloatWindowManager floatWindowManager;

    public static FloatWindowManager getInstance(Context context) {
        if (floatWindowManager == null) {
            floatWindowManager = new FloatWindowManager(context);
        }
        return floatWindowManager;
    }

    private Context context;

    public FloatWindowManager(Context context) {
        this.context = context;
    }

    private static FloatWindowSmall floatWindowSmall;

    /**
     * 显示小悬浮窗
     *
     * @param context
     */
    public void showFloatWindowSmall(Context context) {
        if (floatWindowSmall == null) {
            floatWindowSmall = new FloatWindowSmall(context);
            this.context = context;
        }
        //floatWindowManager = new FloatWindowManager(context);
        getWindowManager().addView(floatWindowSmall, floatWindowSmall.layoutParams);
    }

    /**
     * 关闭小悬浮窗
     */
    public void removeFloatWindowSmall() {
        if (floatWindowSmall != null) {
            getWindowManager().removeView(floatWindowSmall);
            floatWindowSmall = null;
        }
    }

    private static FloatWindowBig floatWindowBig;

    /**
     * 显示大悬浮窗
     *
     * @param context
     */
    public void showFloatWindowBig(Context context) {
        if (floatWindowBig == null) {
            floatWindowBig = new FloatWindowBig(context);
            this.context = context;
        }
        getWindowManager().addView(floatWindowBig, floatWindowBig.layoutParams);
    }

    public void updateFloatWindowBig(){
        if(floatWindowBig!=null){
            floatWindowBig.update();
        }
    }

    /**
     * 关闭大悬浮窗
     */
    public void removeFloatWindowBig() {
        if (floatWindowBig != null) {
            getWindowManager().removeView(floatWindowBig);
            floatWindowBig = null;
        }
    }

    /**
     * 关闭所有悬浮窗
     */
    public void removeAllFloatWindow() {
        removeFloatWindowSmall();
        removeFloatWindowBig();
    }

    /**
     * 返回是否有悬浮窗显示
     *
     * @return
     */
    public boolean isWindowShowing() {
        return floatWindowSmall != null || floatWindowBig != null;
    }

    private WindowManager getWindowManager() {
        if (windowManager == null) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }

}
