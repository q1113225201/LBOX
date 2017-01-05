package com.sjl.lbox.app.DesignPattern.Strategy;

/**
 * Minus
 *
 * @author SJL
 * @date 2017/1/5
 */
//-
public class Minus extends AbstractCalculator implements ICalculator {
    @Override
    public int calculate(String str) {
        int[] arrayInt = split(str, "-");
        return arrayInt[0] - arrayInt[1];
    }
}
