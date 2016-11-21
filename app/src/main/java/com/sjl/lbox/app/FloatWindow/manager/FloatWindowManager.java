package com.sjl.lbox.app.FloatWindow.manager;


import android.content.Context;
import android.view.WindowManager;

import com.sjl.lbox.app.FloatWindow.view.FloatWindowSmall;

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

    public void showFloatWindowSmall(Context context) {
        if (floatWindowSmall == null) {
            floatWindowSmall = new FloatWindowSmall(context);
        }
        floatWindowManager = new FloatWindowManager(context);
        getWindowManager().addView(floatWindowSmall, floatWindowSmall.layoutParams);
    }

    public void removeFloatWindowSmall() {
        if (floatWindowSmall != null) {
            getWindowManager().removeView(floatWindowSmall);
            floatWindowSmall = null;
        }
    }

    public void removeAllFloatWindow(){
        removeFloatWindowSmall();
    }

    public boolean isWindowShowing(){
        return floatWindowSmall!=null;
    }

    private WindowManager getWindowManager() {
        if (windowManager == null) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }

}
