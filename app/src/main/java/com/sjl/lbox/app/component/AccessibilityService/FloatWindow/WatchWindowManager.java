package com.sjl.lbox.app.component.AccessibilityService.FloatWindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sjl.lbox.R;

/**
 * WatchWindowManager
 *
 * @author SJL
 * @date 2017/7/20
 */

public class WatchWindowManager {
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private View view;
    private boolean hasAddView = false;

    private static WatchWindowManager watchWindowManager;

    private WatchWindowManager(Context context) {
        windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                Build.VERSION.SDK_INT <= Build.VERSION_CODES.N ? WindowManager.LayoutParams.TYPE_TOAST : WindowManager.LayoutParams.TYPE_PHONE,
                0x18,
                PixelFormat.TRANSLUCENT
        );
        layoutParams.gravity = Gravity.LEFT + Gravity.TOP;
        view = LayoutInflater.from(context).inflate(R.layout.watch_window, null);
        hasAddView = false;
    }

    public static WatchWindowManager getInstance(Context context) {
        if (watchWindowManager == null) {
            watchWindowManager = new WatchWindowManager(context);
        }
        return watchWindowManager;
    }

    public void show(String text) {
        if(!hasAddView) {
            windowManager.addView(view, layoutParams);
            hasAddView = true;
        }
        ((TextView) view.findViewById(R.id.tvTop)).setText(text);
    }

    public void dismiss() {
        if(hasAddView) {
            windowManager.removeView(view);
            hasAddView = false;
        }
    }
}
