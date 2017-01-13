package com.sjl.lbox.app.ui.animate;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 逐帧动画
 *
 * @author SJL
 * @date 2016/10/29 16:38
 */
public class FrameAnimateActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivFrameXML;
    private ImageView ivFrameDynamic;
    private Button btnStartXML;
    private Button btnStopXML;
    private Button btnStartDynamic;
    private Button btnStopDynamic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animate);

        initView();
    }

    private void initView() {
        ivFrameXML = (ImageView) findViewById(R.id.ivFrameXML);
        ivFrameDynamic = (ImageView) findViewById(R.id.ivFrameDynamic);
        btnStartXML = (Button) findViewById(R.id.btnStartXML);
        btnStartXML.setOnClickListener(this);
        btnStopXML = (Button) findViewById(R.id.btnStopXML);
        btnStopXML.setOnClickListener(this);
        btnStartDynamic = (Button) findViewById(R.id.btnStartDynamic);
        btnStartDynamic.setOnClickListener(this);
        btnStopDynamic = (Button) findViewById(R.id.btnStopDynamic);
        btnStopDynamic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStartXML:
                startAnimateXML();
                break;
            case R.id.btnStopXML:
                stopAnimateXML();
                break;
            case R.id.btnStartDynamic:
                startAnimateDynamic();
                break;
            case R.id.btnStopDynamic:
                stopAnimateDynamic();
                break;
        }
    }

    //XML配置动画
    private AnimationDrawable animationDrawableXML = null;

    private void startAnimateXML() {
        if (animationDrawableXML == null) {
            animationDrawableXML = (AnimationDrawable) ivFrameXML.getBackground();
        }
        if (!animationDrawableXML.isRunning()) {
            animationDrawableXML.start();
        }
    }

    private void stopAnimateXML() {
        if (animationDrawableXML != null) {
            animationDrawableXML.stop();
        }
    }

    //动态加载动画
    private AnimationDrawable animationDrawableDynamic = null;

    private void startAnimateDynamic() {
        initAnimateDrawable();
        if (!animationDrawableDynamic.isRunning()) {
            animationDrawableDynamic.start();
        }
    }

    private void initAnimateDrawable() {
        if (animationDrawableDynamic == null) {
            animationDrawableDynamic = new AnimationDrawable();
            animationDrawableDynamic.addFrame(getDrawable(R.drawable.frame1), 500);
            animationDrawableDynamic.addFrame(getDrawable(R.drawable.frame2), 500);
            animationDrawableDynamic.addFrame(getDrawable(R.drawable.frame3), 500);
            animationDrawableDynamic.addFrame(getDrawable(R.drawable.frame4), 500);
            animationDrawableDynamic.addFrame(getDrawable(R.drawable.frame5), 500);
            animationDrawableDynamic.addFrame(getDrawable(R.drawable.frame6), 500);
            //将AnimateDrawable设置成ImageView的背景
            ivFrameDynamic.setBackground(animationDrawableDynamic);
        }
    }

    private void stopAnimateDynamic() {
        if (animationDrawableDynamic != null) {
            animationDrawableDynamic.stop();
        }
    }
}
