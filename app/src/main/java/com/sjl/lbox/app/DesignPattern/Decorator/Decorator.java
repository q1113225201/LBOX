package com.sjl.lbox.app.DesignPattern.Decorator;

/**
 * Decorator
 *
 * @author SJL
 * @date 2017/1/10
 */
//装饰类
public class Decorator implements Source {
    private Source source;

    public Decorator(Source source) {
        super();
        this.source = source;
    }

    @Override
    public void method() {
        System.out.println("Decorator method before");
        source.method();
        System.out.println("Decorator method after");
    }
}
