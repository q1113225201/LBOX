package com.sjl.lbox.app.DesignPattern.Iterator;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 迭代器模式（Iterator）
 * 
 * @author SJL
 * @date 2017/1/6
 */
public class IteratorActivity extends BaseActivity {
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

        tvDefine.setText("它提供一种方法访问一个容器对象中各个元素，而又不暴露该对象的内部细节。");
        tvAdvantage.setText("");
        tvUsage.setText("1、目前已经是一个没落的模式，基本没人会单独写一个迭代器，除非是产品性质的开发（JAVA最好直接用Iterator）；");

        //简单使用
        Collection collection = new CollectionImp();
        collection.add("item1");
        collection.add("item2");
        collection.add("item3");
        //迭代
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }
}
