package com.sjl.lbox.app.network.http.NoHttp.util;

import android.graphics.Bitmap;

import com.sjl.lbox.app.network.http.NoHttp.http.HttpRequestCallback;
import com.sjl.lbox.app.network.http.NoHttp.http.NoHttpRequest;
import com.sjl.lbox.app.network.http.NoHttp.http.SSLContextUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;

import java.util.Map;

import static com.sjl.lbox.LBoxApp.app;

/**
 * Created by yanfa on 2016/6/22.
 */
public class NoHttpUtil {
    private static final String tag = NoHttpUtil.class.getSimpleName();

    /**
     * 发送post请求
     *
     * @param url                 地址
     * @param dataMap             参数
     * @param httpRequestCallback 回调
     */
    public static void sendPostRequest(String url, int what, Map<String, String> dataMap, HttpRequestCallback httpRequestCallback) {
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        request.add(dataMap);
        if (url.startsWith("https")) {
            SSLContextUtil.doHttps(request);
        }
        if (httpRequestCallback != null) {
            httpRequestCallback.setEncrypt(false);
        }
        NoHttpRequest.getInstance(app).add(what, request, httpRequestCallback);
    }

    /**
     * 发送post请求加密
     *
     * @param url                 地址
     * @param dataMap             参数
     * @param httpRequestCallback 回调
     */
    public static void sendPostRequestEncrypt(String url, int what, Map<String, String> dataMap, HttpRequestCallback httpRequestCallback) {
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        request.add(dataMap);
        if (url.startsWith("https")) {
            SSLContextUtil.doHttps(request);
        }
        if (httpRequestCallback != null) {
            httpRequestCallback.setEncrypt(true);
        }
        NoHttpRequest.getInstance(app).add(what, request, httpRequestCallback);
    }

    /**
     * 图片请求
     *
     * @param url                 地址
     * @param httpRequestCallback 回调
     */
    public static void sendImageRequest(String url, int what, HttpRequestCallback httpRequestCallback) {
        Request<Bitmap> request = NoHttp.createImageRequest(url, RequestMethod.GET);
        if(httpRequestCallback!=null){
            httpRequestCallback.setEncrypt(false);
        }
        NoHttpRequest.getInstance(app).add(what, request, httpRequestCallback);
    }
}
