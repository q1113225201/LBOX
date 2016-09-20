package com.sjl.lbox.util;

import android.util.Log;

import com.sjl.lbox.config.CacheConfig;
import com.sjl.lbox.config.DebugConfig;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 带日志文件输入的，又可控开关的日志调试
 *
 * @author BaoHang
 * @version 1.0
 * @data 2012-2-20
 */
public class LogUtil {
    private static String d_tag = "LogUtil";
    private static Boolean LOG_SWITCH = DebugConfig.isDebug; // 日志文件总开关
    private static Boolean LOG_WRITE_TO_FILE = DebugConfig.isDebug;// 日志写入文件开关
    private static char LOG_TYPE = 'v';// 输入日志类型，w代表只输出告警信息等，v代表输出所有信息
    private static int SAVE_DAYS = 1;// 最多保存天数
    private static String LOG_FILEPATH = CacheConfig.LOG_PATH;// 本类输出的日志文件名称
    private static String LOG_FILENAME = "log.txt";// 本类输出的日志文件名称
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 日志的输出格式
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式

    public static void w(Object msg) { // 警告信息  
        log(d_tag, msg.toString(), 'w');
    }

    public static void e(Object msg) { // 错误信息  
        log(d_tag, msg.toString(), 'e');
    }

    public static void d(Object msg) {// 调试信息  
        log(d_tag, msg.toString(), 'd');
    }

    public static void i(Object msg) {//  
        log(d_tag, msg.toString(), 'i');
    }

    public static void v(Object msg) {
        log(d_tag, msg.toString(), 'v');
    }

    public static void w(String text) {
        log(d_tag, text, 'w');
    }

    public static void e(String text) {
        log(d_tag, text, 'e');
    }

    public static void d(String text) {
        log(d_tag, text, 'd');
    }

    public static void i(String text) {
        log(d_tag, text, 'i');
    }

    public static void v(String text) {
        log(d_tag, text, 'v');
    }

    public static void w(String tag, Object msg) { // 警告信息  
        log(tag, msg.toString(), 'w');
    }

    public static void e(String tag, Object msg) { // 错误信息  
        log(tag, msg.toString(), 'e');
    }

    public static void d(String tag, Object msg) {// 调试信息  
        log(tag, msg.toString(), 'd');
    }

    public static void i(String tag, Object msg) {//  
        log(tag, msg.toString(), 'i');
    }

    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), 'v');
    }

    public static void w(String tag, String text) {
        log(tag, text, 'w');
    }

    public static void e(String tag, String text) {
        log(tag, text, 'e');
    }

    public static void d(String tag, String text) {
        log(tag, text, 'd');
    }

    public static void i(String tag, String text) {
        log(tag, text, 'i');
    }

    public static void v(String tag, String text) {
        log(tag, text, 'v');
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag
     * @param msg
     * @param level
     */
    private static void log(String tag, String msg, char level) {
        if (LOG_SWITCH) {
            if ('e' == level && ('e' == LOG_TYPE || 'v' == LOG_TYPE)) { // 输出错误信息
                Log.e(tag, msg);
            } else if ('w' == level && ('w' == LOG_TYPE || 'v' == LOG_TYPE)) {
                Log.w(tag, msg);
            } else if ('d' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {
                Log.d(tag, msg);
            } else if ('i' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {
                Log.i(tag, msg);
            } else {
                Log.v(tag, msg);
            }
            if (LOG_WRITE_TO_FILE) {
                writeLogtoFile(String.valueOf(level), tag, msg);
            }
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @return
     **/
    private static void writeLogtoFile(String mylogtype, String tag, String text) {// 新建或打开日志文件
        Date nowtime = new Date();
        String msg = sdf.format(nowtime) + "    " + mylogtype + "    " + tag + "    " + text + "\n";
        try {
            FileUtil.writeFile(LOG_FILEPATH + sdf.format(nowtime) + ".txt", msg, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除制定的日志文件
     */
    public static void delFile() {// 删除日志文件
        String needDelFile = logfile.format(getDateBefore());
        FileUtil.deleteFile(needDelFile + LOG_FILENAME);
    }

    /**
     * 得到现在时间前的几天日期，用来得到需要删除的日志文件名
     */
    private static Date getDateBefore() {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - SAVE_DAYS);
        return now.getTime();
    }

}  