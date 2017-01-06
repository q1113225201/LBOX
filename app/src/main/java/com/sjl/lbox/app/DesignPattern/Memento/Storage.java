package com.sjl.lbox.app.DesignPattern.Memento;

/**
 * Storage
 *
 * @author SJL
 * @date 2017/1/6
 */
//存储
public class Storage {
    private Memento memento;

    public Storage(Memento memento) {
        this.memento = memento;
    }

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
