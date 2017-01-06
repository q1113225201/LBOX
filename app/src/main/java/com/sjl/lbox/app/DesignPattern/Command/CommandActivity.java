package com.sjl.lbox.app.DesignPattern.Command;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
/**
 * 命令模式（Command）
 * 
 * @author SJL
 * @date 2017/1/6
 */
public class CommandActivity extends BaseActivity {
    private TextView tvDefine;
    private TextView tvUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("将一个请求封装为一个对象，从而使我们可用不同的请求对客户进行参数化；对请求排队或者记录请求日志，以及支持可撤销的操作。");
        tvUsage.setText("1、只要是命令的地方都可以；\n" +
                "2、系统需要将请求调用者和请求接收者解耦，使得调用者和接收者不直接交互；");

        //简单使用
        //执行者
        Receiver receiver = new ReceiverImp1();
        //命令
        Command command = new CommandImp1(receiver);
        //发布者
        Invoker invoker = new Invoker();
        //设置要发布的命令
        invoker.setCommand(command);
        //发布
        invoker.action();
    }
}
