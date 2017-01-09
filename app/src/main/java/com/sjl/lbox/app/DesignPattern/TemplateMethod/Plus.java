package com.sjl.lbox.app.DesignPattern.TemplateMethod;

/**
 * Plus
 *
 * @author SJL
 * @date 2017/1/9
 */
//+
public class Plus extends AbstractCalculator {
    @Override
    int calculator(int num1, int num2) {
        return num1 + num2;
    }
}
