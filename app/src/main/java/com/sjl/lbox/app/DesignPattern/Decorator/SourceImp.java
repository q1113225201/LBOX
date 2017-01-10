package com.sjl.lbox.app.DesignPattern.Decorator;

/**
 * SourceImp
 *
 * @author SJL
 * @date 2017/1/10
 */
//源实现类
public class SourceImp implements Source {
    @Override
    public void method() {
        System.out.println("SourceImp method");
    }
}
