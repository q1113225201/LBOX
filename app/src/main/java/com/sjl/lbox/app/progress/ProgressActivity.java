package com.sjl.lbox.app.progress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.DialogUtil;
import com.sjl.lbox.view.dialog.ProgressDialog;
/**
 * 自定义进度条显示Activity
 *
 * @author SJL
 * @date 2016/8/22 22:41
 */
public class ProgressActivity extends BaseActivity implements View.OnClickListener {

    private Button btnShowProgressWithTime;

    private Button btnShowProgressMaxTime;

    private Button btnShowProgressCurrentTime;

    private int current = 0;

    private final int DISMISS = 0;
    private final int REFRESH = 1000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DISMISS:
                    DialogUtil.dismissLoadProgressDialog();
                    break;
                case REFRESH:
                    current+=10;
                    if(current<=100){
                        progressDialog.setCurrentProgress(current);
                        handler.sendEmptyMessageDelayed(REFRESH,1000);
                    }else{
                        handler.sendEmptyMessage(DISMISS);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        initView();
    }

    private void initView() {
        btnShowProgressWithTime = (Button) findViewById(R.id.btnShowProgressWithTime);
        btnShowProgressMaxTime = (Button) findViewById(R.id.btnShowProgressMaxTime);
        btnShowProgressCurrentTime = (Button) findViewById(R.id.btnShowProgressCurrentTime);

        btnShowProgressWithTime.setOnClickListener(this);
        btnShowProgressMaxTime.setOnClickListener(this);
        btnShowProgressCurrentTime.setOnClickListener(this);
    }

    private ProgressDialog progressDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShowProgressWithTime:
                progressDialog = DialogUtil.showLoadProgressDialog(mContext, false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DialogUtil.dismissLoadProgressDialog();
                    }
                }, 3000);
                break;
            case R.id.btnShowProgressMaxTime:
                progressDialog = DialogUtil.showLoadProgressDialog(mContext, false);
                progressDialog.setMaxProgress(5);
                break;
            case R.id.btnShowProgressCurrentTime:
                current=0;
                progressDialog = DialogUtil.showLoadProgressDialog(mContext, false);
                progressDialog.setCurrentProgress(current);
                handler.sendEmptyMessage(REFRESH);
                break;
        }
    }
}
