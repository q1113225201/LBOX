package com.sjl.lbox.app.http.NoHttp.http;

import android.app.Application;

import com.sjl.lbox.util.NetWorkUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by yanfa on 2016/6/21.
 */
public class NoHttpRequest {
    private final static String tag = NoHttpRequest.class.getSimpleName();
    private int timeout = 5000;
    private static NoHttpRequest httpUtil;
    /**
     * 请求队列.
     */
    private RequestQueue requestQueue;

    private Application application;

    /**
     * 请求队列.
     */
    public synchronized static NoHttpRequest getInstance(Application application) {
        if (httpUtil == null) {
            httpUtil = new NoHttpRequest(application);
        }
        return httpUtil;
    }

    private NoHttpRequest(Application application) {
        this.application = application;
        requestQueue = NoHttp.newRequestQueue();
    }

    public <T> void add(int what, Request<T> request, HttpRequestCallback httpRequestCallback) {
        request.setConnectTimeout(timeout);
        if (!NetWorkUtil.isNetworkAvailable(application.getApplicationContext())) {
            httpRequestCallback.onFailed("网络不可用，请检查网络连接。");
            return;
        }
        requestQueue.add(what, request, new HttpResponseListener<T>(httpRequestCallback));
    }
}
