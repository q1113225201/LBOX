package com.sjl.lbox.app.animate.activityAnim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        }
    }
}
