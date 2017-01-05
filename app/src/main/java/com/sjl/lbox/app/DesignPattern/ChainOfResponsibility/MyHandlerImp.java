package com.sjl.lbox.app.DesignPattern.ChainOfResponsibility;

/**
 * MyHandlerImp
 *
 * @author SJL
 * @date 2017/1/5
 */
//自定义实现类
public class MyHandlerImp extends AbstratorMyHandler implements MyHandler {
    private String name;

    public MyHandlerImp(String name) {
        this.name = name;
    }

    @Override
    public void operator() {
        System.out.println(name+" operator!");
        if(getMyHandler()!=null){
            getMyHandler().operator();
        }
    }
}
