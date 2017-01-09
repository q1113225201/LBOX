package com.sjl.lbox.app.DesignPattern.Mediator;

/**
 * Colleague1
 *
 * @author SJL
 * @date 2017/1/9
 */
//同事1
public class Colleague1 extends Colleague {
    public Colleague1(Mediator mediator) {
        super(mediator);
    }

    @Override
    void work() {
        System.out.println("Colleague1 work");
    }
}
