package com.sjl.lbox.app.DesignPattern.Visitor;

/**
 * VisitorImp
 *
 * @author SJL
 * @date 2017/1/9
 */
//访问者实现
public class VisitorImp implements Visitor {
    @Override
    public void visit(Subject subject) {
        System.out.println("visit the subject:"+subject.getSubject());
    }
}
