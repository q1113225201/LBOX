package com.sjl.lbox.app.FloatWindow.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.sjl.lbox.app.FloatWindow.manager.FloatWindowManager;
import com.sjl.lbox.util.AppUtil;
import com.sjl.lbox.util.LogUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 悬浮窗后台服务
 *
 * @author SJL
 * @date 2016/11/21 22:49
 */
public class FloatWindowService extends Service {

    private final  static String tag = FloatWindowService.class.getSimpleName();

    private Context context;

    private Handler handler = new Handler();

    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (timer == null) {
            timer = new Timer();
            //500毫秒后执行RefreshTask，第一次执行前等待0毫秒
            timer.schedule(new RefreshTask(), 0, 500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class RefreshTask extends TimerTask {

        @Override
        public void run() {
            if(AppUtil.getLatestPackage(context)==null){
                //最近运行的app包名为null执行刷新小悬浮窗操作
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //已显示悬浮窗，更新大悬浮窗
                        FloatWindowManager.getInstance(context).updateFloatWindowBig();
                    }
                });
                return;
            }
            if (AppUtil.isHome(context)) {
                //当前在桌面上则显示或更新悬浮窗
                if(FloatWindowManager.getInstance(context).isWindowShowing()){
                    //已显示悬浮窗，则更新大悬浮窗
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            FloatWindowManager.getInstance(context).updateFloatWindowBig();
                        }
                    });
                }else{
                    //没显示悬浮窗，则显示小悬浮窗
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.i(tag,"showFloatWindowSmall");
                            FloatWindowManager.getInstance(context).showFloatWindowSmall(context);
                        }
                    });
                }
            }else {
                //当前不在桌面则关闭所有悬浮窗
                if(FloatWindowManager.getInstance(context).isWindowShowing()){
                    //已显示悬浮窗，则关闭所有悬浮窗
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            FloatWindowManager.getInstance(context).removeAllFloatWindow();
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        LogUtil.i(tag,"onDestroy");
        timer.cancel();
        timer = null;
        FloatWindowManager.getInstance(context).removeAllFloatWindow();
        super.onDestroy();
    }
}
