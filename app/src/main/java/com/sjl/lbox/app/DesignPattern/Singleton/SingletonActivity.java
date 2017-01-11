package com.sjl.lbox.app.DesignPattern.Singleton;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 单例模式（Singleton）
 *
 * @author SJL
 * @date 2017/1/4
 */
public class SingletonActivity extends BaseActivity {

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

        tvDefine.setText("确保某一个类只有一个实例，而且自行实例化并向整个系统提供这个实例。");
        tvAdvantage.setText("1、某些类创建比较频繁，对于一些大型的对象，这是一笔很大的系统开销；\n" +
                "2、省去new操作符，降低了系统内存的使用频率，减轻GC压力；\n" +
                "3、有些类创建多个，系统可能混乱。如交易所只有使用单例才能保证核心交易服务器独立控制整个交易流程；");
        tvUsage.setText("1、创建某个类需要消耗大量资源；\n" +
                "2、某个类占用很多内存，创建太多会导致内存占用太多；");
    }
}
