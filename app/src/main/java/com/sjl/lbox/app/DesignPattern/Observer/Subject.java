package com.sjl.lbox.app.DesignPattern.Observer;

/**
 * Subject
 *
 * @author SJL
 * @date 2017/1/6
 */
//目标接口
public interface Subject {
    void addObserver(Observer observer);
    void delObserver(Observer observer);
    void notifyObservers();
}
