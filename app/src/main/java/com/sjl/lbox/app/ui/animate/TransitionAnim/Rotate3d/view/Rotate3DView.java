package com.sjl.lbox.app.ui.animate.TransitionAnim.Rotate3D.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片3D翻转控件
 *
 * @author SJL
 * @date 2017/1/20
 */

public class Rotate3DView extends View {

    public static final int DIRECTION_X = 1;
    public static final int DIRECTION_Y = 2;
    private Context context;
    //相机
    private Camera camera;
    //矩阵
    private Matrix matrix;
    //画笔
    private Paint paint;
    //View宽高
    private int mWidth;
    private int mHeight;
    //旋转轴
    private float axisX;
    private float axisY;
    //旋转方向
    private int direction;
    //要旋转的图片
    private List<Bitmap> bitmapList;
    //第几张图片
    private int currentIndex;
    private int nextIndex;
    private Bitmap[][] bitmaps;
    //分割块数
    private int partNumber = 1;
    //旋转角度
    private float rotateDegree;
    //旋转时间
    private long duration = 3000L;


    public Rotate3DView(Context context) {
        this(context, null);
    }

    public Rotate3DView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Rotate3DView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        camera = new Camera();
        matrix = new Matrix();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapList = new ArrayList<Bitmap>();
        currentIndex = 0;
        nextIndex = 0;
        rotateDegree = 0f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        if (mWidth != 0 && mHeight != 0) {
            for (int i = 0; i < bitmapList.size(); i++) {
                bitmapList.set(i, scaleBitmap(bitmapList.get(i)));
            }
            initBitmaps();
            invalidate();
        }
    }

    /**
     * 初始化旋转图片
     */
    private void initBitmaps() {
        if (mWidth <= 0 && mHeight <= 0) {
            return;
        }
        if (bitmapList == null || bitmapList.size() == 0) {
            return;
        }

        bitmaps = new Bitmap[bitmapList.size()][partNumber];
        for (int i = 0; i < bitmapList.size(); i++) {
            for (int j = 0; j < partNumber; j++) {
                if (direction == DIRECTION_X) {
                    //横向分块
                    bitmaps[i][j] = Bitmap.createBitmap(bitmapList.get(i), 0, j * mHeight / partNumber, mWidth, (j + 1) * mHeight / partNumber);
                } else {
                    //纵向分块
                    bitmaps[i][j] = Bitmap.createBitmap(bitmapList.get(i), j * mWidth / partNumber, 0, (j + 1) * mWidth / partNumber, mHeight);
                }
            }
        }
    }

    /**
     * 初始化当前图片和下一图片下标
     */
    private void initIndex() {
        currentIndex = nextIndex;
        nextIndex = (currentIndex + 1) % bitmapList.size();
    }

    /**
     * 根据控件宽高进行缩放
     *
     * @param bitmap
     * @return
     */
    private Bitmap scaleBitmap(Bitmap bitmap) {
        Bitmap result = bitmap;
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scaleWidth = mWidth * 1.0f / width;
            float scaleHeight = mHeight * 1.0f / height;
            Matrix matrix = new Matrix();
            matrix.setScale(scaleWidth, scaleHeight);
            result = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmapList == null || bitmapList.size() == 0) {
            return;
        }
        drawWhole3D(canvas);
    }

    private void drawWhole3D(Canvas canvas) {
        Bitmap currentBitmap = bitmapList.get(currentIndex);
        Bitmap nextBitmap = bitmapList.get(nextIndex);
        canvas.save();
        if (direction == DIRECTION_X) {
            //绘制当前图片
            camera.save();
            camera.rotateX(-rotateDegree);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-mWidth / 2, 0);
            matrix.postTranslate(mWidth / 2, axisY);
            canvas.drawBitmap(currentBitmap, matrix, paint);

            //绘制旋转过来的图片
            camera.save();
            camera.rotateX(90 - rotateDegree);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-mWidth / 2, -mHeight);
            matrix.postTranslate(mWidth / 2, axisY);
            canvas.drawBitmap(nextBitmap, matrix, paint);
        } else {
            //绘制当前图片
            camera.save();
            camera.rotateY(rotateDegree);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(0, -mHeight / 2);
            matrix.postTranslate(axisX, mHeight / 2);
            canvas.drawBitmap(currentBitmap, matrix, paint);

            //绘制旋转过来的图片
            camera.save();
            camera.rotateY(rotateDegree - 90);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-mWidth, -mHeight / 2);
            matrix.postTranslate(axisX, mHeight / 2);
            canvas.drawBitmap(nextBitmap, matrix, paint);
        }
        canvas.restore();
    }

    public void setRotateDegree(float rotateDegree) {
        this.rotateDegree = rotateDegree;
        if (direction == DIRECTION_X) {
            axisY = rotateDegree * mHeight / 90;
        } else {
            axisX = rotateDegree * mWidth / 90;
        }
        invalidate();
    }

    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
        initBitmaps();
    }

    public void setDirection(int direction) {
        this.direction = direction;
        initBitmaps();
    }

    public void addBitmap(Bitmap bitmap) {
        bitmapList.add(bitmap);
        initBitmaps();
        nextIndex = 0;
        initIndex();
        invalidate();
    }

    public void removeBitmapAt(int index) {
        bitmapList.remove(index);
    }

    private ValueAnimator valueAnimator = ValueAnimator.ofFloat();
    private ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (float) valueAnimator.getAnimatedValue();
            setRotateDegree(value);
        }
    };

    public void toNext() {
        stop();
        int startRotate = 0;
        setRotateDegree(startRotate);
        valueAnimator = ValueAnimator.ofFloat(startRotate, 90);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(updateListener);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                initIndex();
                setRotateDegree(0);
            }
        });
        valueAnimator.start();
    }

    private void stop() {
        valueAnimator.cancel();
    }
}
