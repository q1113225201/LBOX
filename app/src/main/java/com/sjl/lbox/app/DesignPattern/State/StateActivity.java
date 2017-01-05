package com.sjl.lbox.app.DesignPattern.State;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 状态模式（State）
 *
 * @author SJL
 * @date 2017/1/5
 */
public class StateActivity extends BaseActivity {
    private TextView tvDefine;
    private TextView tvUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("允许一个对象在其内部状态改变时改变它的行为，对象看起来似乎修改了它的类。其别名为状态对象(Objects for States)，状态模式是一种对象行为型模式。");
        tvUsage.setText("1、对象的行为依赖于它的状态（属性）并且可以根据它的状态改变而改变它的相关行为；\n" +
                "2、代码中包含大量与对象状态有关的条件语句，这些条件语句的出现，会导致代码的可维护性和灵活性变差，不能方便地增加和删除状态，使客户类与类库之间的耦合增强。；\n");

        //简单使用
        ElevatorController elevatorController = new ElevatorControllerImp();
        elevatorController.up();
        elevatorController.getFloor();
        elevatorController.down();
        elevatorController.getFloor();
    }
}
