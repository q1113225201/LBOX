package com.sjl.lbox.app.DesignPattern.Command;

/**
 * Invoker
 *
 * @author SJL
 * @date 2017/1/6
 */
//调用者
public class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void action() {
        command.execute();
    }
}
