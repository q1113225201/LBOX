package com.sjl.lbox;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
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
        //ARouter初始化
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(app); // 尽可能早，推荐在Application中初始化
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //加载dex分包
        MultiDex.install(this);
    }
}
