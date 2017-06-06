package com.sjl.lbox.app.component.broadcastreceiver.phone;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.sjl.lbox.util.LogUtil;

/**
 * 电话监听
 * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
 * 过滤器
 * <intent-filter>
 * <action android:name="android.intent.action.PHONE_STATE" />
 * </intent-filter>
 *
 * @author SJL
 * @date 2017/5/23 20:19
 */
public class PhoneStateReceiver extends BroadcastReceiver {
    private static final String TAG = "PhoneStateReceiver";
    public static final String ACTION = "PhoneStateReceiver";
    public static String callState = "";
    private static String callNumber = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (TextUtils.equals(state, TelephonyManager.EXTRA_STATE_RINGING)) {
            //来电
            if (TextUtils.equals(callState, TelephonyManager.EXTRA_STATE_RINGING)) {
                return;
            }
            callState = state;
            callNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            sendMessage(context, "来电：" + callNumber);
        }else if(TextUtils.equals(state, TelephonyManager.EXTRA_STATE_OFFHOOK)){
            //接听
            callState = state;
            sendMessage(context, "接听电话：" + callNumber);
        }else if(TextUtils.equals(state,TelephonyManager.EXTRA_STATE_IDLE)){
            //挂断
            callState = state;
            sendMessage(context, "挂断：" + callNumber);
        }else{
            callState = state;
        }
    }

    private void sendMessage(Context context, String msg) {
        LogUtil.i(TAG, "sendMessage:" + msg);
        Intent intent = new Intent(ACTION);
        intent.putExtra("msg", msg);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
