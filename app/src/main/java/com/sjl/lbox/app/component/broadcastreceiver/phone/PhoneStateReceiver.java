package com.sjl.lbox.app.component.broadcastreceiver.phone;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;

import com.sjl.lbox.util.LogUtil;

/**
 * 电话监听
 * <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>
 * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
 * 过滤器
 * <intent-filter>
 * <action android:name="android.intent.action.NEW_OUTGOING_CALL" />
 * <action android:name="android.intent.action.PHONE_STATE" />
 * </intent-filter>
 *
 * @author SJL
 * @date 2017/5/23 20:19
 */
public class PhoneStateReceiver extends BroadcastReceiver {
    private static final String TAG = "PhoneStateReceiver";
    public static final String ACTION = "PhoneStateReceiver";
    private static boolean isCallIn = false;
    private static String callNumber = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_NEW_OUTGOING_CALL.equalsIgnoreCase(intent.getAction())) {
            //拨打电话时会发送action为android.intent.action.NEW_OUTGOING_CALL的广播
            isCallIn = false;
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            sendMessage(context, "拨打电话：" + phoneNumber);
            LogUtil.i(TAG, "intent=" + intent.toString());
            LogUtil.i(TAG, "Extras=" + intent.getExtras().toString());
        } else {
            //电话状态改变时发送action为android.intent.action.PHONE_STATE的广播
            //来电
            isCallIn = true;
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            if (TelephonyManager.CALL_STATE_RINGING == telephonyManager.getCallState()) {
                //响铃
                callNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                sendMessage(context, "来电：" + callNumber);
            } else if (TelephonyManager.CALL_STATE_IDLE == telephonyManager.getCallState()) {
                //呼叫闲置
                sendMessage(context, "无状态：" + callNumber);
            } else if (TelephonyManager.CALL_STATE_OFFHOOK == telephonyManager.getCallState()) {
                //挂断
                sendMessage(context, "接起电话：" + callNumber);
            }
        }
    }

    private void sendMessage(Context context, String msg) {
        LogUtil.i(TAG, "sendMessage:" + msg);
        Intent intent = new Intent(ACTION);
        intent.putExtra("msg", msg);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
