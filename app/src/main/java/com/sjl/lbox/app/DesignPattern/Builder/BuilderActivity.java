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

        tvDefine.setText("将一个复杂的对象的构造与它的表示分离，使得同样的构造过程可以创建不同的表示。");
        tvAdvantage.setText("1、封装性，是客户端不必知道产品内部组成的细节；\n" +
                "2、独立性好，容易扩展，同时可对建造过程进行细化；");
        tvUsage.setText("1、相同方法或相同属性，不同顺序会产生不同结果；");

        //简单使用
        MyData myData = new MyData.MyBuilder().setId(1).setName("SJL").build();
    }
}
