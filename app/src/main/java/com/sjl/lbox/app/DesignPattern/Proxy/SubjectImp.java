package com.sjl.lbox.app.DesignPattern.Proxy;

/**
 * SubjectImp
 *
 * @author SJL
 * @date 2017/1/9
 */
//目标实现
public class SubjectImp implements Subject {
    @Override
    public void method() {
        System.out.println("SubjectImp method");
    }
}
