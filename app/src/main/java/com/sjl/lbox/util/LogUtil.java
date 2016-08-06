package com.sjl.lbox.util;

import android.util.Log;

import com.sjl.lbox.config.DebugConfig;

/**
 * 日志工具类
 *
 * @author SJL
 * @date 2016/8/6 17:20
 */
public class LogUtil {
    public static void i(String tag,String msg){
        if(DebugConfig.isDebug){
            Log.i(tag,msg);
        }
    }
}
