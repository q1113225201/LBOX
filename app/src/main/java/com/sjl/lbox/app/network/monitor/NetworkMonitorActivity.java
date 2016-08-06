package com.sjl.lbox.app.network.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.NetWorkUtil;

/**
 * 网络监听
 * 需要权限
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 * <uses-permission android:name="android.permission.INTERNET" />
 *
 * @author SJL
 * @date 2016/8/6 15:53
 */
public class NetworkMonitorActivity extends BaseActivity implements View.OnClickListener {

    private static String tag = NetworkMonitorActivity.class.getSimpleName();
    private TextView tvNetworkState;
    private TextView tvNetworkType;
    private TextView tvIp;
    private TextView tvMac;
    private TextView tvLinkSpeed;
    private Button btnRefresh;
    private Button btnOpenSetting;

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                initData();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_monitor);
        initView();
    }

    private void initView() {
        tvNetworkState = (TextView) findViewById(R.id.tvNetworkState);
        tvNetworkType = (TextView) findViewById(R.id.tvNetworkType);
        tvIp = (TextView) findViewById(R.id.tvIp);
        tvMac = (TextView) findViewById(R.id.tvMac);
        tvLinkSpeed = (TextView) findViewById(R.id.tvLinkSpeed);
        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnOpenSetting = (Button) findViewById(R.id.btnOpenSetting);

        btnRefresh.setOnClickListener(this);
        btnOpenSetting.setOnClickListener(this);

        initData();
        registerNetworkReceiver();
    }

    private void initData() {
        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            tvNetworkState.setText("网络可用");
        } else {
            tvNetworkState.setText("网络不可用");
        }
        String netWorkType = NetWorkUtil.getNetWorkName(mContext);
        tvNetworkType.setText((netWorkType == null ? "无网络" : netWorkType) + ",type:" + NetWorkUtil.getNetWorkType(mContext));
        tvIp.setText(NetWorkUtil.getIpAddress(mContext));
        tvMac.setText(NetWorkUtil.getMacAddress(mContext));
        tvLinkSpeed.setText(NetWorkUtil.getLinkSpeed(mContext) + "kb/s");
    }

    private void registerNetworkReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, intentFilter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRefresh:
                initData();
                break;
            case R.id.btnOpenSetting:
                NetWorkUtil.openSetting(mContext);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkReceiver);
    }
}
