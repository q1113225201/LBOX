package com.sjl.lbox.app.DesignPattern.Bridge;

/**
 * SourceImp1
 *
 * @author SJL
 * @date 2017/1/10
 */
//目标实现1
public class SourceImp1 implements Source {
    @Override
    public void method() {
        System.out.println("SourceImp1 method");
    }
}
