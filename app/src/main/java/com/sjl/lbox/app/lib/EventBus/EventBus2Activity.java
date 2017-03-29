package com.sjl.lbox.app.lib.EventBus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.lib.EventBus.bean.EventBusBean;
import com.sjl.lbox.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

public class EventBus2Activity extends BaseActivity implements View.OnClickListener {
    private TextView tvValue;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus2);

        initView();
    }

    private void initView() {
        tvValue = (TextView) findViewById(R.id.tvValue);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        //粘性事件最好及时释放，防止内存溢出
        tvValue.setText("strick:" + EventBus.getDefault().removeStickyEvent(Integer.class));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
                EventBus.getDefault().post(new EventBusBean(1, "SJL"));
                break;
        }
    }
}
