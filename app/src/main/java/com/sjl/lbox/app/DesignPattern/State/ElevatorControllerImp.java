package com.sjl.lbox.app.DesignPattern.State;

/**
 * ElevatorControllerImp
 *
 * @author SJL
 * @date 2017/1/5
 */
//电梯控制类
public class ElevatorControllerImp implements ElevatorController {
    private Elevator elevator;

    @Override
    public void up() {
        elevator = new ElevatorUp();
    }

    @Override
    public void down() {
        elevator = new ElevatorDown();
    }

    public String getFloor() {
        return elevator.getFloor();
    }
}
