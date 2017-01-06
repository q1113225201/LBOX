package com.sjl.lbox.app.DesignPattern;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.DesignPattern.AbstractFactory.AbstractFactoryActivity;
import com.sjl.lbox.app.DesignPattern.Builder.BuilderActivity;
import com.sjl.lbox.app.DesignPattern.ChainOfResponsibility.ChainOfResponsibilityActivity;
import com.sjl.lbox.app.DesignPattern.Command.CommandActivity;
import com.sjl.lbox.app.DesignPattern.FactoryMethod.FactoryMethodActivity;
import com.sjl.lbox.app.DesignPattern.Interpreter.InterpreterActivity;
import com.sjl.lbox.app.DesignPattern.Iterator.IteratorActivity;
import com.sjl.lbox.app.DesignPattern.Memento.MementoActivity;
import com.sjl.lbox.app.DesignPattern.Observer.ObserverActivity;
import com.sjl.lbox.app.DesignPattern.Prototype.PrototypeActivity;
import com.sjl.lbox.app.DesignPattern.Singleton.SingletonActivity;
import com.sjl.lbox.app.DesignPattern.State.StateActivity;
import com.sjl.lbox.app.DesignPattern.Strategy.StrategyActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * 设计模式
 *
 * @author SJL
 * @date 2017/1/4
 */
public class DesignPatternActivity extends BaseActivity {
    private ListView lv;
    private ArrayAdapter adapter;
    private List<Module> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_pattern);

        initView();
    }

    private void initView() {
        initData();
        lv = (ListView) findViewById(R.id.lv);
        adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, list.get(position).getAct());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        list = new ArrayList<Module>();
        list.add(new Module("单例模式（Singleton）", SingletonActivity.class));
        list.add(new Module("建造者模式（Builder）", BuilderActivity.class));
        list.add(new Module("原型模式（Prototype）", PrototypeActivity.class));
        list.add(new Module("工厂方法模式（AbstractFactory Method）", FactoryMethodActivity.class));
        list.add(new Module("抽象工厂模式（Abstract AbstractFactory）", AbstractFactoryActivity.class));
        list.add(new Module("策略模式（strategy）", StrategyActivity.class));
        list.add(new Module("状态模式（State）", StateActivity.class));
        list.add(new Module("责任链模式（Chain of Responsibility）", ChainOfResponsibilityActivity.class));
        list.add(new Module("解释器模式（Interpreter）", InterpreterActivity.class));
        list.add(new Module("命令模式（Command）", CommandActivity.class));
        list.add(new Module("观察者模式（Observer）", ObserverActivity.class));
        list.add(new Module("备忘录模式（Memento）", MementoActivity.class));
        list.add(new Module("迭代器模式（Iterator）", IteratorActivity.class));
    }
}
