package com.sjl.lbox.app.ui.animate.activityAnim;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * Activity切换动画
 *
 * @author SJL
 * @date 2017/1/3 22:06
 */
public class ActivityAnimActivity extends BaseActivity implements View.OnClickListener {

    private Button btnFade1;
    private Button btnSlide1;
    private Button btnFade2;
    private Button btnSlide2;
    private Button btnExplode2;
    private Button btnSharedElement2;
    private ImageView ivSharedElement1;
    private ImageView ivSharedElement2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        initView();
    }

    private void initView() {
        btnSlide1 = (Button) findViewById(R.id.btnSlide1);
        btnSlide1.setOnClickListener(this);
        btnFade1 = (Button) findViewById(R.id.btnFade1);
        btnFade1.setOnClickListener(this);
        btnSlide2 = (Button) findViewById(R.id.btnSlide2);
        btnSlide2.setOnClickListener(this);
        btnFade2 = (Button) findViewById(R.id.btnFade2);
        btnFade2.setOnClickListener(this);
        btnExplode2 = (Button) findViewById(R.id.btnExplode2);
        btnExplode2.setOnClickListener(this);
        btnSharedElement2 = (Button) findViewById(R.id.btnSharedElement2);
        btnSharedElement2.setOnClickListener(this);
        ivSharedElement1 = (ImageView) findViewById(R.id.ivSharedElement1);
        ivSharedElement2 = (ImageView) findViewById(R.id.ivSharedElement2);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        ActivityOptionsCompat activityOptionsCompat;
        switch (v.getId()) {
            case R.id.btnSlide1:
                startActivity(new Intent(mContext, AnotherActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btnFade1:
                startActivity(new Intent(mContext, AnotherActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.btnSlide2:
                intent.setClass(mContext, AnotherActivity.class);
                intent.putExtra(AnotherActivity.EXTRA_NAME, R.transition.slide_left);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.btnFade2:
                intent.setClass(mContext, AnotherActivity.class);
                intent.putExtra(AnotherActivity.EXTRA_NAME, R.transition.fade);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.btnExplode2:
                intent.setClass(mContext, AnotherActivity.class);
                intent.putExtra(AnotherActivity.EXTRA_NAME, R.transition.explode);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.btnSharedElement2:
                intent.setClass(mContext, AnotherActivity.class);
                intent.putExtra(AnotherActivity.EXTRA_NAME, -1);
                Pair pair1 = new Pair(ivSharedElement1, ViewCompat.getTransitionName(ivSharedElement1));
                Pair pair2 = new Pair(ivSharedElement2, ViewCompat.getTransitionName(ivSharedElement2));
                activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1, pair2);
                startActivity(intent, activityOptionsCompat.toBundle());
                break;
        }
    }
}
