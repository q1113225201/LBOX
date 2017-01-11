package com.sjl.lbox.app.DesignPattern.Strategy;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 策略模式（strategy）
 * 
 * @author SJL
 * @date 2017/1/5
 */
public class StrategyActivity extends BaseActivity {
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

        tvDefine.setText("定义一系列算法，将每一个算法封装起来，并让它们可以相互替换。");
        tvAdvantage.setText("1、策略模式提供了对“开闭原则”的完美支持，用户可以在不修改原有系统的基础上选择算法或行为，也可以灵活地增加新的算法或行为；\n" +
                "2、使用策略模式可以避免使用多重条件转移语句；");
        tvUsage.setText("1、对外提供算法可以自由切换的策略；\n" +
                "2、避免使用多重条件判断；\n" +
                "3、扩展性好，在现有策略上增加新策略不需要改变原有方法；");

        //简单使用
        ICalculator iCalculator = new Plus();
        iCalculator.calculate("1+1");
        iCalculator = new Minus();
        iCalculator.calculate("1-1");
    }
}
