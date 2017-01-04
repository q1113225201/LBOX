package com.sjl.lbox.app.DesignPattern.Singleton;

/**
 * 单例对象
 *
 * @author SJL
 * @date 2017/1/4
 */

public class Singleton {
    private static Singleton instance;

    //将默认的构造函数私有化，防止其他类手动new对象
    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            //避免不必要的同步
            synchronized (Singleton.class) {
                if (instance == null) {
                    //确保没有其他线程进入过synchronized块创建实例
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
