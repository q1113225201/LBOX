package com.sjl.lbox.app.DesignPattern.Bridge;

/**
 * MyBridge
 *
 * @author SJL
 * @date 2017/1/10
 */
//自定义的桥
public class MyBridge extends Bridge {
    @Override
    public void method() {
        getSource().method();
    }
}
