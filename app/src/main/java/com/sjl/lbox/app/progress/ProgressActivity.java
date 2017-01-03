package com.sjl.lbox.app.progress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sjl.lbox.R;
import com.sjl.lbox.app.progress.view.CircleProgress;
import com.sjl.lbox.app.progress.view.NodeProgress;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.DialogUtil;
import com.sjl.lbox.util.ToastUtil;
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
    private Button btnProgressSetting;
    private Button btnProgressRunning;
    private EditText etProgressValue;
    private CircleProgress circleProgress;
    private Button btnARC;
    private Button btnLINE;
    private Button btnNodeProgressRun;
    private NodeProgress nodeProgress;

    private int current = 0;
    private int currentNodeProgress = 0;

    private final int DISMISS = 0;
    private final int REFRESH = 1000;
    private final int PROGRESS = 10;
    private final int NODE_PROGRESS = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DISMISS:
                    DialogUtil.dismissLoadProgressDialog();
                    break;
                case REFRESH:
                    current += 10;
                    if (current <= 100) {
                        progressDialog.setCurrentProgress(current);
                        handler.sendEmptyMessageDelayed(REFRESH, 1000);
                    } else {
                        handler.sendEmptyMessage(DISMISS);
                    }
                    break;
                case PROGRESS:
                    if (current < 360) {
                        current++;
                        circleProgress.setProgressValue(current);
                        circleProgress.setText(current + "");
                        handler.sendEmptyMessageDelayed(PROGRESS, PROGRESS);
                    } else {
                        circleProgress.setProgressValue(0);
                    }
                    break;
                case NODE_PROGRESS:
                    if (currentNodeProgress < 100) {
                        currentNodeProgress++;
                        nodeProgress.setCurrentProgress(currentNodeProgress);
                        handler.sendEmptyMessageDelayed(NODE_PROGRESS, PROGRESS);
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
        btnProgressSetting = (Button) findViewById(R.id.btnProgressSetting);
        btnProgressRunning = (Button) findViewById(R.id.btnProgressRunning);
        //圆弧进度
        etProgressValue = (EditText) findViewById(R.id.etProgressValue);
        circleProgress = (CircleProgress) findViewById(R.id.circleProgress);
        //设置初始进度
        circleProgress.setProgressValue(Float.parseFloat(etProgressValue.getText().toString()));

        //节点进度
        btnARC = (Button) findViewById(R.id.btnARC);
        btnLINE = (Button) findViewById(R.id.btnLINE);
        btnNodeProgressRun = (Button) findViewById(R.id.btnNodeProgressRun);
        nodeProgress = (NodeProgress) findViewById(R.id.nodeProgress);

        btnShowProgressWithTime.setOnClickListener(this);
        btnShowProgressMaxTime.setOnClickListener(this);
        btnShowProgressCurrentTime.setOnClickListener(this);
        btnProgressSetting.setOnClickListener(this);
        btnProgressRunning.setOnClickListener(this);
        btnARC.setOnClickListener(this);
        btnLINE.setOnClickListener(this);
        btnNodeProgressRun.setOnClickListener(this);
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
                current = 0;
                progressDialog = DialogUtil.showLoadProgressDialog(mContext, false);
                progressDialog.setCurrentProgress(current);
                handler.sendEmptyMessage(REFRESH);
                break;
            case R.id.btnProgressSetting:
                try {
                    Float value = Float.parseFloat(etProgressValue.getText().toString());
                    circleProgress.setProgressValue(value);
                } catch (Exception e) {
                    ToastUtil.showToast(mContext, "请输入合法进度");
                }
                break;
            case R.id.btnProgressRunning:
                current = 0;
                circleProgress.setProgressMax(360);
                handler.sendEmptyMessageDelayed(PROGRESS, PROGRESS);
                break;
            case R.id.btnARC:
                nodeProgress.setProgressStyle(NodeProgress.ARC);
                break;
            case R.id.btnLINE:
                nodeProgress.setProgressStyle(NodeProgress.LINE);
                break;
            case R.id.btnNodeProgressRun:
                currentNodeProgress = 0;
                handler.sendEmptyMessageDelayed(NODE_PROGRESS, PROGRESS);
                break;
        }
    }
}
