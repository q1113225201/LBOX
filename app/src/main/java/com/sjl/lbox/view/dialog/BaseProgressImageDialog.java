package com.sjl.lbox.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.util.LogUtil;

/**
 * Created by yanfa on 2016/6/28.
 */
public class BaseProgressImageDialog extends Dialog {
    private final String tag = BaseProgressImageDialog.class.getSimpleName();
    private Context mContext;
    private Boolean cancelableOnTouchOutside = false;

    private View view;

    private TextView tvProgress;

    private final int TIME = 1000;
    private int currentSecond = 0;
    private int maxTime = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TIME) {
                currentSecond++;
                if (currentSecond == maxTime) {
                    tvProgress.setText("100%");
                    handler.removeMessages(TIME);
                    return;
                }
                if (tvProgress != null) {
                    String currentProgress = (int) (currentSecond * 100 / maxTime) + "%";
                    LogUtil.i(tag, "当前进度：" + currentProgress);
                    tvProgress.setText(currentProgress);
                }
                handler.sendEmptyMessageDelayed(TIME, TIME);
            }
        }
    };

    public void setMaxTime(int maxTime) {
        if (maxTime <= 0) {
            maxTime = 1;
        }
        this.maxTime = maxTime;
        if (tvProgress != null) {
            tvProgress.setText("0%");
            tvProgress.setVisibility(View.VISIBLE);
        }
        handler.sendEmptyMessageDelayed(TIME, TIME);
    }

    public void setCurrentProgress(int progress) {
        if (tvProgress != null) {
            if (progress == 100) {
                handler.removeMessages(TIME);
            }
            tvProgress.setText(progress + "%");
            tvProgress.setVisibility(View.VISIBLE);
        }
    }

    private int layoutId = -1;

    public BaseProgressImageDialog(Context context) {
        super(context, R.style.baseDialogStyle);
        this.mContext = context;
        this.cancelableOnTouchOutside = false;
        setCanceledOnTouchOutside(cancelableOnTouchOutside);
    }

    public BaseProgressImageDialog(Context context, int layoutId) {
        super(context, R.style.baseDialogStyle);
        this.mContext = context;
        this.layoutId = layoutId;
        this.cancelableOnTouchOutside = false;
        setCanceledOnTouchOutside(cancelableOnTouchOutside);
    }

    public BaseProgressImageDialog(Context context, int layoutId, boolean cancelableOnTouchOutside) {
        super(context, R.style.baseDialogStyle);
        this.mContext = context;
        this.layoutId = layoutId;
        this.cancelableOnTouchOutside = cancelableOnTouchOutside;
        setCanceledOnTouchOutside(cancelableOnTouchOutside);
    }

    private void setDefalutLayout() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.dialog_progress_circle, null);
    }

    public void setLayout(int layout) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(layout, null);
    }

    @Override
    public void show() {
        if (layoutId == -1) {
            setDefalutLayout();
        } else {
            setLayout(layoutId);
        }
        setContentView(view);
        Window window = this.getWindow();
        WindowManager.LayoutParams windowWM = window.getAttributes();
        window.setAttributes(windowWM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        findView();
        super.show();
    }

    private ProgressBar progressBar;

    private void findView() {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        tvProgress = (TextView) view.findViewById(R.id.tvProgress);
    }

    public void setProgressBarBackground(int bgResource) {
        progressBar.setBackgroundResource(bgResource);
    }

    public void setProgressBarBackground(Drawable drawable) {
        progressBar.setBackground(drawable);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (cancelableOnTouchOutside) {
                dismiss();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        handler.removeMessages(TIME);
        super.setOnDismissListener(listener);
    }
}
