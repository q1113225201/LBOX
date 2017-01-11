package com.sjl.lbox.app.DesignPattern.Adapter;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
/**
 * 适配器模式（Adapter）
 * 
 * @author SJL
 * @date 2017/1/9
 */
public class AdapterActivity extends BaseActivity {
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

        tvDefine.setText("将一个类的接口变换成客户端所期待的另一种接口，从而使原本因接口不匹配而无法在一起工作的两个类能够在一起工作。");
        tvAdvantage.setText("1、让两个没有任何关系的类在一起运行；\n" +
                "2、增加类的透明性；\n" +
                "3、提高了类的复用度；\n" +
                "4、灵活性非常好；");
        tvUsage.setText("1、想修改一个已经在使用中的接口；");

        //简单使用
        //类适配
        ITarget iTarget1 = new Adapter1();
        iTarget1.method1();
        iTarget1.method2();
        //对象适配
        ITarget iTarget2 = new Adapter2(new Source());
        iTarget2.method1();
        iTarget2.method2();
        //接口适配
        ITarget iTarget3 = new Adapter3Extends();
        iTarget3.method1();
        iTarget3.method2();
    }
}
