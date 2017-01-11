package com.sjl.lbox.app.DesignPattern.Visitor;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
/**
 * 访问者模式（Visitor）
 * 
 * @author SJL
 * @date 2017/1/9
 */
public class VisitorActivity extends BaseActivity {
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

        tvDefine.setText("封装一个作用于某种数据结构中的各元素的操作，它可以在不改变数据结构的前提下定义作用于这些元素的新操作。");
        tvAdvantage.setText("1、符合单一职责原则；\n" +
                "2、优秀的扩展性；\n" +
                "3、灵活性非常高；");
        tvUsage.setText("1、一个对象结构包含很多类对象，它们有不同接口，而你想对这些对象实施一些依赖于其具体类的操作，也就是迭代器模式已经不能胜任的情景；\n" +
                "2、需要对一个对象结构中的对象进行很多不同并且不相关的操作，而你想避免让这些操作“污染”这些对象的类；\n" +
                "（使用率不高，大部分情况不需要访问者模式，少数特定场景需要）");

        //简单使用
        Subject subject = new SubjectImp();
        Visitor visitor = new VisitorImp();
        //访问者访问目标
        subject.accept(visitor);
    }
}
