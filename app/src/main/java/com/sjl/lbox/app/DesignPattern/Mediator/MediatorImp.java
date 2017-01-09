package com.sjl.lbox.app.DesignPattern.Mediator;

/**
 * MediatorImp
 *
 * @author SJL
 * @date 2017/1/9
 */
//中介者实现
public class MediatorImp implements Mediator {
    private Colleague colleague1;
    private Colleague colleague2;

    @Override
    public void createMediator() {
        colleague1 = new Colleague1(this);
        colleague2 = new Colleague2(this);
    }

    @Override
    public void workAll() {
        colleague1.work();
        colleague2.work();
    }
}
