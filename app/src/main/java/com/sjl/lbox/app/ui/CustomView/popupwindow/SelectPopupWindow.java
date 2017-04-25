package com.sjl.lbox.app.ui.CustomView.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.popupwindow.adapter.TextSelectAdapter;
import com.sjl.lbox.app.ui.CustomView.popupwindow.listener.OnItemClickListener;

import java.util.List;

/**
 * SelectPopupWindow
 *
 * @author SJL
 * @date 2017/2/23
 */

public class SelectPopupWindow implements View.OnClickListener {
    private Context context;
    private View parentView;
    public PopupWindow popupWindow;
    private LinearLayout llParent;
    private RecyclerView rv;
    private TextView tvCancel;
    private List<String> list;
    private TextSelectAdapter textSelectAdapter;
    private OnItemClickListener onItemClickListener;
    private boolean isShow;

    public SelectPopupWindow(Context context, List<String> list, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 显示底部弹窗
     */
    public void show() {
        if (popupWindow == null) {
            setDefaultLayout();
        }
        textSelectAdapter.notifyDataSetChanged();
        if (popupWindow != null && !isShow) {
            popupWindow.showAtLocation(parentView, Gravity.BOTTOM, (int) parentView.getX(), (int) (parentView.getY()));
            changeAlpha(0.3f);
            isShow = true;
        }
    }

    private void setDefaultLayout() {
        parentView = ((Activity) context).findViewById(android.R.id.content);
        View popupView = LayoutInflater.from(context).inflate(R.layout.popup_window_select, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setAnimationStyle(R.style.PopupWindowSelectAnimation);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismiss();
            }
        });
        llParent = (LinearLayout) popupView.findViewById(R.id.llParent);
        rv = (RecyclerView) popupView.findViewById(R.id.rv);
        tvCancel = (TextView) popupView.findViewById(R.id.tvCancel);
        textSelectAdapter = new TextSelectAdapter(context,list);
        rv.setAdapter(textSelectAdapter);
        rv.setLayoutManager(new LinearLayoutManager(context));

        llParent.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        textSelectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                onItemClickListener.onItemClick(view,position);
                dismiss();
            }
        });
    }

    /**
     * 取消弹窗
     */
    public void dismiss() {
        if (popupWindow != null && isShow) {
            popupWindow.dismiss();
            changeAlpha(1f);
            isShow = false;
        }
    }

    //改变activity透明度
    private void changeAlpha(float alpha) {
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.alpha = alpha;
        ((Activity) context).getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llParent:
                dismiss();
                break;
            case R.id.tvCancel:
                dismiss();
                break;
        }
    }
}
