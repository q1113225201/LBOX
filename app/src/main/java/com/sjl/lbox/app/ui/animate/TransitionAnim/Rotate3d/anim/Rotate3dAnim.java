package com.sjl.lbox.app.ui.animate.TransitionAnim.Rotate3d.anim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 3D旋转动画
 *
 * @author SJL
 * @date 2017/1/18
 */

public class Rotate3dAnim extends Animation {
    public static final int AXIAL_X= 1;
    public static final int AXIAL_Y= 2;
    public static final int AXIAL_Z= 3;
    private float mFromDegrees;
    private float mToDegrees;
    private float mCenterX;
    private float mCenterY;
    private float mDepthZ;
    private int mAxial;
    private Boolean isReverse;
    private Camera mCamera;

    public Rotate3dAnim(float mFromDegrees, float mToDegrees, float mCenterX, float mCenterY, float mDepthZ, int mAxial, Boolean isReverse) {
        this.mFromDegrees = mFromDegrees;
        this.mToDegrees = mToDegrees;
        this.mCenterX = mCenterX;
        this.mCenterY = mCenterY;
        this.mDepthZ = mDepthZ;
        this.mAxial = mAxial;
        this.isReverse = isReverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
//        super.applyTransformation(interpolatedTime, t);
        float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + (mToDegrees - fromDegrees) * interpolatedTime;
        float centerX = mCenterX;
        float centerY = mCenterY;
        Camera camera = mCamera;
        Matrix matrix = t.getMatrix();
        camera.save();
        if(isReverse){
            camera.translate(0f,0f,mDepthZ*interpolatedTime);
        }else{
            camera.translate(0f,0f,mDepthZ*(1-interpolatedTime));
        }

        if(mAxial == AXIAL_X){
            camera.rotateX(degrees);
        }else if(mAxial == AXIAL_Y){
            camera.rotateY(degrees);
        }else{
            camera.rotateZ(degrees);
        }

        camera.getMatrix(matrix);

        camera.restore();

        matrix.preTranslate(-centerX,-centerY);

        matrix.postTranslate(centerX,centerY);
    }
}
