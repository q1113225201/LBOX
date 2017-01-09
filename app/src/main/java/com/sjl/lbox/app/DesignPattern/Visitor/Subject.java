package com.sjl.lbox.app.DesignPattern.Visitor;

/**
 * Subject
 *
 * @author SJL
 * @date 2017/1/9
 */
//目标接口
public interface Subject {
    void accept(Visitor visitor);
    String getSubject();
}
