package com.sjl.lbox.app.DesignPattern.Singleton;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 单例模式
 *
 * @author SJL
 * @date 2017/1/4
 */
public class SingletonActivity extends BaseActivity {

    private TextView tvDefine;
    private TextView tvUsage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleton);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("确保单例类只有一个实例，并且这个单例类提供一个函数接口让其他类获取到这个唯一的实例。");
        tvUsage.setText("1、创建某个类需要消耗大量资源；\n" +
                "2、某个类占用很多内存，创建太多会导致内存占用太多；");
    }
}
