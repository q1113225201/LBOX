package com.sjl.lbox.app.DesignPattern.Strategy;

/**
 * Plus
 *
 * @author SJL
 * @date 2017/1/5
 */
//+
public class Plus extends AbstractCalculator implements ICalculator {
    @Override
    public int calculate(String str) {
        int[] arrayInt = split(str, "\\+");
        return arrayInt[0] + arrayInt[1];
    }
}
