package com.sjl.lbox.app.DesignPattern.FactoryMethod;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 工厂方法模式（Factory Method）
 *
 * @author SJL
 * @date 2017/1/4
 */
public class FactoryMethodActivity extends BaseActivity {

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

        tvDefine.setText("定义一个用于创建对象的接口，让子类决定实例化哪一个类。工厂方法使一个类的实例化延迟到其子类。");
        tvAdvantage.setText("1、良好的封装性，代码结构清晰；\n" +
                "2、扩展性好；\n" +
                "3、屏蔽产品类；\n" +
                "4、符合迪米特法则、依赖倒置原则和里氏替换原则，是典型的解耦框架；");
        tvUsage.setText("1、有大量产品需要创建，并且具有共同接口，可以通过工厂方法模式创建；\n" +
                "2、需要灵活的、可扩展的框架时；");

        //简单使用
        Product product = Factory.createProductA();
        product.getProductType();
    }
}
