package com.sjl.lbox.app.component.webview;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sjl.lbox.app.network.http.NoHttp.http.SSLContextUtil;
import com.sjl.lbox.listener.BaseListener;
import com.sjl.lbox.util.DialogUtil;
import com.sjl.lbox.util.LogUtil;
import com.sjl.lbox.util.PermisstionUtil;

import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertPathValidatorException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;

/**
 * 重写WebViewClient
 *
 * @author SJL
 * @date 2016/8/11 23:39
 */
public class ReWebViewClient extends WebViewClient {
    private final String tag = ReWebViewClient.class.getSimpleName();

    private Context mContext;
    private WebView mWebView;
    private String mUrl;
    private SSLContext sslContext;
    private Boolean isHttps;
    private Boolean isError = false;

    public ReWebViewClient(Context context, WebView webView, Boolean isHttps) {
        super();
        this.mContext = context;
        this.mWebView = webView;
        this.isHttps = isHttps;
        if (isHttps) {
            sslContext = SSLContextUtil.getSSLContext();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (isHttps) {
            return processRequest(view, request.getUrl().toString(), request.getMethod());
        } else {
            return super.shouldInterceptRequest(view, request);
        }
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (isHttps) {
            return processRequest(view, url, getMethod(url));
        }
        return super.shouldInterceptRequest(view, url);
    }

    private String getMethod(String url) {
        return url.contains(".action") ? "POST" : "GET";
    }

    private WebResourceResponse processRequest(WebView view, String uri, String method) {
        try {
            URL url = new URL(uri);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());
            //urlConnection.setHostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);
            urlConnection.setRequestMethod(method);

            InputStream is = urlConnection.getInputStream();
            String contentType = urlConnection.getContentType();
            String encoding = urlConnection.getContentEncoding();

            if (contentType != null) {

                String mimeType = contentType;

                if (contentType.contains(";")) {
                    mimeType = contentType.split(";")[0].trim();
                }
                return new WebResourceResponse(mimeType, encoding, is);
            }

        } catch (SSLHandshakeException e) {
            if (isCause(CertPathValidatorException.class, e)) {

            }
        } catch (Exception e) {
        }
        return new WebResourceResponse(null, null, null);
    }

    public static boolean isCause(
            Class<? extends Throwable> expected,
            Throwable exc
    ) {
        return expected.isInstance(exc) || (
                exc != null && isCause(expected, exc.getCause())
        );
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, final WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, final String url) {
        LogUtil.i(tag, "shouldOverrideUrlLoading url:" + url);
        if (url.toString().startsWith("tel:")) {
            PermisstionUtil.requestPermissions(mContext, PermisstionUtil.PHONE, 1002, "拨打电话需要拨号权限，是否继续？", new PermisstionUtil.OnPermissionResult() {
                @Override
                public void granted(int requestCode) {
                    try {
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                            mContext.startActivity(intent);
                        }
                    } catch (Exception e) {

                    }
                }

                @Override
                public void denied(int requestCode) {

                }
            });
        } else if (url.startsWith("http:") || url.startsWith("https:")) {
            view.loadUrl(url);
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        LogUtil.i(tag, "startTime:" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        LogUtil.i(tag, "onPageStarted:" + url);
        //开始计时
        startTime();
        mUrl = url;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        LogUtil.i(tag, "finishTime:" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        LogUtil.i(tag, "onPageFinished:" + url);
        super.onPageFinished(view, url);
        if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
            mWebView.getSettings().setLoadsImagesAutomatically(true);
        }
        //取消计时
        cancelTime();
        if (!isError) {
            DialogUtil.dismissNetWorkErrorDialog();
        }
        isError = false;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request,
                                WebResourceError error) {
        LogUtil.i(tag, "onReceivedError:" + request.toString() + "---error:" + error.toString());
        //取消计时
//        cancelTime();
        //加载页面出错时
//        showNetworkError();
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url,
                                       boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
        LogUtil.i(tag, "url:" + url + "---isReload:" + isReload);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request,
                                    WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        LogUtil.i(tag, "WebResourceRequest:" + request.toString() + "---WebResourceResponse:" + errorResponse.toString());
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();  // 证书错误时，接受所有网站的证书
    }

    private final int TIME_OUT = 10000;
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (TIME_OUT == msg.what) {
                if (mWebView.getProgress() < 100) {
                    //超时后
                    //1.停止计时
                    cancelTime();
                    //2.中断链接
//                    mWebView.stopLoading();
                    //3.关闭加载进度条
                    DialogUtil.dismissProgressImageDialog();
                    //4.显示错误界面
                    showNetworkError();
                }
            }
        }
    };

    /**
     * 开始计时
     */
    private void startTime() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(TIME_OUT);
            }
        };
        timer.schedule(timerTask, TIME_OUT);
        LogUtil.i(tag, "计时开始");
    }

    /**
     * 取消计时
     */
    private void cancelTime() {
        LogUtil.i(tag, "计时结束");
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    /**
     * 显示网络错误
     */
    private void showNetworkError() {
        LogUtil.i(tag, "showNetworkError");
        mWebView.stopLoading();
        isError = true;
        if (WebViewActivity.isAlive) {
            DialogUtil.showNetWorkErrorDialog(mContext, new BaseListener() {
                @Override
                public void baseListener(View v, String msg) {
//                dismissProgress();
//                mWebView.reload();
                    mWebView.loadUrl(mUrl);
                }
            }, new BaseListener() {
                @Override
                public void baseListener(View v, String msg) {
//                dismissDialog();
                    ((Activity) mContext).finish();
                }
            });
        }
    }
}
