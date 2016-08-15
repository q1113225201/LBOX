package com.sjl.lbox.app.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.sjl.lbox.listener.BaseListener;
import com.sjl.lbox.util.DialogUtil;
import com.sjl.lbox.util.LogUtil;
/**
 * 重写WebChomeClient
 *
 * @author SJL
 * @date 2016/8/11 23:36
 */
public class ReWebChomeClient extends WebChromeClient {

    private final String tag = ReWebChomeClient.class.getSimpleName();

    private Boolean isProgressShow = false;
    private Context mContext;
    private OpenFileChooserCallBack mOpenFileChooserCallBack;

    public ReWebChomeClient(Context mContext, OpenFileChooserCallBack mOpenFileChooserCallBack) {
        this.mContext = mContext;
        this.mOpenFileChooserCallBack = mOpenFileChooserCallBack;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message,
                             JsResult result) {
        DialogUtil.showAlert(mContext, null, message, null, null, false);
        result.confirm();
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        DialogUtil.showConfirm(mContext, "提示", message, null, null, new BaseListener() {

            @Override
            public void baseListener(View v, String msg) {
                result.confirm();
            }
        }, new BaseListener() {

            @Override
            public void baseListener(View v, String msg) {
                result.cancel();
            }
        }, false);
        return true;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message,
                              String defaultValue, JsPromptResult result) {
        DialogUtil.showAlert(mContext, null, message, null, null, false);
        result.confirm();
        return true;
    }

    //For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }

    // For Android  > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);

    }

    public interface OpenFileChooserCallBack {
        void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);
        //  void  openFileChooserCallBackForAndroid5(ValueCallback<Uri[]> uploadMsg);
    }

    // For Android > 5.0
    @SuppressLint("NewApi")
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        //mOpenFileChooserCallBack.openFileChooserCallBackForAndroid5(uploadMsg);
        String acceptTypes[] = fileChooserParams.getAcceptTypes();
        String acceptType = "";
        for (int i = 0; i < acceptTypes.length; ++i) {
            if (acceptTypes[i] != null && acceptTypes[i].length() != 0)
                acceptType += acceptTypes[i] + ";";
        }
        if (acceptType.length() == 0)
            acceptType = "*/*";

        final ValueCallback<Uri[]> finalFilePathCallback = filePathCallback;
        ValueCallback<Uri> vc = new ValueCallback<Uri>() {
            @Override
            public void onReceiveValue(Uri value) {

                Uri[] result;
                if (value != null)
                    result = new Uri[]{value};
                else
                    result = null;

                finalFilePathCallback.onReceiveValue(result);
            }
        };
        openFileChooser(vc, acceptType, "filesystem");
        return true;
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message,
                                    JsResult result) {
        return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress == 100) {
            LogUtil.i(tag, "onProgressChanged：" + newProgress);
            dismissProgressDialog();
        } else {
            LogUtil.i(tag, "onProgressChanged：" + newProgress);
            if (!isProgressShow) {
                showProgressDialog();
            }
        }
        super.onProgressChanged(view, newProgress);
    }

    private void dismissProgressDialog() {
        LogUtil.i(tag, "dismissProgressDialog");
        isProgressShow = false;
        DialogUtil.dismissBaseDialog();
        DialogUtil.dismissProgressImageDialog();
    }

    private void showProgressDialog() {
        isProgressShow = true;
        LogUtil.i(tag, "showProgressDialog");
        DialogUtil.showProgressDialog(mContext, false);
    }
}
