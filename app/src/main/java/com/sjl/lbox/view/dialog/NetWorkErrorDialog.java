package com.sjl.lbox.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sjl.lbox.R;
import com.sjl.lbox.listener.BaseListener;


public class NetWorkErrorDialog extends Dialog {
    private Context mContext;

    private View view;

    private ImageView ivRefresh;

    private ImageView ivBack;

    private BaseListener refreshListener;

    private BaseListener backListener;

    public NetWorkErrorDialog(Context context) {
        super(context, R.style.baseDialogStyle);
        this.mContext = context;
        setCanceledOnTouchOutside(false);
    }

    public NetWorkErrorDialog(Context context, BaseListener refreshListener, BaseListener backListener) {
        super(context, R.style.baseDialogStyle);
        this.mContext = context;
        this.refreshListener = refreshListener;
        this.backListener = backListener;
        setCanceledOnTouchOutside(false);
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
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        findView();
        super.show();
    }

    private void setDefalutLayout() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.dialog_network_error, null);
    }

    private void findView() {
        if (view == null) {
            setDefalutLayout();
        }
        ivRefresh = (ImageView) view.findViewById(R.id.network_refresh_iv);
        ivBack = (ImageView) view.findViewById(R.id.network_back_home_iv);

        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (refreshListener != null) {
                    refreshListener.baseListener(v, "");
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (refreshListener != null) {
                    backListener.baseListener(v, "");
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
