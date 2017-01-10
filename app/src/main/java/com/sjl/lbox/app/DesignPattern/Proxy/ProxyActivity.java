package com.sjl.lbox.app.DesignPattern.Proxy;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 代理模式（Proxy）
 *
 * @author SJL
 * @date 2017/1/9
 */
public class ProxyActivity extends BaseActivity {
    private TextView tvDefine;
    private TextView tvUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("为其他对象提供一种代理以控制对这个对象的访问。");
        tvUsage.setText("1、需要对原有方法进行修改，又不违反“对拓展开放，对修改关闭”原则；");

        //简单使用
        Subject subject = new Proxy();
        subject.method();
    }
}
