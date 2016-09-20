package com.sjl.lbox.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.sjl.lbox.R;

public class BasePopBottom {
    public Context context;
    public View parentView;
    public PopupWindow popupWindow;
    public LinearLayout parent, ll;
    public ListView lv;
    public Button btnCancel;
    public boolean isShow = false;

    public BasePopBottom(Context context, boolean bottomShow) {
        this.context = context;
        this.parentView = ((Activity) context).findViewById(android.R.id.content);
        View popupView = LayoutInflater.from(context).inflate(R.layout.app_base_pop_bottom, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setAnimationStyle(R.style.popAnimate);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        parent = (LinearLayout) popupView.findViewById(R.id.pop_bottom_parent_ll);
        ll = (LinearLayout) popupView.findViewById(R.id.pop_bottom_ll);
        lv = (ListView) popupView.findViewById(R.id.pop_bottom_lv);
        btnCancel = (Button) popupView.findViewById(R.id.pop_bottom_cancel_btn);

        parent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (!bottomShow) {
            ll.setVisibility(View.GONE);
        }
    }

    public BasePopBottom(Context context, View view, boolean bottomShow) {
        this.context = context;
        this.parentView = view;
        View popupView = LayoutInflater.from(context).inflate(R.layout.app_base_pop_bottom, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setAnimationStyle(R.style.popAnimate);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        parent = (LinearLayout) popupView.findViewById(R.id.pop_bottom_parent_ll);
        ll = (LinearLayout) popupView.findViewById(R.id.pop_bottom_ll);
        lv = (ListView) popupView.findViewById(R.id.pop_bottom_lv);
        btnCancel = (Button) popupView.findViewById(R.id.pop_bottom_cancel_btn);

        parent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (!bottomShow) {
            ll.setVisibility(View.GONE);
        }
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public LinearLayout getCurrentParent() {
        return parent;
    }

    public LinearLayout getLl() {
        return ll;
    }

    public ListView getLv() {
        return lv;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public Boolean isShow() {
        return isShow;
    }

    public void dismiss() {
        if (popupWindow != null && isShow) {
            popupWindow.dismiss();
            changeAlpha(1f);
            isShow = false;
        }
    }

    public void show() {
        if (popupWindow != null && !isShow) {
            popupWindow.showAtLocation(parentView, Gravity.BOTTOM, (int) parentView.getX(), (int) (parentView.getY()));
            changeAlpha(0.3f);
            isShow = true;
        }
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        if (onDismissListener != null) {
            popupWindow.setOnDismissListener(onDismissListener);
        }
    }

    //改变activity透明度
    private void changeAlpha(float alpha) {
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.alpha = alpha;
        ((Activity) context).getWindow().setAttributes(params);
    }
}
