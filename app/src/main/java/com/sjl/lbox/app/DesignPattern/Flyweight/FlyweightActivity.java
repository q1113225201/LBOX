package com.sjl.lbox.app.DesignPattern.Flyweight;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 享元模式（Flyweight）
 *
 * @author SJL
 * @date 2017/1/10
 */
public class FlyweightActivity extends BaseActivity {
    private TextView tvDefine;
    private TextView tvUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flyweight);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("使用共享对象可有效地支持大量的细粒度的对象。");
        tvUsage.setText("1、系统中存在大量相似对象；\n" +
                "2、细粒度的对象都具备较接近的外部状态，而且内部状态与环境无关；\n" +
                "3、需要缓冲池的场景；");

        //简单使用
        Flyweight flyweight = FlyweightFactory.getFlyweight("data1");
        flyweight.operate();
        flyweight = FlyweightFactory.getFlyweight("data2");
        flyweight.operate();
        flyweight = FlyweightFactory.getFlyweight("data1");
        flyweight.operate();
    }
}
