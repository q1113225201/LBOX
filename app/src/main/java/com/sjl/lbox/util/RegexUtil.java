package com.sjl.lbox.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dessmann on 16/7/12.
 */
public class RegexUtil {
    private static String tag = RegexUtil.class.getSimpleName();

    // 正则通用验证
    public static boolean regexCheck(String res, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(res);
        return matcher.matches();
    }

    // 正则验证 x到y个任意字符
    public static boolean regex_X_Y_any_Chars(String res, int x, int y) {
        // if(regexCheck(res,
        // "^[A-Za-z_]+[A-Za-z0-9_]+$")){//只包含字母、数字、下划线，并且只能以字母或者下划线开头
        // return true;
        // }
        return !(x < 0 || y < 0 || x >= y) && regexCheck(res, "^.{" + x + "," + y + "}$");
    }

    // 正则验证 x到y个中文字符
    public static boolean regex_X_Y_chinese_Chars(String res, int x, int y) {
        return !(x < 0 || y < 0 || x >= y) && regexCheck(res, "^[\u4E00-\u9FA5]{" + x + "," + y + "}$");
    }

    // 正则验证 x到y个阿拉伯数字
    public static boolean regex_X_Y_numbers_Chars(String res, int x, int y) {
        return !(x < 0 || y < 0 || x >= y) && regexCheck(res, "^[0-9]{" + x + "," + y + "}$");
    }

    // 验证字符串res是否是x位的不是空白符的字符串
    public static boolean regex_X_Not_Empty_Chars(String res, int x) {
        return x > 0 && regexCheck(res, "^(\\S){" + x + "}$");
    }

    // 正则验证 11位数字手机号码
    public static boolean regexMobile(String res) {
        // if(regexCheck(res, "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$")){
        // return true;
        // }
        // return false;

        //客户端只验证长度
        return regexCheck(res, "^[0-9]{11}$");
        //		if (regexCheck(res, "^[1]([3]|[4]|[5]|[7]|[8])[0-9]{9}$")) {
//			return true;
//		}
//		return false;
    }

    // 身份证验证
    public static boolean regexIDCard(String res) {
        return regexCheck(res, "^\\d{15}|(\\d{17}([0-9]|X|x))$");
    }

    //X位纯数字
    public static boolean regexXNumbers(String res, int x) {
        return regexCheck(res, "^[0-9]{" + x + "}$");
    }

    /**
     * @param str
     * @param minLength 最短长度
     * @param maxLength 最长长度
     * @return
     */
    public static boolean regexChars(String str, int minLength, int maxLength) {
        return regexCheck(str, "^.{" + minLength + "," + maxLength + "}$");
    }

    /**
     * @param str
     * @param minLength 最短长度
     * @param maxLength 最长长度
     * @return
     */
    public static boolean regexChinese(String str, int minLength, int maxLength) {
        return regexCheck(str, "^[\u4E00-\u9FA5]{" + minLength + "," + maxLength + "}$");
    }

    /**
     * 通过正则表达式来判断。只允许显示字母、数字和汉字。
     *
     * @param str
     * @return
     */
    public static String regexSpecialChar(String str) {
        // 只允许字母、数字和汉字
        String reg = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str.replaceAll(" ", ""));
        LogUtil.i(tag, matcher.replaceAll(" ").trim());
        return matcher.replaceAll(" ").trim();
    }
}
