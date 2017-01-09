package com.sjl.lbox.app.DesignPattern.Mediator;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 中介者模式（Mediator）
 *
 * @author SJL
 * @date 2017/1/9
 */
public class MediatorActivity extends BaseActivity {
    private TextView tvDefine;
    private TextView tvUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediator);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("用一个中介对象封装一系列的对象交互，中介者使各对象不需要显示地相互作用，从而使其耦合松散，而且可以独立地改变它们之间的交互。");
        tvUsage.setText("1、适用于多个对象之间紧密耦合的情况，紧密耦合的标准是：在类图中出现蜘蛛网状结构。");

        //简单使用
        Mediator mediator = new MediatorImp();
        mediator.createMediator();
        mediator.workAll();
    }
}
