package com.sjl.lbox.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.InputFilter;
import android.view.View;
import android.widget.BaseAdapter;

import com.sjl.lbox.R;
import com.sjl.lbox.listener.BaseListener;
import com.sjl.lbox.listener.ItemClickListener;
import com.sjl.lbox.app.ui.CustomView.progress.view.dialog.BaseDialog;
import com.sjl.lbox.app.ui.CustomView.progress.view.dialog.BaseProgressDialog;
import com.sjl.lbox.app.ui.CustomView.progress.view.dialog.BaseProgressImageDialog;
import com.sjl.lbox.app.ui.CustomView.progress.view.dialog.NetWorkErrorDialog;
import com.sjl.lbox.app.ui.CustomView.progress.view.dialog.ProgressDialog;

public class DialogUtil {
    private static BaseDialog baseDialog;
    private static BaseProgressDialog progressDialog;
    private static BaseProgressImageDialog progressImageDialog;
    private static NetWorkErrorDialog netWorkErrorDialog;

    /**
     * 显示提示框
     *
     * @param context
     * @param title                    标题
     * @param msg                      提示文字
     * @param confirm                  确认按钮文字，默认：确认
     * @param confirmListener          确认按钮监听
     * @param cancelableOnTouchOutside
     */
    public static BaseDialog showAlert(Context context, String title, CharSequence msg, String confirm, BaseListener confirmListener, Boolean cancelableOnTouchOutside) {
        return showBaseDialog(context, title, msg, null, false, -1, -1, -1, confirm, null, false, confirmListener, null, cancelableOnTouchOutside, 0);
    }

    /**
     * 显示提示框
     *
     * @param context
     * @param title                    标题
     * @param msg                      提示文字
     * @param confirm                  确认按钮文字，默认：确认
     * @param confirmListener          确认按钮监听
     * @param cancelableOnTouchOutside
     * @param time                     显示时长(秒)
     * @return
     */
    public static BaseDialog showAlertAndHide(Context context, String title, CharSequence msg, String confirm, BaseListener confirmListener, Boolean cancelableOnTouchOutside, long time) {
        return showBaseDialog(context, title, msg, null, false, -1, -1, -1, confirm, null, false, confirmListener, null, cancelableOnTouchOutside, time);
    }

    /**
     * 确认弹窗
     *
     * @param context
     * @param title                    标题
     * @param msg                      提示文字
     * @param confirm                  确认按钮文字，默认：确认
     * @param cancel                   取消按钮文字，默认：取消
     * @param confirmListener          确认按钮监听
     * @param cancelListener           取消按钮监听
     * @param cancelableOnTouchOutside
     * @return
     */
    public static BaseDialog showConfirm(Context context, String title, CharSequence msg, String confirm, String cancel, BaseListener confirmListener, BaseListener cancelListener, Boolean cancelableOnTouchOutside) {
        return showBaseDialog(context, title, msg, null, false, -1, -1, -1, confirm, cancel, true, confirmListener, cancelListener, cancelableOnTouchOutside, 0);
    }

    /**
     * 确认弹窗
     *
     * @param context
     * @param title                    标题
     * @param msg                      提示文字
     * @param confirm                  确认按钮文字，默认：确认
     * @param cancel                   取消按钮文字，默认：取消
     * @param confirmListener          确认按钮监听
     * @param cancelListener           取消按钮监听
     * @param cancelableOnTouchOutside
     * @return
     */
    public static BaseDialog showConfirmAndHide(Context context, String title, CharSequence msg, String confirm, String cancel, BaseListener confirmListener, BaseListener cancelListener, Boolean cancelableOnTouchOutside, long time) {
        return showBaseDialog(context, title, msg, null, false, -1, -1, -1, confirm, cancel, true, confirmListener, cancelListener, cancelableOnTouchOutside, time);
    }

    /**
     * @param context
     * @param title                    标题
     * @param msg                      提示文字
     * @param hintEditText             提示文字
     * @param maxLength                输入框可输入最大长达
     * @param inputType                输入框输入类型数字、文本、密码等,-1为无限制
     * @param confirm                  确认按钮文字，默认：确认
     * @param cancel                   取消按钮文字，默认：取消
     * @param showCancel               是否显示取消按钮
     * @param confirmListener          确认按钮监听
     * @param cancelListener           取消按钮监听
     * @param cancelableOnTouchOutside
     * @return
     */
    public static BaseDialog showInputDialog(Context context, String title, CharSequence msg, String hintEditText, int maxLength, int inputType, int rawInputType, String confirm, String cancel, Boolean showCancel, BaseListener confirmListener, BaseListener cancelListener, Boolean cancelableOnTouchOutside) {
        return showBaseDialog(context, title, msg, hintEditText, true, maxLength, inputType, rawInputType, confirm, cancel, showCancel, confirmListener, cancelListener, cancelableOnTouchOutside, 0);
    }

    /**
     * @param context
     * @param title                    标题
     * @param msg                      提示文字
     * @param showEditText             显示输入框
     * @param maxLength                输入框最大输入长度，-1为不限制
     * @param inputType                输入框输入类型数字、文本、密码等,-1为无限制
     * @param rawInputType             输入框键盘类型数字、文本等,-1为无限制
     * @param confirm                  确认按钮文字，默认：确认
     * @param cancel                   取消按钮文字，默认：取消
     * @param showCancel               是否显示取消按钮
     * @param confirmListener          确认按钮监听
     * @param cancelListener           取消按钮监听
     * @param cancelableOnTouchOutside
     * @param time                     显示时长(秒)
     */
    private static BaseDialog showBaseDialog(Context context, String title, CharSequence msg, String hintEditText, Boolean showEditText, int maxLength, int inputType, int rawInputType, String confirm, String cancel, Boolean showCancel, BaseListener confirmListener, BaseListener cancelListener, Boolean cancelableOnTouchOutside, long time) {
        baseDialog = new BaseDialog(context, cancelableOnTouchOutside);
        baseDialog.show();
        if (!isEmpty(title)) {
            baseDialog.setTitle(title);
        }
        if (!isEmpty(msg)) {
            baseDialog.setContent(msg);
        }
        if (showEditText) {
            baseDialog.getEtContent().setVisibility(View.VISIBLE);
            if (hintEditText != null) {
                baseDialog.getEtContent().setHint(hintEditText);
            }
            InputFilter[] inputFilter = new InputFilter[]{new InputFilter.LengthFilter(maxLength)};
            if (maxLength != -1) {
                baseDialog.getEtContent().setFilters(inputFilter);
            }
            if (inputType != -1) {
                baseDialog.getEtContent().setInputType(inputType);
            }
            if (rawInputType != -1) {
                baseDialog.getEtContent().setRawInputType(rawInputType);
            }
        }
        if (!isEmpty(confirm)) {
            baseDialog.getBtnConfirm().setText(confirm);
        }
        if (!isEmpty(cancel)) {
            baseDialog.getBtnCancel().setText(cancel);
        }
        if (!showCancel) {
            baseDialog.getBtnCancel().setVisibility(View.GONE);
            baseDialog.getViewCancel().setVisibility(View.GONE);
        }
        if (confirmListener != null) {
            baseDialog.setConfirmListener(confirmListener);
        }
        if (cancelListener != null) {
            baseDialog.setCancelListener(cancelListener);
        }
        if (time > 0) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    baseDialog.dismiss();
                }
            }, time * 1000);
        }
        return baseDialog;
    }

    /**
     * 显示列表弹窗
     *
     * @param context
     * @param title                    标题
     * @param adapter                  适配器
     * @param itemClickListener        列表点击事件
     * @param cancelableOnTouchOutside
     * @return
     */
    public static BaseDialog showLVDialog(Context context, String title, BaseAdapter adapter, ItemClickListener itemClickListener, Boolean showConfirm, BaseListener confirmListener, Boolean cancelableOnTouchOutside) {
        baseDialog = new BaseDialog(context, cancelableOnTouchOutside);
        baseDialog.show();
        baseDialog.setTitle(title);
        baseDialog.getLlContent().setVisibility(View.GONE);
        baseDialog.getLlBottom().setVisibility(View.GONE);
        baseDialog.getLlLvContent().setVisibility(View.VISIBLE);
        baseDialog.getLv().setAdapter(adapter);
        baseDialog.setItemClickListener(itemClickListener);
        if (showConfirm) {
            baseDialog.getLlBottom().setVisibility(View.VISIBLE);
            baseDialog.getBtnConfirm().setVisibility(View.VISIBLE);
            baseDialog.getBtnCancel().setVisibility(View.VISIBLE);
        }
        baseDialog.setConfirmListener(confirmListener);
        return baseDialog;
    }

    public static BaseDialog showLVDialog(Context context, String title, BaseAdapter adapter, ItemClickListener itemClickListener, Boolean cancelableOnTouchOutside) {
        baseDialog = new BaseDialog(context, cancelableOnTouchOutside);
        baseDialog.show();
        baseDialog.setTitle(title);
        baseDialog.getLlContent().setVisibility(View.GONE);
        baseDialog.getLlBottom().setVisibility(View.GONE);
        baseDialog.getLlLvContent().setVisibility(View.VISIBLE);
        baseDialog.getLv().setAdapter(adapter);
        baseDialog.setItemClickListener(itemClickListener);
        return baseDialog;
    }

    /**
     * 弹出窗消失
     */
    public static void dismissBaseDialog() {
        if (baseDialog != null && baseDialog.isShowing()) {
            baseDialog.dismiss();
            baseDialog = null;
        }
    }

    /**
     * 显示进度条弹出窗
     *
     * @param context
     * @param message                  内容
     * @param cancelableOnTouchOutside
     * @return
     */
    public static BaseProgressDialog showProgressTextDialog(Context context, String message, boolean cancelableOnTouchOutside) {
        progressDialog = new BaseProgressDialog(context, cancelableOnTouchOutside);
        progressDialog.show();
        if (!isEmpty(message)) {
            progressDialog.setMessage(message);
        } else {
            progressDialog.getTvMsg().setVisibility(View.GONE);
        }
        return progressDialog;
    }

    /**
     * 进度条弹出框消失
     */
    public static void dismissProgressTextDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * 显示带图片的进度加载弹窗
     *
     * @param context
     * @param drawable                 图片
     * @param cancelableOnTouchOutside
     * @return
     */
    public static BaseProgressImageDialog showProgressImageDialog(Context context, Drawable drawable, boolean cancelableOnTouchOutside) {
        progressImageDialog = new BaseProgressImageDialog(context);
        progressImageDialog.show();
        progressImageDialog.setProgressBarBackground(drawable);
        progressImageDialog.setCanceledOnTouchOutside(cancelableOnTouchOutside);
        return progressImageDialog;
    }

    /**
     * 显示带图片的进度加载弹窗
     *
     * @param context
     * @param resource                 图片
     * @param cancelableOnTouchOutside
     * @return
     */
    public static BaseProgressImageDialog showProgressImageDialog(Context context, int resource, boolean cancelableOnTouchOutside) {
        if (progressImageDialog != null && progressImageDialog.isShowing()) {
            return progressImageDialog;
        }
        progressImageDialog = new BaseProgressImageDialog(context);
        progressImageDialog.show();
        progressImageDialog.setProgressBarBackground(resource);
        progressImageDialog.setCanceledOnTouchOutside(cancelableOnTouchOutside);
        return progressImageDialog;
    }

    /**
     * 显示带图片和进度数值的进度加载弹窗
     *
     * @param context
     * @param resource                 图片
     * @param maxTime                  最大运行时长(s)
     * @param cancelableOnTouchOutside
     * @return
     */
    public static BaseProgressImageDialog showProgressImageDialog(Context context, int resource, int maxTime, boolean cancelableOnTouchOutside) {
        if (progressImageDialog != null && progressImageDialog.isShowing()) {
            return progressImageDialog;
        }
        progressImageDialog = new BaseProgressImageDialog(context);
        progressImageDialog.show();
        progressImageDialog.setMaxTime(maxTime);
        progressImageDialog.setProgressBarBackground(resource);
        progressImageDialog.setCanceledOnTouchOutside(cancelableOnTouchOutside);
        return progressImageDialog;
    }

    /**
     * 显示进度加载弹窗
     *
     * @param context
     * @param cancelableOnTouchOutside
     * @return
     */
    public static BaseProgressImageDialog showProgressDialog(Context context, boolean cancelableOnTouchOutside) {
        if (progressImageDialog != null && progressImageDialog.isShowing()) {
            LogUtil.i("showProgressDialog", "progressImageDialog != null && progressImageDialog.isShowing()");
            return progressImageDialog;
        }
        progressImageDialog = new BaseProgressImageDialog(context, R.layout.dialog_progress_circle_50);
        progressImageDialog.show();
        progressImageDialog.setProgressBarBackground(R.drawable.progress_bg_load);
        progressImageDialog.setCanceledOnTouchOutside(cancelableOnTouchOutside);
        LogUtil.i("showProgressDialog", "progressImageDialog.show();");
        return progressImageDialog;
    }

    /**
     * 显示有进度数值的加载弹窗
     *
     * @param context
     * @param maxTime                  最大运行时长(s)
     * @param cancelableOnTouchOutside
     * @return
     */
    public static BaseProgressImageDialog showProgressDialog(Context context, int maxTime, boolean cancelableOnTouchOutside) {
        if (progressImageDialog != null && progressImageDialog.isShowing()) {
            return progressImageDialog;
        }
        progressImageDialog = new BaseProgressImageDialog(context, R.layout.dialog_progress_circle_50);
        progressImageDialog.show();
        progressImageDialog.setMaxTime(maxTime);
        progressImageDialog.setProgressBarBackground(R.drawable.progress_bg_load);
        progressImageDialog.setCanceledOnTouchOutside(cancelableOnTouchOutside);
        return progressImageDialog;
    }

    /**
     * 带图片进度条弹出框消失
     */
    public static void dismissProgressImageDialog() {
        if (progressImageDialog != null && progressImageDialog.isShowing()) {
            progressImageDialog.dismiss();
            progressImageDialog = null;
            LogUtil.i("dismissProgressImageDialog", "progressImageDialog != null && progressImageDialog.isShowing()");
        }
        LogUtil.i("dismissProgressImageDialog", "progressImageDialog = null");
    }

    private static ProgressDialog loadProgressDialog;

    public static ProgressDialog showLoadProgressDialog(Context context, boolean cancelableOnTouchOutside) {
        if (loadProgressDialog != null && loadProgressDialog.isShowing()) {
            return loadProgressDialog;
        }
        loadProgressDialog = new ProgressDialog(context, cancelableOnTouchOutside);
        loadProgressDialog.show();
        return loadProgressDialog;
    }
    /**
     * 带进度条弹出框消失
     */
    public static void dismissLoadProgressDialog() {
        if (loadProgressDialog != null && loadProgressDialog.isShowing()) {
            loadProgressDialog.dismiss();
            loadProgressDialog = null;
            LogUtil.i("progressDialog", "progressDialog != null && progressDialog.isShowing()");
        }
        LogUtil.i("progressDialog", "progressDialog = null");
    }
    /**
     * 显示网络报错页面
     *
     * @param context
     * @param refreshListener 刷新事件
     * @param backListener    返回首页事件
     * @return
     */
    public static NetWorkErrorDialog showNetWorkErrorDialog(Context context, BaseListener refreshListener, BaseListener backListener) {
        if (netWorkErrorDialog == null) {
            netWorkErrorDialog = new NetWorkErrorDialog(context, refreshListener, backListener);
        }
        netWorkErrorDialog.show();
        return netWorkErrorDialog;
    }

    /**
     * 取消网络失败弹窗
     */
    public static void dismissNetWorkErrorDialog() {
        if (netWorkErrorDialog != null && netWorkErrorDialog.isShowing()) {
            netWorkErrorDialog.dismiss();
            netWorkErrorDialog = null;
        }
    }

    public static void dismiss(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private static Boolean isEmpty(CharSequence str) {
        return (str == null || str.toString().length() == 0);
    }
}
