package com.sjl.lbox.app.ui.animate.TransitionAnim.Rotate3d;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.animate.TransitionAnim.Rotate3d.anim.Rotate3dAnim;
import com.sjl.lbox.app.ui.animate.TransitionAnim.Rotate3d.view.Rotate3DView;
import com.sjl.lbox.base.BaseActivity;

/**
 * 3D旋转动画
 *
 * @author SJL
 * @date 2017/1/18
 */
public class Rotate3dActivity extends BaseActivity implements View.OnClickListener {
    private Button btnRotateX;
    private Button btnRotateY;
    private ImageView ivRotate3D1;
    private ImageView ivRotate3D2;
    private RelativeLayout rlRotate3D;

    private Button btnRotateX2;
    private Button btnRotateY2;
    private Rotate3DView rotate3DView;

    private float centerX;
    private float centerY;
    private int axial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate3d);

        initView();
    }

    private void initView() {
        btnRotateX = (Button) findViewById(R.id.btnRotateX);
        btnRotateX.setOnClickListener(this);
        btnRotateY = (Button) findViewById(R.id.btnRotateY);
        btnRotateY.setOnClickListener(this);
        ivRotate3D1 = (ImageView) findViewById(R.id.ivRotate3D1);
        ivRotate3D2 = (ImageView) findViewById(R.id.ivRotate3D2);
        rlRotate3D = (RelativeLayout) findViewById(R.id.rlRotate3D);

        btnRotateX2 = (Button) findViewById(R.id.btnRotateX2);
        btnRotateX2.setOnClickListener(this);
        btnRotateY2 = (Button) findViewById(R.id.btnRotateY2);
        btnRotateY2.setOnClickListener(this);
        rotate3DView = (Rotate3DView) findViewById(R.id.rotate3DView);
        rotate3DView.addBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.frame1));
        rotate3DView.addBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.frame3));
        rotate3DView.addBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.frame6));
    }

    @Override
    public void onClick(View v) {
        centerX = rlRotate3D.getWidth() / 2.0f;
        centerY = rlRotate3D.getHeight() / 2.0f;
        axial = Rotate3dAnim.AXIAL_Z;
        switch (v.getId()) {
            case R.id.btnRotateX:
                axial = Rotate3dAnim.AXIAL_X;
                startRotate3DAnim();
                break;
            case R.id.btnRotateY:
                axial = Rotate3dAnim.AXIAL_Y;
                startRotate3DAnim();
                break;
            case R.id.btnRotateX2:
                rotate3DView.setDirection(Rotate3DView.DIRECTION_X);
                rotate3DView.toNext();
                break;
            case R.id.btnRotateY2:
                rotate3DView.setDirection(Rotate3DView.DIRECTION_Y);
                rotate3DView.toNext();
                break;
        }

    }

    private Rotate3dAnim rotate3dAnim = null;
    private void startRotate3DAnim() {
        rotate3dAnim = new Rotate3dAnim(360f, 270f, centerX, centerY, 0, axial, true);
        rotate3dAnim.setDuration(3000);
        rotate3dAnim.setFillAfter(true);
        rotate3dAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (ivRotate3D1.getVisibility() == View.VISIBLE) {
                    ivRotate3D1.setVisibility(View.GONE);
                    ivRotate3D2.setVisibility(View.VISIBLE);
                } else {
                    ivRotate3D1.setVisibility(View.VISIBLE);
                    ivRotate3D2.setVisibility(View.GONE);
                }
                Rotate3dAnim rotate3dAnim1 = new Rotate3dAnim(90f, 0f, centerX, centerY, 0, axial, false);
                rotate3dAnim1.setDuration(3000);
                rotate3dAnim1.setFillAfter(true);
                rlRotate3D.startAnimation(rotate3dAnim1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rlRotate3D.startAnimation(rotate3dAnim);
    }
}
