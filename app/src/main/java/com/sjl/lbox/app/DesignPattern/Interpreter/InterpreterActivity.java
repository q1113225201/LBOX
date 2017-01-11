package com.sjl.lbox.app.DesignPattern.Interpreter;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
/**
 * 解释器模式（Interpreter）
 * 
 * @author SJL
 * @date 2017/1/5
 */
public class InterpreterActivity extends BaseActivity {
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

        tvDefine.setText("给定一门语言，定义它的文法的一种表示，并定义一个解释器，这个解释器用于解析语言。");
        tvAdvantage.setText("1、扩展性，修改语法规则只要修改相应的非终结符表达式即可；");
        tvUsage.setText("1、重复发生的问题可以使用解释其模式；\n" +
                "2、一个简单语法需要解释的场景；");

        //简单使用

    }
}
