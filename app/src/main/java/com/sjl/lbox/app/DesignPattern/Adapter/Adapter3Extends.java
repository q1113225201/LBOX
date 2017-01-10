package com.sjl.lbox.app.DesignPattern.Adapter;

/**
 * Adapter3Extends
 *
 * @author SJL
 * @date 2017/1/9
 */
//接口适配器继承类
public class Adapter3Extends extends Adapter3 {
    @Override
    public void method2() {
        super.method2();
        System.out.println("method2");
    }
}
