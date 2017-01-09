package com.sjl.lbox.app.DesignPattern.Mediator;

/**
 * Colleague2
 *
 * @author SJL
 * @date 2017/1/9
 */
//同事2
public class Colleague2 extends Colleague {
    public Colleague2(Mediator mediator) {
        super(mediator);
    }

    @Override
    void work() {
        System.out.println("Colleague2 work");
    }
}
