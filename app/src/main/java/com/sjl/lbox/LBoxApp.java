package com.sjl.lbox;

import android.app.Application;

import com.yolanda.nohttp.NoHttp;

/**
 * Application
 *
 * @author SJL
 * @date 2016/8/6 14:13
 */
public class LBoxApp extends Application {
    public static LBoxApp app;

    public static LBoxApp getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        init();
    }

    private void init() {
        NoHttp.initialize(app);
    }
}
