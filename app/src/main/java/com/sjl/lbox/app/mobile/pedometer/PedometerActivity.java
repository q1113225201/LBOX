package com.sjl.lbox.app.mobile.pedometer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.mobile.pedometer.config.StepConfig;
import com.sjl.lbox.app.mobile.pedometer.service.StepService;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.LogUtil;

/**
 * 计步器
 *
 * @author SJL
 * @date 2016/9/27 21:07
 */
public class PedometerActivity extends BaseActivity {
    private static final String tag = PedometerActivity.class.getSimpleName();

    //循环取当前时刻的步数的间隔时间
    private long TIME_INTERVAL = 500;

    private TextView tvCount;

    private Messenger messenger;
    private Messenger getReplyMessenger = new Messenger(new StepHandler());

    private StepHandler stepHandler = new StepHandler();
    private class StepHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case StepConfig.MSG_FROM_SERVER:
                    //更新界面
                    tvCount.setText(msg.obj.toString());
                    stepHandler.sendEmptyMessageDelayed(StepConfig.REQUEST_SERVER,TIME_INTERVAL);
                    LogUtil.i(tag,"MSG_FROM_SERVER");
                    break;
                case StepConfig.REQUEST_SERVER:
                    //请求服务
                    try {
                        Message message = Message.obtain(null,StepConfig.MSG_FROM_CLIENT);
                        message.replyTo = getReplyMessenger;
                        messenger.send(message);
                        LogUtil.i(tag,"REQUEST_SERVER");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                messenger = new Messenger(service);
                Message message = Message.obtain(null,StepConfig.MSG_FROM_CLIENT);
                message.replyTo = getReplyMessenger;
                messenger.send(message);
                LogUtil.i(tag,"onServiceConnected");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        initView();
    }

    private void initView() {
        tvCount = (TextView) findViewById(R.id.tvCount);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, StepService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        stepHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
