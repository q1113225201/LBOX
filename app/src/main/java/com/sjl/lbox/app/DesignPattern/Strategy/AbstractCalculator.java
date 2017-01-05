package com.sjl.lbox.app.DesignPattern.Strategy;

/**
 * AbstractCalculator
 *
 * @author SJL
 * @date 2017/1/5
 */
//辅助类
public abstract class AbstractCalculator {
    public int[] split(String exp, String opt) {
        String[] arrayStr = exp.split(opt);
        int[] arrayInt = new int[2];
        arrayInt[0] = Integer.parseInt(arrayStr[0]);
        arrayInt[1] = Integer.parseInt(arrayStr[1]);
        return arrayInt;
    }
}
