package com.sjl.lbox.app.DesignPattern.Prototype;

/**
 * MyPrototype
 *
 * @author SJL
 * @date 2017/1/4
 */

public class MyPrototype implements Cloneable {
    private int id;
    private String name;

    public MyPrototype(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new MyPrototype(id, name);
    }
}
