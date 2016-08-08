package com.sjl.lbox.config;

import android.os.Environment;

/**
 * @author SJL
 * @date 2016/8/8 23:38
 */
public class CacheConfig {
    /**
     * 基础路径
     */
    public static String PATH = Environment.getExternalStorageDirectory()+"/lbox";
    /**
     * Log日志路径
     */
    public static String LOG_PATH = PATH+"/log";
}
