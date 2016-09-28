package com.sjl.lbox.app.pedometer.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.sjl.lbox.app.pedometer.config.StepConfig;
import com.sjl.lbox.util.LogUtil;

/**
 * 计步服务
 *
 * @author SJL
 * @date 2016/9/27 21:27
 */
public class StepService extends Service {
    private static final String tag = StepService.class.getSimpleName();//测试
    private static int step = 0;
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            step++;
            LogUtil.i(tag, "当前步数：" + step);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    private Messenger messenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case StepConfig.MSG_FROM_CLIENT:
                    try {
                        Messenger messenger = msg.replyTo;
                        Message replyMsg = Message.obtain(null, StepConfig.MSG_FROM_SERVER);
                        replyMsg.obj = step;
                        messenger.send(replyMsg);
                        LogUtil.i(tag,"MSG_FROM_CLIENT");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                startStepDetector();
            }
        }).start();
    }

    /**
     * 开始计步探测
     */
    private void startStepDetector() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        step = 0;

        addCountStepListener();
    }

    /**
     * android4.4以后可以使用计步传感器
     * 计步传感器
     */
    private void addCountStepListener() {
        Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (detectorSensor != null) {
            sensorManager.registerListener(sensorEventListener, detectorSensor, SensorManager.SENSOR_DELAY_UI);
        } else if (countSensor != null) {
            sensorManager.registerListener(sensorEventListener, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            LogUtil.i(tag, "Count sensor not available!");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i(tag,"onBind");
        return messenger.getBinder();
    }
}
