package com.sjl.lbox.app.DesignPattern;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.DesignPattern.AbstractFactory.AbstractFactoryActivity;
import com.sjl.lbox.app.DesignPattern.Adapter.AdapterActivity;
import com.sjl.lbox.app.DesignPattern.Bridge.BridgeActivity;
import com.sjl.lbox.app.DesignPattern.Builder.BuilderActivity;
import com.sjl.lbox.app.DesignPattern.ChainOfResponsibility.ChainOfResponsibilityActivity;
import com.sjl.lbox.app.DesignPattern.Command.CommandActivity;
import com.sjl.lbox.app.DesignPattern.Composite.CompositeActivity;
import com.sjl.lbox.app.DesignPattern.Decorator.DecoratorActivity;
import com.sjl.lbox.app.DesignPattern.Facade.FacadeActivity;
import com.sjl.lbox.app.DesignPattern.FactoryMethod.FactoryMethodActivity;
import com.sjl.lbox.app.DesignPattern.Flyweight.FlyweightActivity;
import com.sjl.lbox.app.DesignPattern.Interpreter.InterpreterActivity;
import com.sjl.lbox.app.DesignPattern.Iterator.IteratorActivity;
import com.sjl.lbox.app.DesignPattern.Mediator.MediatorActivity;
import com.sjl.lbox.app.DesignPattern.Memento.MementoActivity;
import com.sjl.lbox.app.DesignPattern.Observer.ObserverActivity;
import com.sjl.lbox.app.DesignPattern.Prototype.PrototypeActivity;
import com.sjl.lbox.app.DesignPattern.Proxy.ProxyActivity;
import com.sjl.lbox.app.DesignPattern.Singleton.SingletonActivity;
import com.sjl.lbox.app.DesignPattern.State.StateActivity;
import com.sjl.lbox.app.DesignPattern.Strategy.StrategyActivity;
import com.sjl.lbox.app.DesignPattern.TemplateMethod.TemplateMethodActivity;
import com.sjl.lbox.app.DesignPattern.Visitor.VisitorActivity;
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
        list.add(new Module("设计模式的六大原则", DesignPatternPrincipleActivity.class));
        list.add(new Module("单例模式（Singleton）", SingletonActivity.class));
        list.add(new Module("工厂方法模式（Factory Method）", FactoryMethodActivity.class));
        list.add(new Module("抽象工厂模式（Abstract Factory）", AbstractFactoryActivity.class));
        list.add(new Module("模板方法模式（Template Method）", TemplateMethodActivity.class));
        list.add(new Module("建造者模式（Builder）", BuilderActivity.class));
        list.add(new Module("代理模式（Proxy）", ProxyActivity.class));
        list.add(new Module("中介者模式（Mediator）", MediatorActivity.class));
        list.add(new Module("命令模式（Command）", CommandActivity.class));
        list.add(new Module("责任链模式（Chain of Responsibility）", ChainOfResponsibilityActivity.class));
        list.add(new Module("装饰模式（Decorator）", DecoratorActivity.class));
        list.add(new Module("策略模式（strategy）", StrategyActivity.class));
        list.add(new Module("适配器模式（Adapter）", AdapterActivity.class));
        list.add(new Module("迭代器模式（Iterator）", IteratorActivity.class));
        list.add(new Module("组合模式（Composite）", CompositeActivity.class));
        list.add(new Module("观察者模式（Observer）", ObserverActivity.class));
        list.add(new Module("外观模式（Facade）", FacadeActivity.class));
        list.add(new Module("备忘录模式（Memento）", MementoActivity.class));
        list.add(new Module("访问者模式（Visitor）", VisitorActivity.class));
        list.add(new Module("状态模式（State）", StateActivity.class));
        list.add(new Module("解释器模式（Interpreter）", InterpreterActivity.class));
        list.add(new Module("享元模式（Flyweight）", FlyweightActivity.class));
        list.add(new Module("桥接模式（Bridge）", BridgeActivity.class));
        list.add(new Module("原型模式（Prototype）", PrototypeActivity.class));
    }
}
