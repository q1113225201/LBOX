package com.sjl.lbox.app.component.AccessibilityService;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 无障碍服务
 *
 * @author SJL
 * @date 2017/1/18
 */
public class AccessibilityServiceActivity extends BaseActivity implements View.OnClickListener {

    private Button btnStartService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility_service);

        initView();
    }

    private void initView() {
        btnStartService = (Button) findViewById(R.id.btnStartService);
        btnStartService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStartService:
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            break;
        }
    }
}
