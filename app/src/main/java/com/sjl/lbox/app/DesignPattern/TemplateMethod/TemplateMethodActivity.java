package com.sjl.lbox.app.DesignPattern.TemplateMethod;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
/**
 * 模板方法模式（Template Method）
 * 
 * @author SJL
 * @date 2017/1/9
 */
public class TemplateMethodActivity extends BaseActivity {
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

        tvDefine.setText("定义一个操作中的算法的框架，而将一些步骤延迟到子类中。使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。");
        tvAdvantage.setText("1、封装不变部分，扩展可变部分；\n" +
                "2、提取公共部分代码，便于维护；\n" +
                "3、行为由父类控制，子类实现；");
        tvUsage.setText("1、多个子类有公有的方法，并且逻辑基本相同时；\n" +
                "2、重要、复杂的算法，可以办核心算法设计成模版方法，周边相关细节由子类实现；\n" +
                "3、需要使用重构的地方；");

        //简单使用
        AbstractCalculator calculator = new Plus();
        calculator.calculator("1+2","\\+");
    }
}
