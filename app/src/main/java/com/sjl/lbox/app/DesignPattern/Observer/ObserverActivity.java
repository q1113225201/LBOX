package com.sjl.lbox.app.DesignPattern.Observer;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
/**
 * 观察者模式（Observer）
 *
 * @author SJL
 * @date 2017/1/6
 */
public class ObserverActivity extends BaseActivity {
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

        tvDefine.setText("定义对象间的一种一对多依赖关系，使得每当一个对象状态发生改变时，其相关依赖对象皆得到通知并被自动更新。");
        tvAdvantage.setText("1、可以实现表示层和数据逻辑层的分离，并定义了稳定的消息更新传递机制，抽象了更新接口，使得可以有各种各样不同的表示层作为具体观察者角色;\n" +
                "2、在观察目标和观察者之间建立一个抽象的耦合；\n" +
                "3、支持广播通信；\n" +
                "4、符合“开闭原则”的要求；");
        tvUsage.setText("1、凡是涉及到一对一或者一对多的对象交互场景都可以使用观察者模式；");

        //简单使用
        MySubject mySubject = new MySubject();
        //添加观察者
        mySubject.addObserver(new ObserverImp1());
        mySubject.addObserver(new ObserverImp2());
        //执行操作
        mySubject.operation();
    }
}
