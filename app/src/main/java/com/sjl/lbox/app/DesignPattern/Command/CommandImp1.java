package com.sjl.lbox.app.DesignPattern.Command;

/**
 * CommandImp1
 *
 * @author SJL
 * @date 2017/1/6
 */
//命令1
public class CommandImp1 implements Command {
    private Receiver receiver;

    public CommandImp1(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.doSomething();
    }
}
