package com.sjl.lbox.app.DesignPattern.AbstractFactory;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 抽象工厂模式（Abstract AbstractFactory）
 *
 * @author SJL
 * @date 2017/1/4
 */
public class AbstractFactoryActivity extends BaseActivity {

    private TextView tvDefine;
    private TextView tvUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abstract_factory);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("提供一个创建一系列相关或相互依赖对象的接口，而无须指定它们具体的类。");
        tvUsage.setText("1、一个系统不应依赖产品类如何被创建、组合和表达的细节；\n" +
                "2、系统中有多个产品族，而每次只是用其中某一个产品族；\n" +
                "3、系统提供一个产品类的库，所有产品以同样接口出现，使客户端不依赖于具体实现；");

        //简单使用
        AbstractFactory abstractFactory = new AbstractFactory1();
        AbstractProductA abstractProductA = abstractFactory.createProductA();
        AbstractProductB abstractProductB = abstractFactory.createProductB();
        abstractProductA.getProductAType();
        abstractProductB.getProductBType();
    }
}
