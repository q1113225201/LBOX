package com.sjl.lbox.app.DesignPattern.FactoryMethod;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;

/**
 * 工厂方法模式（AbstractFactory Method）
 *
 * @author SJL
 * @date 2017/1/4
 */
public class FactoryMethodActivity extends Activity {
    private TextView tvDefine;
    private TextView tvUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory_method);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("定义一个创建对象的接口，让子类决定实例化哪个类。");
        tvUsage.setText("1、有大量产品需要创建，并且具有共同接口，可以通过工厂方法模式创建；");

        //简单使用
        Product product = Factory.createProductA();
        product.getProductType();
    }
}
