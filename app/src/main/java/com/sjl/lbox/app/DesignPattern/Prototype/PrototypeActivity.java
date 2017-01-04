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
    private TextView tvUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prototype);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("简单的说就是将一个对象进行拷贝。\n" +
                "浅复制：将一个对象复制后，基本数据类型重新创建，引用类型的指向还是原对象所指的。\n" +
                "深复制：将一个对象复制后，基本数据和引用类型都重新创建。简单说就是深复制是完全复制，浅复制不彻底。");
        tvUsage.setText("1、可在类属性特别多，但又要经常对类进行拷贝的的时候使用；");
    }
}
