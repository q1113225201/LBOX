package com.sjl.lbox.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 网络工具类
 *
 * @author SJL
 * @date 2016/8/6 16:02
 */
public class NetWorkUtil {
    private static String tag = NetWorkUtil.class.getSimpleName();

    /**
     * 判断当前网络是否可用
     *
     * @return 如果可用返回true，否则返回false
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            LogUtil.i(tag, "Network Unavailable");
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        LogUtil.i(tag, "Network Available");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取当前网络状态
     * 有网络时
     *
     * @return -1
     */
    public static int getNetWorkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return -1;
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return -1;
        }
        return networkInfo.getType();
    }

    /**
     * 获取当前网络状态
     * 有网络时
     *
     * @return NULL
     */
    public static String getNetWorkName(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return null;
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return null;
        }
        return networkInfo.getTypeName();
    }

    /**
     * 打开WIFI设置界面
     *
     * @param context
     */
    public static void openSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 返回ip地址
     *
     * @return
     */
    public static String getIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        int intIp = info.getIpAddress();
        byte[] bytes = int2byte(intIp);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(bytes[i] & 0xFF);
            if (i < 3) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    private static byte[] int2byte(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (0xff & i);
        bytes[1] = (byte) ((0xff00 & i) >> 8);
        bytes[2] = (byte) ((0xff0000 & i) >> 16);
        bytes[3] = (byte) ((0xff000000 & i) >> 24);
        return bytes;
    }

    /**
     * 获得当前WIFI连接的速度
     *
     * @return
     */
    public static int getLinkSpeed(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        return info.getLinkSpeed();
    }

    /**
     * 获得MAC地址
     *
     * @return
     */
    public static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 获得Wifi的状态
     *
     * @return One of
     * {@link WifiManager#WIFI_STATE_DISABLED},
     * {@link WifiManager#WIFI_STATE_DISABLING},
     * {@link WifiManager#WIFI_STATE_ENABLED},
     * {@link WifiManager#WIFI_STATE_ENABLING},
     * {@link WifiManager#WIFI_STATE_UNKNOWN}
     */
    public static int getWifiState(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getWifiState();
    }

    /**
     * 将域名解析成ip
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getIpByDomainName(String domainName)
            throws UnknownHostException {
        InetAddress address = InetAddress.getByName(domainName);
        return address.getHostAddress();
    }
}
