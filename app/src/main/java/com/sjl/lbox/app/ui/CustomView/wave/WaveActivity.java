package com.sjl.lbox.app.ui.CustomView.wave;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.wave.view.Wave;
import com.sjl.lbox.base.BaseActivity;

/**
 * 波浪演示
 *
 * @author SJL
 * @date 2017/1/19
 */
public class WaveActivity extends BaseActivity {
    private Button btnDraw;
    private Wave wave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);

        initView();
    }

    private void initView() {
        btnDraw = (Button) findViewById(R.id.btnDraw);
        wave = (Wave) findViewById(R.id.wave);
        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wave.start();
            }
        });
    }
}
