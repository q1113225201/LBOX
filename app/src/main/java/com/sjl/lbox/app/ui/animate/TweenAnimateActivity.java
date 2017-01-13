package com.sjl.lbox.app.ui.animate;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 补间动画
 *
 * @author SJL
 * @date 2016/10/29 17:04
 */
public class TweenAnimateActivity extends BaseActivity implements View.OnClickListener {

    private Button btnStart;
    private Button btnReverse;
    private ImageView ivXML;

    private Button btnAlpha;
    private Button btnRotate;
    private Button btnScale;
    private Button btnTranslate;
    private ImageView ivCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween_animate);

        initView();
    }

    private void initView() {
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        btnReverse = (Button) findViewById(R.id.btnReverse);
        btnReverse.setOnClickListener(this);

        ivXML = (ImageView) findViewById(R.id.ivXML);

        btnAlpha = (Button) findViewById(R.id.btnAlpha);
        btnAlpha.setOnClickListener(this);
        btnRotate = (Button) findViewById(R.id.btnRotate);
        btnRotate.setOnClickListener(this);
        btnScale = (Button) findViewById(R.id.btnScale);
        btnScale.setOnClickListener(this);
        btnTranslate = (Button) findViewById(R.id.btnTranslate);
        btnTranslate.setOnClickListener(this);

        ivCode = (ImageView) findViewById(R.id.ivCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.tween_animate);
                ivXML.startAnimation(animation);
                break;
            case R.id.btnReverse:
                Animation reverse = AnimationUtils.loadAnimation(mContext, R.anim.tween_reverse);
                ivXML.startAnimation(reverse);
                break;
            case R.id.btnAlpha:
                AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.3f);
                alphaAnimation.setDuration(2000);
                alphaAnimation.setFillAfter(true);
                ivCode.startAnimation(alphaAnimation);
                break;
            case R.id.btnRotate:
                //参数意义：开始角度，结束角度，X坐标参照类型，X坐标参照点，Y坐标参照类型，Y坐标参照点
                RotateAnimation rotateAnimation = new RotateAnimation(0,180,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                rotateAnimation.setDuration(2000);
                rotateAnimation.setRepeatCount(3);
                ivCode.startAnimation(rotateAnimation);
                break;
            case R.id.btnScale:
                //参数意义：X开始倍数，X结束倍数，Y开始倍数，Y结束倍数，X坐标参照类型，X坐标参照点，Y坐标参照类型，Y坐标参照点
                ScaleAnimation scaleAnimation = new ScaleAnimation(1,2,1,2,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f);
                scaleAnimation.setDuration(2000);
                scaleAnimation.setRepeatMode(Animation.INFINITE);
                scaleAnimation.setRepeatCount(Animation.INFINITE);
                ivCode.startAnimation(scaleAnimation);
                break;
            case R.id.btnTranslate:
                //参数意义：X坐标参照类型，X开始坐标参照点，X坐标参照类型，X结束坐标参照点，Y坐标参照类型，Y开始坐标参照点，Y坐标参照类型，Y结束坐标参照点
                TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,0f,Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0f,Animation.RELATIVE_TO_PARENT,0.5f);
                translateAnimation.setDuration(2000);
                //new AccelerateDecelerateInterpolator()动画开始与结束的地方速率改变较慢，中间时候加速
                //new AccelerateInterpolator()动画开始的地方速率改变较慢，然后加速
                //new CycleInterpolator(2)动画循环播放特定次数，速率改变沿正弦曲线
                //new DecelerateInterpolator()动画开始的地方速率改变较慢，然后减速
                //new LinearInterpolator()动画匀速改变
                translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                ivCode.startAnimation(translateAnimation);
                break;
        }
    }
}
