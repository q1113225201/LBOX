package com.sjl.lbox.app.DesignPattern.AbstractFactory;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 抽象工厂模式（Abstract Factory）
 *
 * @author SJL
 * @date 2017/1/4
 */
public class AbstractFactoryActivity extends BaseActivity {

    private TextView tvDefine;
    private TextView tvAdvantage;
    private TextView tvUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_pattern_item);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvAdvantage = (TextView) findViewById(R.id.tvAdvantage);
        tvUsage = (TextView) findViewById(R.id.tvUsage);

        tvDefine.setText("为创建一组相关或相互依赖的对象提供一个接口，而且无须制定他们的具体类。");
        tvAdvantage.setText("1、抽象工厂模式隔离了具体类的生成，使得客户并不需要知道什么被创建，可实现；\n" +
                "2、当一个产品族中的多个对象被设计成一起工作时，能保证客户端始终只使用同一个产品族中对象；\n" +
                "3、增加新的具体工厂和产品族，无须修改已有系统，符合“开闭原则”；");
        tvUsage.setText("1、一个产品族都有相同的约束；");

        //简单使用
        AbstractFactory abstractFactory = new AbstractFactory1();
        AbstractProductA abstractProductA = abstractFactory.createProductA();
        AbstractProductB abstractProductB = abstractFactory.createProductB();
        abstractProductA.getProductAType();
        abstractProductB.getProductBType();
    }
}
