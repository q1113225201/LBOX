package com.sjl.lbox.app.DesignPattern.ChainOfResponsibility;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
/**
 * 责任链模式（Chain of Responsibility）
 * 
 * @author SJL
 * @date 2017/1/5
 */
public class ChainOfResponsibilityActivity extends BaseActivity {
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

        tvDefine.setText("使多个对象都有机会处理请求，从而避免请求的发送者和接受者直接的耦合关系，将这些对象连成一条链，并沿这条链传递该请求，直到有对象处理它为止。");
        tvAdvantage.setText("1、解耦提高灵活性，责任链模式可以实现，在隐瞒客户端的情况下，对系统进行动态的调整；");
        tvUsage.setText("");

        //简单使用
        MyHandlerImp myHandlerImp1 = new MyHandlerImp("myHandlerImp1");
        MyHandlerImp myHandlerImp2 = new MyHandlerImp("myHandlerImp2");
        MyHandlerImp myHandlerImp3 = new MyHandlerImp("myHandlerImp3");

        myHandlerImp1.setMyHandler(myHandlerImp2);
        myHandlerImp2.setMyHandler(myHandlerImp3);

        myHandlerImp1.operator();
    }
}
