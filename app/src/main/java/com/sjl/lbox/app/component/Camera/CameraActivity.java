package com.sjl.lbox.app.component.Camera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

public class CameraActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initView();
    }

    private void initView() {
        findViewById(R.id.btnCamera1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, Camera1Activity.class));
            }
        });
        findViewById(R.id.btnCamera2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, Camera2Activity.class));
            }
        });
    }
}
