package com.sjl.lbox.app.DesignPattern.Decorator;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 装饰模式（Decorator）
 *
 * @author SJL
 * @date 2017/1/10
 */
public class DecoratorActivity extends BaseActivity {
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

        tvDefine.setText("动态地给一个对象添加一些额外的职责。就增加功能来说，装饰模式相比生成子类更为灵活。");
        tvAdvantage.setText("1、装饰类和被装饰类可以独立发展，而不会互相耦合；\n" +
                "2、是继承关系的一个替代方案；\n" +
                "3、可以动态地扩展一个实现类的功能；");
        tvUsage.setText("1、需要扩展一个类的功能，或给一个类增加附加功能；\n" +
                "2、需要动态给一个对象增加功能，这些功能可以再动态地撤销；\n" +
                "3、需要为一批兄弟类进行改装或加装功能，首选装饰模式；");

        //简单使用
        Source source = new SourceImp();
        Source decorator = new Decorator(source);
        decorator.method();
    }
}
