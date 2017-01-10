package com.sjl.lbox.app.DesignPattern.Flyweight;

/**
 * Flyweight
 *
 * @author SJL
 * @date 2017/1/10
 */
//数据对象抽象
public abstract class Flyweight {
    //内部状态
    private String inState;
    //外部状态
    protected final String outState;

    public Flyweight(String outState) {
        this.outState = outState;
    }

    public String getInState() {
        return inState;
    }

    public void setInState(String inState) {
        this.inState = inState;
    }

    abstract void operate();
}
