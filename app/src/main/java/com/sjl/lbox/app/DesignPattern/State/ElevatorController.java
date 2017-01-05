package com.sjl.lbox.app.DesignPattern.State;

/**
 * ElevatorController
 *
 * @author SJL
 * @date 2017/1/5
 */
//电梯控制接口
public interface ElevatorController {
    void up();
    void down();
    String getFloor();
}
