package com.sjl.lbox.app.DesignPattern.Mediator;

/**
 * Colleague
 *
 * @author SJL
 * @date 2017/1/9
 */
//同事抽象
public abstract class Colleague {
    private Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }

    public Mediator getMediator() {
        return mediator;
    }
    abstract void work();
}
