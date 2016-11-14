package com.sjl.lbox.app.animate;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 属性动画
 *
 * @author SJL
 * @date 2016/10/29 17:16
 */
public class PropertyAnimateActivity extends BaseActivity implements View.OnClickListener {

    private Button btnStartValueAnimator ;
    private TextView tvValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animate);

        initView();
    }

    private void initView() {
        btnStartValueAnimator = (Button) findViewById(R.id.btnStartValueAnimator);
        btnStartValueAnimator.setOnClickListener(this);
        tvValue = (TextView) findViewById(R.id.tvValue);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStartValueAnimator:
                startValueAnimator();
                break;
        }
    }

    /**
     * ValueAnimator属性动画，
     */
    private void startValueAnimator() {
        //ValueAnimator能自动实现从初始值平滑过渡到结束值的效果
        //ofArgb,ofFloat,ofObject
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,10,0);
        valueAnimator.setDuration(2000);//动画时间
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                tvValue.setText(String.format("value:%d",value));
            }
        });
        valueAnimator.start();
    }
}
