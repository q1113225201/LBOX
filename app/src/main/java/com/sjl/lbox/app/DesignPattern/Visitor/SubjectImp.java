package com.sjl.lbox.app.DesignPattern.Visitor;

/**
 * SubjectImp
 *
 * @author SJL
 * @date 2017/1/9
 */
//目标实现
public class SubjectImp implements Subject {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getSubject() {
        return "SubjectImp";
    }
}
