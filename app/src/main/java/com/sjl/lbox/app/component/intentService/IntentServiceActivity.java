package com.sjl.lbox.app.component.intentService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.component.intentService.service.CustomeIntentService;
import com.sjl.lbox.base.BaseActivity;

/**
 * IntentService和LocalBroadcastManager综合使用
 *
 * @author SJL
 * @date 2016/12/14 21:29
 */
public class IntentServiceActivity extends BaseActivity implements View.OnClickListener {

    private Button btnDownload;
    private Button btnUpload;
    private LinearLayout llDescription;
    private TextView tvResult;

    private LocalBroadcastManager localBroadcastManager;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (CustomeIntentService.ACTION_RESULE.equals(intent.getAction())) {
                llDescription.setVisibility(View.GONE);
                tvResult.append(intent.getStringExtra(CustomeIntentService.RESULT) + "\n");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);

        initView();
    }

    private void initView() {
        btnDownload = (Button) findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(this);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(this);
        llDescription = (LinearLayout) findViewById(R.id.llDescription);
        tvResult = (TextView) findViewById(R.id.tvResult);

        localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        IntentFilter intentFilter = new IntentFilter(CustomeIntentService.ACTION_RESULE);
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnDownload:
                intent = new Intent(mContext, CustomeIntentService.class);
                intent.putExtra(CustomeIntentService.TYPE, CustomeIntentService.DOWNLOAD);
                startService(intent);
                break;
            case R.id.btnUpload:
                intent = new Intent(mContext, CustomeIntentService.class);
                intent.putExtra(CustomeIntentService.TYPE, CustomeIntentService.UPLOAD);
                startService(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }
}
