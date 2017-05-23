package com.sjl.lbox.app.component.broadcastreceiver.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.LogUtil;

/**
 * 电话状态监听
 *
 * @author SJL
 * @date 2017/5/23 20:16
 */
public class PhoneStateReceiverActivity extends BaseActivity {
    private static final String TAG = "PhoneStateReceiverActiv";
    private TextView tvReceiver;
    private TextView tvListener;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (PhoneStateReceiver.ACTION.equalsIgnoreCase(intent.getAction())) {
                tvReceiver.append("\n"+intent.getStringExtra("msg"));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_state_receiver);
        initView();
    }

    private void initView() {
        tvReceiver = (TextView) findViewById(R.id.tvReceiver);
        tvListener = (TextView) findViewById(R.id.tvListener);

        IntentFilter intentFilter = new IntentFilter(PhoneStateReceiver.ACTION);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(broadcastReceiver, intentFilter);

        listenerPhone();
    }

    private void listenerPhone() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state){
                    case TelephonyManager.CALL_STATE_IDLE:
                        tvListener.append("\n无状态："+incomingNumber);
                        LogUtil.i(TAG,"无状态:"+incomingNumber);
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        tvListener.append("\n接起电话："+incomingNumber);
                        LogUtil.i(TAG,"接起电话:"+incomingNumber);
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        tvListener.append("\n来电："+incomingNumber);
                        LogUtil.i(TAG,"来电:"+incomingNumber);
                        break;
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        },PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(broadcastReceiver);
    }
}
