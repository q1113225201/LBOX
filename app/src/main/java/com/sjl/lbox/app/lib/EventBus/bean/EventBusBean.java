package com.sjl.lbox.app.lib.EventBus.bean;

/**
 * EventBusBean
 *
 * @author SJL
 * @date 2017/3/29
 */

public class EventBusBean {
    private int id;
    private String name;

    public EventBusBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "EventBusBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
