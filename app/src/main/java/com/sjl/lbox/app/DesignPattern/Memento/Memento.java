package com.sjl.lbox.app.DesignPattern.Memento;

/**
 * Memento
 *
 * @author SJL
 * @date 2017/1/6
 */
//备份数据
public class Memento {
    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
