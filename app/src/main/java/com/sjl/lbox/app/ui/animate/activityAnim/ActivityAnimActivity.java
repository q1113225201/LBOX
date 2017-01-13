package com.sjl.lbox.app.ui.animate.activityAnim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ScrollView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * Activity切换动画
 *
 * @author SJL
 * @date 2017/1/3 22:06
 */
public class ActivityAnimActivity extends BaseActivity implements View.OnClickListener {

    private Button btnFade;
    private Button btnSlide;
    private Button btnSlideRight;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        initView();
    }

    private void initView() {
        btnFade = (Button) findViewById(R.id.btnFade);
        btnFade.setOnClickListener(this);
        btnSlide = (Button) findViewById(R.id.btnSlide);
        btnSlide.setOnClickListener(this);
        btnSlideRight = (Button) findViewById(R.id.btnSlideRight);
        btnSlideRight.setOnClickListener(this);
        scrollView = (ScrollView) findViewById(R.id.activity_anim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFade:
                startActivity(new Intent(mContext, AnotherActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.btnSlide:
                startActivity(new Intent(mContext, AnotherActivity.class));
                overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                break;
            case R.id.btnSlideRight:
                Animation rightOutAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
                scrollView.startAnimation(rightOutAnimation);
                startActivity(new Intent(mContext, AnotherActivity.class));
                break;
        }
    }
}
