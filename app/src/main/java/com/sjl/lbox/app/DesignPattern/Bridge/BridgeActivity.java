package com.sjl.lbox.app.DesignPattern.Bridge;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 桥接模式（Bridge）
 * 
 * @author SJL
 * @date 2017/1/10
 */
public class BridgeActivity extends BaseActivity {
    private TextView tvDefine;
    private TextView tvUsage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("将抽象和实现解耦，使得两者可以独立地变化。");
        tvUsage.setText("1、不希望或不适用使用继承的场景；\n" +
                "2、接口或抽象类不稳定的场景；\n" +
                "3、重要性要求较高的场景；");

        //简单使用
        Bridge bridge = new MyBridge();
        //目标对象1
        Source source1 = new SourceImp1();
        bridge.setSource(source1);
        bridge.method();
        //目标对象2
        Source source2 = new SourceImp2();
        bridge.setSource(source2);
        bridge.method();
    }
}
