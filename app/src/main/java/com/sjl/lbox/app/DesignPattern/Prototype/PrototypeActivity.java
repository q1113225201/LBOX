package com.sjl.lbox.app.DesignPattern.Prototype;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 原型模式（Prototype）
 *
 * @author SJL
 * @date 2017/1/4
 */
public class PrototypeActivity extends BaseActivity {

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

        tvDefine.setText("用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。");
        tvAdvantage.setText("1、性能优良；\n" +
                "2、避免构造函数的约束；");
        tvUsage.setText("1、资源优化场景，类的初始化需要使用较多资源；\n" +
                "2、性能和安全要求的场景，通过new产生一个对象需要非常繁琐的数据准备或访问权限；\n" +
                "3、一个对象多个修改者的场景，一个对象需要提供给其他对象访问，而且各个调用者可能都需要修改其值时；");
    }
}
