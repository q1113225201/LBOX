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
    private TextView tvUsage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("将一个类的接口变换成客户端所期待的另一种接口，从而使原本因接口不匹配而无法在一起工作的两个类能够在一起工作。");
        tvUsage.setText("1、类的适配器模式：希望一个类转换成满足另一个新接口的类时，可使用该模式创建一个新类继承原有类实现新接口；\n" +
                "2、对象的适配模式：希望一个对象转换成满足另一个新接口的对象时，可创建一个新类持有原有类的一个实例，在新类中调用实例的方法；\n" +
                "3、接口的适配模式：不希望实现一个接口中所有方法时，可创建一个抽象类实现所有方法，写其他类的时候继承抽象类即可；");

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
