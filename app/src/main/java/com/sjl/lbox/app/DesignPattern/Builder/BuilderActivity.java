package com.sjl.lbox.app.DesignPattern.Builder;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 建造者模式（Builder）
 *
 * @author SJL
 * @date 2017/1/4
 */
public class BuilderActivity extends BaseActivity {
    private TextView tvDefine;
    private TextView tvUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("将一个复杂的对象的构造与它的表示分离，使得同样的构造过程可以创建不同的表示。");
        tvUsage.setText("1、创建某个对象需要设置很多参数，但这些参数又必须按照某个顺序设定，或是设定步骤不同会有不同结果；");

        //简单使用
        MyData myData = new MyData.MyBuilder().setId(1).setName("SJL").build();
    }
}