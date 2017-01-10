package com.sjl.lbox.app.DesignPattern.Adapter;

/**
 * Adapter2
 *
 * @author SJL
 * @date 2017/1/9
 */
//对象适配器
public class Adapter2 implements ITarget {
    private Source source;

    public Adapter2(Source source) {
        this.source = source;
    }

    @Override
    public void method1() {
        source.method1();
    }

    @Override
    public void method2() {
        System.out.println("method2");
    }
}
