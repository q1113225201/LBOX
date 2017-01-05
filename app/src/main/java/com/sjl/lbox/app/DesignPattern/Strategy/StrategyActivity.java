package com.sjl.lbox.app.DesignPattern.Strategy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;

/**
 * 策略模式（strategy）
 * 
 * @author SJL
 * @date 2017/1/5
 */
public class StrategyActivity extends Activity {
    private TextView tvDefine;
    private TextView tvUsage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("定义一系列算法，将每一个算法封装起来，并让它们可以相互替换。策略模式让算法独立于使用它的客户而变化，也称为政策模式(Policy)。");
        tvUsage.setText("1、如果在一个系统里面有许多类，它们之间的区别仅在于它们的行为，那么使用策略模式可以动态地让一个对象在许多行为中选择一种行为；\n" +
                "2、一个系统需要动态地在几种算法中选择一种；\n" +
                "3、不希望客户端知道复杂的、与算法相关的数据结构，在具体策略类中封装算法和相关的数据结构，提高算法的保密性与安全性；");

        //简单使用
        ICalculator iCalculator = new Plus();
        iCalculator.calculate("1+1");
        iCalculator = new Minus();
        iCalculator.calculate("1-1");
    }
}
