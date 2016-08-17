package com.sjl.lbox.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

/**
 * 字符串工具类
 *
 * @author SJL
 * @date 2016/8/17 19:34
 */
public class StringUtil {
    /**
     * 判断是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null) || ("null".equalsIgnoreCase(str)) || (str.length() == 0);
    }
    /**
     * 将null转化成""
     *
     * @param str
     * @return
     */
    public static String parseEmpty(String str) {
        if (str == null || str.trim().equals("null")) {
            str = "";
        }
        return str.trim();
    }
    /**
     * 新建一个可以添加属性的文本对象
     * @param text
     * @param size
     * @return
     */
    public static SpannableString buildSpannableString(String text, int size){
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(text);

        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size,true);

        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ss;
    }
}
