package com.sjl.lbox.app.http.NoHttp.http;

/**
 * Created by yanfa on 2016/6/22.
 */
public abstract class HttpRequestCallback<T> {
    private Boolean isEncrypt;

    public Boolean getEncrypt() {
        return isEncrypt;
    }

    public void setEncrypt(Boolean encrypt) {
        isEncrypt = encrypt;
    }

    public abstract void onSucceed(T data);

    public abstract void onFailed(String error);
}
