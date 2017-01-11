package com.sjl.lbox.app.DesignPattern.Facade;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 外观模式（Facade）
 * 
 * @author SJL
 * @date 2017/1/10
 */
public class FacadeActivity extends BaseActivity {
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

        tvDefine.setText("要求一个子系统的外部与其内部的通信必须通过一个统一的对象进行，该模式提供一个高层次的接口使得子系统更易于使用。");
        tvAdvantage.setText("1、减少系统的相互依赖；\n" +
                "2、提高了灵活性；\n" +
                "3、提高了安全性；");
        tvUsage.setText("1、为一个复杂的模块或子系统提供一个供外部访问的接口；\n" +
                "2、子系统相对独立；\n" +
                "3、预防低水平人员带来的风险扩散；");

        //简单使用
        AutomaticWashingMachine automaticWashingMachine = new AutomaticWashingMachine();
        automaticWashingMachine.washingClothes();
    }
}
