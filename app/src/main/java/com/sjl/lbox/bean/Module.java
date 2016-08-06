package com.sjl.lbox.bean;

/**
 * 模块
 *
 * @author SJL
 * @date 2016/8/6 14:19
 */
public class Module {
    private String title;
    private Class act;

    public Module(String title, Class act) {
        this.title = title;
        this.act = act;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getAct() {
        return act;
    }

    public void setAct(Class act) {
        this.act = act;
    }

    @Override
    public String toString() {
        return title;
    }
}
