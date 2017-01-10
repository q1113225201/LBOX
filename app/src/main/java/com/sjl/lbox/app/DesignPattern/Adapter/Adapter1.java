package com.sjl.lbox.app.DesignPattern.Adapter;

/**
 * Adapter1
 *
 * @author SJL
 * @date 2017/1/9
 */
//类适配器
public class Adapter1 extends Source implements ITarget {
    @Override
    public void method2() {
        System.out.println("method2");
    }
}
