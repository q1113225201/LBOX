package com.sjl.lbox.app.DesignPattern.Bridge;

/**
 * Bridge
 *
 * @author SJL
 * @date 2017/1/10
 */
//桥抽象
public abstract class Bridge implements Source {
    private Source source;

    @Override
    public void method() {
        source.method();
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
