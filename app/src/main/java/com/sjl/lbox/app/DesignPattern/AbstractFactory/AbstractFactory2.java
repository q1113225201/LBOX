package com.sjl.lbox.app.DesignPattern.AbstractFactory;

/**
 * AbstractFactory2
 *
 * @author SJL
 * @date 2017/1/5
 */

public class AbstractFactory2 extends AbstractFactory {
    @Override
    AbstractProductA createProductA() {
        return new AbstractProductA2();
    }

    @Override
    AbstractProductB createProductB() {
        return new AbstractProductB2();
    }
}
