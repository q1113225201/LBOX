package com.sjl.lbox.app.animate.activityAnim;

import android.app.Activity;
import android.os.Bundle;

import com.sjl.lbox.R;

/**
 * 动画跳转
 *
 * @author SJL
 * @date 2017/1/4
 */
public class AnotherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
    }
}
