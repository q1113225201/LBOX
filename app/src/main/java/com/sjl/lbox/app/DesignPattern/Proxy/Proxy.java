package com.sjl.lbox.app.DesignPattern.Proxy;

/**
 * Proxy
 *
 * @author SJL
 * @date 2017/1/9
 */
//代理
public class Proxy implements Subject {
    private Subject subject;

    public Proxy() {
        this.subject = new SubjectImp();
    }

    @Override
    public void method() {
        before();
        subject.method();
        after();
    }

    private void before() {
        System.out.println("Proxy before");
    }

    private void after() {
        System.out.println("Proxy after");
    }
}
