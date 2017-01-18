package com.sjl.lbox.app.ui.animate.TransitionAnim;

import android.animation.Animator;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.AppUtil;

/**
 * 动画跳转
 *
 * @author SJL
 * @date 2017/1/4
 */
public class AnotherActivity extends BaseActivity {

    public static final String EXTRA_NAME = "resource";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //要使用共享元素需要在调用和被调用的Activity里声明getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //或在主题里面声明<item name="android:windowContentTransitions">true</item>
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_another);

        initView();
        initTransition();
    }

    /**
     * 初始化切换效果
     */
    private void initTransition() {
        int resource = getIntent().getIntExtra(EXTRA_NAME,0);
        if(resource==-1){
            findViewById(R.id.rlAnother).setVisibility(View.GONE);
            findViewById(R.id.llAnother).setVisibility(View.VISIBLE);
            return;
        }else{
            findViewById(R.id.rlAnother).setVisibility(View.VISIBLE);
            findViewById(R.id.llAnother).setVisibility(View.GONE);
        }
        if(resource==0){
            return;
        }
        Transition transition = TransitionInflater.from(mContext).inflateTransition(resource);
        getWindow().setExitTransition(transition);
        getWindow().setEnterTransition(transition);
    }

    private void initView() {
        findViewById(R.id.btnFloat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.activity_another).setVisibility(View.VISIBLE);
                //createCircularReveal参数：动画View，动画中心坐标x，动画中心坐标y，起始半径，结束半径
                Animator circularReveal = ViewAnimationUtils.createCircularReveal(findViewById(R.id.activity_another), AppUtil.getScreen(mContext).x/2,AppUtil.getScreen(mContext).y/2,0, (float) Math.hypot(AppUtil.getScreen(mContext).x/2,AppUtil.getScreen(mContext).y/2));
                circularReveal.setDuration(500).start();
            }
        });
    }
}
