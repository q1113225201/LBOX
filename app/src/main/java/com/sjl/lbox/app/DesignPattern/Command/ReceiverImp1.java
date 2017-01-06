package com.sjl.lbox.app.DesignPattern.Command;

/**
 * ReceiverImp1
 *
 * @author SJL
 * @date 2017/1/6
 */
//执行者1
public class ReceiverImp1 implements Receiver {
    @Override
    public void doSomething() {
        System.out.println("ReceiverImp1 doSomething!");
    }
}
