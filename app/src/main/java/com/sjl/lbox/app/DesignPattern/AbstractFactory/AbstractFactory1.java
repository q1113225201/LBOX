package com.sjl.lbox.app.DesignPattern.AbstractFactory;

/**
 * AbstractFactory1
 *
 * @author SJL
 * @date 2017/1/5
 */

public class AbstractFactory1 extends AbstractFactory {
    @Override
    AbstractProductA createProductA() {
        return new AbstractProductA1();
    }

    @Override
    AbstractProductB createProductB() {
        return new AbstractProductB1();
    }
}
