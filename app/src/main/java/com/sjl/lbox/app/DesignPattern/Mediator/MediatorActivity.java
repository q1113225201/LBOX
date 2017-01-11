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

        tvDefine.setText("用一个中介对象封装一系列的对象交互，中介者使各对象不需要显示地相互作用，从而使其耦合松散，而且可以独立地改变它们之间的交互。");
        tvAdvantage.setText("1、减少类间依赖，把原有的一对多的依赖变成一对一的依赖，同事类只依赖中介者，减少了依赖，当然同时也降低了类间的耦合；");
        tvUsage.setText("1、适用于多个对象之间紧密耦合的情况，紧密耦合的标准是：在类图中出现蜘蛛网状结构。");

        //简单使用
        Mediator mediator = new MediatorImp();
        mediator.createMediator();
        mediator.workAll();
    }
}
