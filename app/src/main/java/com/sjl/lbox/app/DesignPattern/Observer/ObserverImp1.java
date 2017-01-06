package com.sjl.lbox.app.DesignPattern.Observer;

/**
 * ObserverImp1
 *
 * @author SJL
 * @date 2017/1/6
 */
//观察者1
public class ObserverImp1 implements Observer {
    @Override
    public void update() {
        System.out.println("ObserverImp1 update!");
    }
}
