package com.sjl.lbox.app.DesignPattern.Iterator;

/**
 * Collection
 *
 * @author SJL
 * @date 2017/1/6
 */
//收集器
public interface Collection {
    Iterator iterator();
    Object get(int index);
    void add(Object object);
    int size();
}
