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
    private TextView tvUsage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpreter);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("给定一个语言，定义它的语法，并定义一个解释器，这个解释器用于解析语言。");
        tvUsage.setText("1、一般主要应用在OOP开发中的编译器的开发中，所以适用面比较窄；");

        //简单使用

    }
}
