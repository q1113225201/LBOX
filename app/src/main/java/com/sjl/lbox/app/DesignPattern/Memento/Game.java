package com.sjl.lbox.app.DesignPattern.Memento;

/**
 * Game
 *
 * @author SJL
 * @date 2017/1/6
 */
//游戏
public class Game {
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Memento createMemento() {
        return new Memento(state);
    }

    public void restoreMemento(Memento memento) {
        this.state = memento.getState();
    }
}
