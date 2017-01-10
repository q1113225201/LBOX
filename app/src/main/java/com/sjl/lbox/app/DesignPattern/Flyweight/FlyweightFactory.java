package com.sjl.lbox.app.DesignPattern.Flyweight;

import java.util.HashMap;

/**
 * FlyweightFactory
 *
 * @author SJL
 * @date 2017/1/10
 */
//数据获取工厂
public class FlyweightFactory {
    //定义一个池容量
    private static HashMap<String, Flyweight> pool = new HashMap<String, Flyweight>();

    public static Flyweight getFlyweight(String outState) {
        //结果数据
        Flyweight flyweight = null;
        if (pool.containsKey(outState)) {
            //池中有该对象，获取该对象
            flyweight = pool.get(outState);
        } else {
            //池中无该对象，创建并放入池中
            flyweight = new FlyweightReal1(outState);
            pool.put(outState, flyweight);
        }
        return flyweight;
    }
}
