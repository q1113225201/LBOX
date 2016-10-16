package com.sjl.lbox.view.gesture;

import java.util.List;

/**
 * 手势密码工具类
 * 
 * @author SJL
 * @date 2016/10/16 21:26
 */
public class GestureUtil {

    private static final String tag = GestureUtil.class.getSimpleName();

    /**
     * 获取绘制结果
     *
     * @param list
     * @return
     */
    public static String getSelectResult(List<GestureView.Point> list) {
        return parseList(list);
    }

    private static String parseList(List<GestureView.Point> list) {
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = (byte) list.get(i).num;
        }
        return parseBytes(bytes);
    }

    private static String parseBytes(byte[] bytes) {
        String result = "";
        for (byte item : bytes) {
            result += item;
        }
        return result;
    }
}
