package com.sjl.lbox.app.DesignPattern.Observer;

import java.util.Enumeration;
import java.util.Vector;

/**
 * AbstractSubject
 *
 * @author SJL
 * @date 2017/1/6
 */
//目标抽象类
public abstract class AbstractSubject implements Subject {
    private Vector<Observer> vector = new Vector<Observer>();

    @Override
    public void addObserver(Observer observer) {
        vector.add(observer);
    }

    @Override
    public void delObserver(Observer observer) {
        vector.remove(observer);
    }

    @Override
    public void notifyObservers() {
        Enumeration<Observer> enumeration = vector.elements();
        while (enumeration.hasMoreElements()) {
            enumeration.nextElement().update();
        }
    }
    //具体操作
    abstract void operation();
}
