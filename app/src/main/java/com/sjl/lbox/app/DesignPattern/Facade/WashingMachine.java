package com.sjl.lbox.app.DesignPattern.Facade;

/**
 * WashingMachine
 *
 * @author SJL
 * @date 2017/1/10
 */
//自动洗衣机
public class WashingMachine {
    public void shuixi(){
        System.out.println("水洗");
    }
    public void piaoxi(){
        System.out.println("漂洗");
    }
    public void tuoshui(){
        System.out.println("脱水");
    }
}
