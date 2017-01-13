package com.sjl.lbox.app.ui.animate;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ToastUtil;

/**
 * 属性动画
 *
 * @author SJL
 * @date 2016/10/29 17:16
 */
public class PropertyAnimateActivity extends BaseActivity implements View.OnClickListener {

    //ValueAnimator
    private Button btnStartValueAnimator;
    private TextView tvValue;
    //ObjectAnimator
    private Button btnObjectAnimatorAlpha;
    private Button btnObjectAnimatorRotation;
    private Button btnObjectAnimatorScaleX;
    private Button btnObjectAnimatorTranslationX;
    private TextView tvObject;
    //组合动画
    private Button btnGroup;
    private TextView tvGroup;
    //动画监听器
    private Button btnAnimatorListener;
    private TextView tvAnimatorListener;

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

        btnObjectAnimatorAlpha = (Button) findViewById(R.id.btnObjectAnimatorAlpha);
        btnObjectAnimatorAlpha.setOnClickListener(this);
        btnObjectAnimatorRotation = (Button) findViewById(R.id.btnObjectAnimatorRotation);
        btnObjectAnimatorRotation.setOnClickListener(this);
        btnObjectAnimatorScaleX = (Button) findViewById(R.id.btnObjectAnimatorScaleX);
        btnObjectAnimatorScaleX.setOnClickListener(this);
        btnObjectAnimatorTranslationX = (Button) findViewById(R.id.btnObjectAnimatorTranslationX);
        btnObjectAnimatorTranslationX.setOnClickListener(this);
        tvObject = (TextView) findViewById(R.id.tvObject);

        btnGroup = (Button) findViewById(R.id.btnGroup);
        btnGroup.setOnClickListener(this);
        tvGroup = (TextView) findViewById(R.id.tvGroup);

        btnAnimatorListener = (Button) findViewById(R.id.btnAnimatorListener);
        btnAnimatorListener.setOnClickListener(this);
        tvAnimatorListener = (TextView) findViewById(R.id.tvAnimatorListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartValueAnimator:
                //ValueAnimator能自动实现从初始值平滑过渡到结束值的效果
                //ofArgb,ofFloat,ofObject
                ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 10, 0);
                valueAnimator.setDuration(2000);//动画时间
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        tvValue.setText(String.format("value:%d", value));
                    }
                });
                valueAnimator.start();
                break;
            case R.id.btnObjectAnimatorAlpha:
                //可使用任何view属性
                ObjectAnimator objectAnimatorAlpha = ObjectAnimator.ofFloat(tvObject, "alpha", 1f, 0.3f, 1f);
                objectAnimatorAlpha.setDuration(5000);
                objectAnimatorAlpha.start();
                break;
            case R.id.btnObjectAnimatorRotation:
                ObjectAnimator objectAnimatorRotation = ObjectAnimator.ofFloat(tvObject, "rotation", 0f,360f);
                objectAnimatorRotation.setDuration(5000);
                objectAnimatorRotation.start();
                break;
            case R.id.btnObjectAnimatorScaleX:
                ObjectAnimator objectAnimatorScaleX = ObjectAnimator.ofFloat(tvObject, "scaleX", 1f, 3f, 1f);
                objectAnimatorScaleX.setDuration(5000);
                objectAnimatorScaleX.start();
                break;
            case R.id.btnObjectAnimatorTranslationX:
                float translationX = tvObject.getTranslationX();
                ObjectAnimator objectAnimatorTranslationX = ObjectAnimator.ofFloat(tvObject, "translationX", translationX, translationX+500, translationX);
                objectAnimatorTranslationX.setDuration(5000);
                objectAnimatorTranslationX.start();
                break;
            case R.id.btnGroup:
                //将Animator装到AnimatorSet里进行播放
                float nowTranslation = tvGroup.getTranslationX();
                ObjectAnimator move = ObjectAnimator.ofFloat(tvGroup,"translationX",nowTranslation,nowTranslation+500);
                ObjectAnimator moveBack = ObjectAnimator.ofFloat(tvGroup,"translationX",nowTranslation+500,nowTranslation);
                ObjectAnimator rotate = ObjectAnimator.ofFloat(tvGroup,"rotation",0f,360f);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(tvGroup,"alpha",1f,0f,1f);
                ObjectAnimator scale = ObjectAnimator.ofFloat(tvGroup,"scaleX",1f,3f,1f);
                AnimatorSet animatorSet = new AnimatorSet();
                //在缩放后，边移动边变透明度边旋转，最后移动回去（with是和play一起进行，before在play前，after在play后）
                animatorSet.play(move).with(alpha).with(rotate).after(scale).before(moveBack);
                animatorSet.setDuration(10000);
                animatorSet.start();
                break;
            case R.id.btnAnimatorListener:
                float translationListenerX = tvObject.getTranslationX();
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tvAnimatorListener, "translationX", translationListenerX, translationListenerX+500, translationListenerX);
                objectAnimator.setDuration(5000);
                objectAnimator.setRepeatCount(3);
                objectAnimator.start();
                objectAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        ToastUtil.showToast(mContext,"onAnimationStart");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ToastUtil.showToast(mContext,"onAnimationEnd");
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        ToastUtil.showToast(mContext,"onAnimationCancel");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        ToastUtil.showToast(mContext,"onAnimationRepeat");
                    }
                });
                break;
        }
    }
}
