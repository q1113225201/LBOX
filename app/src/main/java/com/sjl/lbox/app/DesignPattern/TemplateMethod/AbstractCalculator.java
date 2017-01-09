package com.sjl.lbox.app.DesignPattern.TemplateMethod;

/**
 * AbstractCalculator
 *
 * @author SJL
 * @date 2017/1/9
 */
//计算模版
public abstract class AbstractCalculator {
    //调用方法，实现对其他方法的调用
    public final int calculator(String exp, String opt) {
        int[] array = split(exp, opt);
        return calculator(array[0], array[1]);
    }

    //子类重写的计算方法
    abstract int calculator(int num1, int num2);

    public int[] split(String exp, String opt) {
        String[] arrayStr = exp.split(opt);
        int[] arrayInt = new int[2];
        arrayInt[0] = Integer.parseInt(arrayStr[0]);
        arrayInt[1] = Integer.parseInt(arrayStr[1]);
        return arrayInt;
    }
}
