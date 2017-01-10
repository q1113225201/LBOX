package com.sjl.lbox.app.DesignPattern.Flyweight;

/**
 * FlyweightReal1
 *
 * @author SJL
 * @date 2017/1/10
 */
//实际数据对象1
public class FlyweightReal1 extends Flyweight {

    public FlyweightReal1(String outState) {
        super(outState);
    }

    @Override
    void operate() {
        System.out.println(outState+" operate");
    }
}
