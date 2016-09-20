package com.sjl.lbox.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjl.lbox.R;

/**
 * 自定义进度条
 *
 * @author SJL
 * @date 2016/8/22 22:41
 */
public class ProgressDialog extends Dialog {

    private Context mContext;

    private View view;

    private ImageView ivProgressFrame;

    private ImageView ivProgressBar;

    private TextView tvProgress;

    private Boolean cancelableOnTouchOutside;

    private int maxProgress;

    private int currentProgress;

    private int REFRESH = 0x10;
    private ProgressHandler handler = new ProgressHandler();

    private class ProgressHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == REFRESH) {
                int current = currentProgress * 100 / maxProgress;
                if (current > 100) {
                    tvProgress.setText("100%");
                    dismiss();
                    return;
                }
                currentProgress++;
                tvProgress.setText(current + "%");
                handler.sendEmptyMessageDelayed(REFRESH, 1000);
            }
        }
    }

    public ProgressDialog(Context context) {
        super(context, R.style.baseDialogStyle);
        this.mContext = context;
        this.cancelableOnTouchOutside = false;
        setCanceledOnTouchOutside(false);
    }

    public ProgressDialog(Context context, boolean cancelableOnTouchOutside) {
        super(context, R.style.baseDialogStyle);
        this.mContext = context;
        this.cancelableOnTouchOutside = cancelableOnTouchOutside;
        setCanceledOnTouchOutside(cancelableOnTouchOutside);
    }

    private void setDefalutLayout() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.dialog_progress, null);
    }

    public void setLayout(int id) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(id, null);
    }

    @Override
    public void show() {
        if (view == null) {
            setDefalutLayout();
        }
        setContentView(view);
        Window window = this.getWindow();
        WindowManager.LayoutParams windowWM = window.getAttributes();
        window.setAttributes(windowWM);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        findView();
        super.show();
    }

    private void findView() {
        ivProgressFrame = (ImageView) view.findViewById(R.id.ivProgressFrame);
        ivProgressBar = (ImageView) view.findViewById(R.id.ivProgressBar);
        tvProgress = (TextView) view.findViewById(R.id.tvProgress);
        tvProgress.setVisibility(View.INVISIBLE);
        startAnimate();
    }

    private RotateAnimation animation;

    private void startAnimate() {
        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);//设置动画持续时间
        animation.setRepeatCount(Animation.INFINITE);// 设置重复次数，这里是无限
        animation.setRepeatMode(Animation.RESTART);// 设置重复模式
        // 匀速转动的代码
        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);
        ivProgressBar.startAnimation(animation);
    }

    private void stopAnimate() {
        if (animation != null) {
            animation.cancel();
            animation = null;
        }
    }

    public ImageView getIvProgressFrame() {
        return ivProgressFrame;
    }

    public ImageView getIvProgressBar() {
        return ivProgressBar;
    }

    public TextView getTvProgress() {
        return tvProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        this.currentProgress = 0;
        if (tvProgress.getVisibility() != View.VISIBLE) {
            tvProgress.setVisibility(View.VISIBLE);
        }
        tvProgress.setText("0%");
        handler.sendEmptyMessageDelayed(REFRESH, 1000);
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        if (tvProgress.getVisibility() != View.VISIBLE) {
            tvProgress.setVisibility(View.VISIBLE);
        }
        tvProgress.setText(currentProgress + "%");
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        stopAnimate();
        handler.removeMessages(REFRESH);
        super.setOnDismissListener(listener);
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
}
