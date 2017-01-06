package com.sjl.lbox.app.DesignPattern.Observer;

/**
 * MySubject
 *
 * @author SJL
 * @date 2017/1/6
 */
//目标对象
public class MySubject extends AbstractSubject {

    @Override
    void operation() {
        System.out.println("MySubject operation");
        notifyObservers();
    }
}
