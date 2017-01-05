package com.sjl.lbox.app.DesignPattern.ChainOfResponsibility;

/**
 * AbstratorMyHandler
 *
 * @author SJL
 * @date 2017/1/5
 */
//自定义处理类抽象
public abstract class AbstratorMyHandler {
    private MyHandler myHandler;

    public MyHandler getMyHandler() {
        return myHandler;
    }

    public void setMyHandler(MyHandler myHandler) {
        this.myHandler = myHandler;
    }
}
