package com.sjl.lbox.app.mobile.AppInfo.bean;

import android.content.pm.ActivityInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * App信息
 *
 * @author SJL
 * @date 2016/8/17 22:34
 */
public class AppInfo implements Serializable {
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
     * 版本
     */
    private int code;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 源文件路径
     */
    private String srcPath;
    /**
     * 最后修改时间
     */
    private Long lastModifyTime;
    /**
     * Activitys
     */
    private ActivityInfo[] activitys;
    /**
     * 广播
     */
    private ActivityInfo[] receivers;
    /**
     * 服务
     */
    private ServiceInfo[] services;
    /**
     * 内容提供者
     */
    private ProviderInfo[] providers;
    /**
     * 权限
     */
    private String[] permissions;
    /**
     * 签名
     */
    private Signature[] signatures;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public Long getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public ActivityInfo[] getActivitys() {
        return activitys;
    }

    public void setActivitys(ActivityInfo[] activitys) {
        this.activitys = activitys;
    }

    public ActivityInfo[] getReceivers() {
        return receivers;
    }

    public void setReceivers(ActivityInfo[] receivers) {
        this.receivers = receivers;
    }

    public ServiceInfo[] getServices() {
        return services;
    }

    public void setServices(ServiceInfo[] services) {
        this.services = services;
    }

    public ProviderInfo[] getProviders() {
        return providers;
    }

    public void setProviders(ProviderInfo[] providers) {
        this.providers = providers;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public Signature[] getSignatures() {
        return signatures;
    }

    public void setSignatures(Signature[] signatures) {
        this.signatures = signatures;
    }

    public Boolean getSystemApp() {
        return isSystemApp;
    }

    public void setSystemApp(Boolean systemApp) {
        isSystemApp = systemApp;
    }
}
