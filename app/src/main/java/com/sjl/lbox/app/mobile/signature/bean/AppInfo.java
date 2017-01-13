package com.sjl.lbox.app.mobile.signature.bean;

import android.graphics.drawable.Drawable;

/**
 * App信息
 *
 * @author SJL
 * @date 2016/8/17 22:34
 */
public class AppInfo {
    /**
     * 图标
     */
    private Drawable icon;
    /**
     * 名称
     */
    private String name;
    /**
     * 版本号
     */
    private String version;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 是否是系统app
     */
    private Boolean isSystemApp;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Boolean getSystemApp() {
        return isSystemApp;
    }

    public void setSystemApp(Boolean systemApp) {
        isSystemApp = systemApp;
    }
}
