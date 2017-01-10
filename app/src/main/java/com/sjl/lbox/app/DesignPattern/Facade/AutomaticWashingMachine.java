package com.sjl.lbox.app.DesignPattern.Facade;

/**
 * AutomaticWashingMachine
 *
 * @author SJL
 * @date 2017/1/10
 */
//自动洗衣机
public class AutomaticWashingMachine {
    private WashingMachine washingMachine = new WashingMachine();
    public void washingClothes(){
        washingMachine.shuixi();
        washingMachine.piaoxi();
        washingMachine.tuoshui();
    }
}
