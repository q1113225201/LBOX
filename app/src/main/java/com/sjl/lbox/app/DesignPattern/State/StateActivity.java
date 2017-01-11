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

        tvDefine.setText("当一个对象内在状态改变时允许其改变行为，这个对象看起来像改变了其类。");
        tvAdvantage.setText("1、结构清晰，避免了switch...case或if...else使用，避免了程序的复杂程度，提高系统可维护性；\n" +
                "2、遵循设计原则，体现开闭原则和单一职责原则，每个状态都是一个类，增加或改变状态都只要修改一个子类；\n" +
                "3、封装性好，状态变换放置在类内部，外部调用不用知道内部状态和行为变换；");
        tvUsage.setText("1、行为随状态改变而改变的场景；\n" +
                "2、条件、分支判断语句的替代者；");

        //简单使用
        ElevatorController elevatorController = new ElevatorControllerImp();
        elevatorController.up();
        elevatorController.getFloor();
        elevatorController.down();
        elevatorController.getFloor();
    }
}
