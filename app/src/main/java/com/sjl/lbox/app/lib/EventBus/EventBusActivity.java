package com.sjl.lbox.app.lib.EventBus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.lib.EventBus.bean.EventBusBean;
import com.sjl.lbox.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * EventBus使用
 * https://github.com/greenrobot/EventBus
 * compile 'org.greenrobot:eventbus:3.0.0'
 *
 * @author SJL
 * @date 2017/3/29
 */
public class EventBusActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvValue;
    private Button btnJump;
    private Button btnSendStrick;
    private Button btnDestory;

    private int num=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);

        initView();
    }

    private void initView() {
        tvValue = (TextView) findViewById(R.id.tvValue);
        btnJump = (Button) findViewById(R.id.btnJump);
        btnJump.setOnClickListener(this);
        btnSendStrick = (Button) findViewById(R.id.btnSendStrick);
        btnSendStrick.setOnClickListener(this);
        btnDestory = (Button) findViewById(R.id.btnDestory);
        btnDestory.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setValue(EventBusBean eventBusBean) {
        tvValue.setText(eventBusBean.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJump:
                startActivity(new Intent(mContext, EventBus2Activity.class));
                break;
            case R.id.btnSendStrick:
                num++;
                //发送粘性事件，给未来用户使用
                EventBus.getDefault().postSticky(num);
                break;
            case R.id.btnDestory:
                //发送普通事件
                EventBus.getDefault().post("destory");
                break;
        }
    }
}
