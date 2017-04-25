package com.sjl.lbox.app.component.webview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.BitmapUtil;
import com.sjl.lbox.util.LogUtil;
import com.sjl.lbox.util.NetWorkUtil;
import com.sjl.lbox.app.mobile.image.PictureUtil;

import java.io.File;
/**
 * WebView控件
 *
 * @author SJL
 * @date 2016/8/11 23:34
 */
public class WebViewActivity extends BaseActivity implements
        ReWebChomeClient.OpenFileChooserCallBack {
    private final String tag = WebViewActivity.class.getSimpleName();

    public static Boolean isAlive = false;
    //缓存路径
    private final String appCachePath = "/webcache";
    //是否是Https
    public static Boolean isHttp = false;

    public WebView mWebView;
    public Context mContext;

    public WebView getmWebView() {
        return mWebView;
    }

    public void setmWebView(WebView mWebView) {
        this.mWebView = mWebView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setContentView(mWebView);
        isAlive = true;
        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            clearWebViewCache();
        }
        initData();
    }

    private void initData() {
        mWebView.loadUrl("http://www.baidu.com");
    }

    private void initView() {
        mContext = this;
        mWebView = new WebView(mContext);
        initWebViewSetting();
        mWebView.setWebChromeClient(new ReWebChomeClient(mContext, this));
        mWebView.setWebViewClient(new ReWebViewClient(mContext, mWebView, isHttp));
        //屏蔽webview长按事件，避免出现系统的复制控件
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        initKeyListener();
    }

    private void initKeyListener() {
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    } else {
                        finish();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * 初始化webview设置
     */
    private void initWebViewSetting() {
        //设置缩放
        mWebView.getSettings().setSupportZoom(false);
        //设置允许访问文件
        mWebView.getSettings().setAllowFileAccess(true);
        //设置保存表单数据
        mWebView.getSettings().setSaveFormData(true);
        //设置是否使用viewport
        mWebView.getSettings().setUseWideViewPort(false);
        //设置WebView标准字体库字体，默认"sans-serif"
        mWebView.getSettings().setStandardFontFamily("sans-serif");
        //设置WebView固定的字体库字体，默认"monospace"
        mWebView.getSettings().setFixedFontFamily("monospace");
        //设置WebView是否以http、https方式访问从网络加载图片资源，默认false
        mWebView.getSettings().setBlockNetworkImage(false);
        //设置WebView是否从网络加载资源，Application需要设置访问网络权限，否则报异常
        mWebView.getSettings().setBlockNetworkLoads(false);
        //设置WebView是否允许执行JavaScript脚本
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置WebView运行中的脚本可以是否访问任何原始起点内容，默认true
        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(false);
        //设置Application缓存API是否开启，默认false
        mWebView.getSettings().setAppCacheEnabled(true);
        //设置当前Application缓存文件路径
        mWebView.getSettings().setAppCachePath(appCachePath);
        //设置是否开启数据库存储API权限
        mWebView.getSettings().setDatabaseEnabled(true);
        //设置是否开启DOM存储API权限
        mWebView.getSettings().setDomStorageEnabled(true);
        //设置是否开启定位功能，默认true，开启定位
        mWebView.getSettings().setGeolocationEnabled(true);
        //设置脚本是否允许自动打开弹窗，默认false，不允许
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        //设置WebView加载页面文本内容的编码，默认“UTF-8”。
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        // 设置缓存模式
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        //设置WebView是否自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            mWebView.getSettings().setLoadsImagesAutomatically(false);
        }
        //硬件加速器的使用
        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == PictureUtil.REQUEST_CODE_CAMERA || requestCode == PictureUtil.REQUEST_CODE_CROP || requestCode == PictureUtil.REQUEST_CODE_PHOTO) {
                PictureUtil.onActivityResult(requestCode,resultCode,data);
                /*PictureUtil.onActivityResult(requestCode, resultCode, data,
                        new PictureUtil.BitmapLoadCallBack() {

                            @Override
                            public void bitmapLoadSuccess(Bitmap bitmap, Uri uri)
                                    throws Exception {
                                try {
                                    mUploadMsg.onReceiveValue(uri);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void bitmapLoadFailure(String error)
                                    throws Exception {
                                if (mUploadMsg == null) {
                                    return;
                                }
                                mUploadMsg.onReceiveValue(null);
                            }
                        });*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (mUploadMsg != null) {
                mUploadMsg.onReceiveValue(null);
                mUploadMsg = null;
            }
        }
    }

    public static ValueCallback<Uri> mUploadMsg;

    // 文件选择回调
    @Override
    public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg,
                                        String acceptType) {
        mUploadMsg = uploadMsg;
        PictureUtil.choosePicture(this, new PictureUtil.PictureLoadCallBack() {
            @Override
            public void bitmapLoadSuccess(Bitmap bitmap, Uri uri) {
                try {
                    BitmapUtil.save(bitmap,uri.getPath());
                    mUploadMsg.onReceiveValue(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void bitmapLoadFailure(String error) {
                if (mUploadMsg == null) {
                    return;
                }
                mUploadMsg.onReceiveValue(null);
            }
        });
       /* PictureUtil.showPop(new PictureUtil.OnPopDismiss() {

            @Override
            public void dismiss(Boolean isShow) {
                if (isShow) {
                    if (mUploadMsg != null) {
                        mUploadMsg.onReceiveValue(null);
                        mUploadMsg = null;
                    }
                }
            }
        });*/
    }

    private final int scrollSize = 200;
    private int startX;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //滑动切换界面
//        mWebView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        startX = (int) event.getX();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        int endX = (int) event.getX();
//                        if (endX > startX && mWebView.canGoBack() && endX - startX > scrollSize) {
//                            mWebView.goBack();
//                        } else if (endX < startX && mWebView.canGoForward() && startX - endX > scrollSize) {
//                            mWebView.goForward();
//                        }
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache() {
        // 清理WebView缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // WebView缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath()
                + appCachePath);
        LogUtil.i(tag, "appCacheDir path=" + appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()
                + appCachePath);
        LogUtil.i(tag, "appCacheDir path=" + webviewCacheDir.getAbsolutePath());

        // 删除webView缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        }
        // 删除webView缓存，缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
    }

    public void deleteFile(File file) {
        LogUtil.i(tag, "delete file path=" + file.getAbsolutePath());
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (File file1 : files) {
                    deleteFile(file1);
                }
            }
            file.delete();
        } else {
            LogUtil.i(tag, "delete file no exists " + file.getAbsolutePath());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onDestroy() {
        isAlive = false;
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
            mContext = null;
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mWebView.saveState(outState);
//        LogUtil.i(tag, "save state");
//    }

//    @Override
//    public File getCacheDir() {
//        //设置缓存路径
//        return super.getCacheDir();
//    }
}
