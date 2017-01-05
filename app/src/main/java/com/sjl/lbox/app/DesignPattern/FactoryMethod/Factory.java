package com.sjl.lbox.app.DesignPattern.FactoryMethod;

/**
 * AbstractFactory
 *
 * @author SJL
 * @date 2017/1/4
 */

public class Factory {
    public static Product createProductA() {
        return new ProductA();
    }

    public static Product createProductB() {
        return new ProductB();
    }
}
