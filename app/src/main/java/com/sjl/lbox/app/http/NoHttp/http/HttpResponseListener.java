package com.sjl.lbox.app.http.NoHttp.http;

import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;

import java.net.ProtocolException;

/**
 * Created by yanfa on 2016/6/21.
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {
    private final static String tag = HttpResponseListener.class.getSimpleName();
    /**
     * 结果回调.
     */
    private HttpRequestCallback<T> httpCallback;

    /**
     * @param httpCallback 回调对象.
     */
    public HttpResponseListener(HttpRequestCallback httpCallback) {
        this.httpCallback = httpCallback;
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        int responseCode = response.getHeaders().getResponseCode();
        if (responseCode > 400) {
            if (responseCode == 405) {// 405表示服务器不支持这种请求方法，比如GET、POST、TRACE中的TRACE就很少有服务器支持。

            } else {// 但是其它400+的响应码服务器一般会有流输出。

            }
        }
        if (httpCallback != null) {
            T result = response.get();
            if (httpCallback.getEncrypt()) {
                //对result解密
            }
            httpCallback.onSucceed(result);
        }
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        String errorMsg = "";
        if (exception instanceof NetworkError) {// 网络不好
            errorMsg = "请检查网络。";
        } else if (exception instanceof TimeoutError) {// 请求超时
            errorMsg = "请求超时，网络不好或者服务器不稳定。";
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            errorMsg = "未发现指定服务器。";
        } else if (exception instanceof URLError) {// URL是错的
            errorMsg = "URL错误。";
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            errorMsg = "没有发现缓存。";
        } else if (exception instanceof ProtocolException) {
            errorMsg = "系统不支持的请求方式。";
        } else {
            errorMsg = "未知错误。";
            //errorMsg+=exception.toString();
        }
        if (httpCallback != null)
            httpCallback.onFailed(errorMsg);
    }

    @Override
    public void onStart(int what) {

    }

    @Override
    public void onFinish(int what) {

    }
}
