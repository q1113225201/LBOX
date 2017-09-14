package com.sjl.lbox.app.ui.CustomView.Turntable.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.sjl.lbox.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * TurntableView
 *
 * @author SJL
 * @date 2017/9/14
 */

public class TurntableView extends View {
    private static final String TAG = "TurntableView";
    //旋转中
    private static final int ROTATING = 1;
    //旋转停止中
    private static final int STOPING = 2;
    //旋转停止
    private static final int STOPED = 3;
    //当前状态
    private int state = STOPED;

    public TurntableView(Context context) {
        this(context, null);
    }

    public TurntableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TurntableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    //转盘半径
    private float radius;
    //控件宽高
    private int width;
    private int height;
    //控件中心点
    private PointF centerPoint;
    //扇区数量
    private int count = 1;
    //转盘背景色
    private int bgColor;
    //转盘边缘空白距离
    private float edge;
    //扇区背景色
    private ArrayList<Integer> bgColorList;
    //扇区图片
    private ArrayList<Bitmap> bitmapList;
    //画笔
    private Paint paint;

    /**
     * 初始化
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        initAttrs(attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(40);
    }

    /**
     * 初始化属性
     *
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TurntableView);
        bgColor = typedArray.getColor(R.styleable.TurntableView_bgColorTV, Color.RED);
        edge = typedArray.getFloat(R.styleable.TurntableView_edgeTV, 20f);
        duration = typedArray.getInteger(R.styleable.TurntableView_durationTV, 500);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        centerPoint = new PointF(width / 2, height / 2);
        //半径=减去padding和边缘距离的长宽最小值/2
        radius = Math.min(width - getPaddingStart() - getPaddingEnd() - 2 * edge, height - getPaddingTop() - getPaddingBottom() - 2 * edge) / 2;
        //设置背景色透明
        setBackgroundColor(Color.TRANSPARENT);
        //画背景大圆
        drawBackground(canvas);
        //画扇区
        drawSector(canvas);
    }

    /**
     * 画转盘大圆
     *
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        paint.setColor(bgColor);
        //圆心是控件中心，半径是扇区半径+边缘空白区
        canvas.drawCircle(centerPoint.x, centerPoint.y, radius + edge, paint);
    }

    /**
     * 画扇区
     *
     * @param canvas
     */
    private void drawSector(Canvas canvas) {
        //每个扇区角度
        float itemAngle = 360.0f / count;
        //每个扇区弧度
        double itemRadian = itemAngle * Math.PI / 180;
        //扇区内切圆半径(内切圆半径r，扇形半径R，扇区角度radius，公式R=r+r/sin(radius/2))
        float sectorInsideRadius = (float) ((radius * Math.sin(itemRadian / 2)) / (1 + Math.sin(itemRadian / 2)));
        //扇区圆心与内切圆圆心之间的距离=扇形半径-内切圆半径
        float distanceRr = radius - sectorInsideRadius;
        //第一个扇区起始角度，最上方
        float firstAngle = -itemAngle / 2 - 90;
        //扇区所在圆矩形
        RectF rectF = new RectF(centerPoint.x - radius, centerPoint.y - radius, centerPoint.x + radius, centerPoint.y + radius);
        for (int i = 0; i < count; i++) {
            //画扇区背景
            if (bgColorList.size() > 0) {
                drawSectorBackground(canvas, rectF, firstAngle + itemAngle * i, itemAngle, bgColorList.get(i % bgColorList.size()));
            }
            //画扇区内容
            if (bitmapList.size() > 0) {
                //内切圆圆心相对于第一个扇区起始边的角度
                float inCircleAngle = firstAngle + itemAngle * i + itemAngle / 2.0f;
                //内切圆圆心位置
                PointF inCirclePoint = new PointF((float) (centerPoint.x + distanceRr * Math.cos(inCircleAngle * Math.PI / 180.0)), (float) (centerPoint.y + distanceRr * Math.sin(inCircleAngle * Math.PI / 180.0)));
                //扇区内切圆里要显示的图片
                Bitmap bitmap = bitmapList.get(i % bitmapList.size());
                drawSectorBitmap(canvas,inCircleAngle,inCirclePoint,sectorInsideRadius,bitmap);
            }
        }


    }

    /**
     * 画扇区背景
     *
     * @param canvas
     * @param rectF      扇区所在圆矩形
     * @param startAngle 起始角度
     * @param sweepAngle 偏移角度
     * @param color      画笔颜色
     */
    private void drawSectorBackground(Canvas canvas, RectF rectF, float startAngle, float sweepAngle, int color) {
        paint.setColor(color);
        canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);
    }

    /**
     *
     * @param canvas
     * @param circleAngle
     * @param circlePoint
     * @param sectorInsideRadius
     * @param bitmap
     */
    private void drawSectorBitmap(Canvas canvas, float circleAngle, PointF circlePoint, float sectorInsideRadius, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        //平移使图片中心与扇形内切圆中心重合
        matrix.postTranslate((float) (circlePoint.x - bitmap.getWidth() / 2.0), (float) (circlePoint.y - bitmap.getHeight() / 2.0));
        //图片长宽缩放至扇形内切圆的内接矩形
        float sx = (float) (Math.sqrt(2) * sectorInsideRadius) / bitmap.getWidth();
        float sy = (float) (Math.sqrt(2) * sectorInsideRadius) / bitmap.getHeight();
        float scale = Math.min(Math.min(sx, sy), 1);
        matrix.postScale(scale, scale, circlePoint.x, circlePoint.y);
        //图片旋转当前扇形中线角度
        matrix.postRotate(circleAngle + 90, circlePoint.x, circlePoint.y);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    //旋转停止角度
    private float endAngle = 360;
    //每圈所需时间
    private long duration = 500;
    //开始停止到最终停止经过的时间
    private long stopDuration;
    //开始旋转时的初速度
    private float v = 0;
    private ValueAnimator valueAnimator;

    /**
     * 开始旋转
     */
    public void startRotate() {
        if (state == STOPED) {
            //如果当前状态是停止旋转，开始旋转
            state = ROTATING;
            valueAnimator = ValueAnimator.ofFloat(0, 360);
            valueAnimator.setRepeatCount(Animation.INFINITE);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationRepeat(Animator animation) {
                    if (state == STOPING) {
                        animation.end();
                        stopRotate();
                    } else {
                        super.onAnimationRepeat(animation);
                    }
                }
            });
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (v == 0) {
                        v = (float) animation.getAnimatedValue();
                    }
                    setRotation((Float) animation.getAnimatedValue());
                }
            });
            v = 0;
            valueAnimator.setDuration(duration);
            valueAnimator.start();
        }
    }

    /**
     * 停止旋转
     */
    private void stopRotate() {
        stopDuration = 3 * duration;
        //计算开始停止到停止经过的时间
        stopDuration = (long) (2 * (360 * 2 + endAngle) / v * 1000 / 60.0f);
        valueAnimator = ValueAnimator.ofFloat(0, 360 * 2 + endAngle);
        valueAnimator.setDuration(stopDuration);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setRotation((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //动画结束后状态改为停止旋转
                setRotation(endAngle);
                state = STOPED;
            }
        });
        valueAnimator.start();
    }

    /**
     * 停止旋转
     *
     * @param endAngle 停止角度
     * @return 返回停留扇区
     */
    public int stopRotateAngle(float endAngle) {
        this.endAngle = (endAngle % 360 + 360) % 360;
        if (state == ROTATING) {
            //如正在旋转，状态变成停止中
            state = STOPING;
        }
        return (count - (int) ((endAngle + (180 / count)) / (360 / count))) % count;
    }

    /**
     * 停止旋转
     *
     * @param index        停止块
     * @param isStopRandom 是否随机角度停止（否：停在中间；是：扇区内随机角度）
     * @return
     */
    public float stopRotateSector(int index, boolean isStopRandom) {
        if (index < 0) {
            throw new RuntimeException("停止扇区数不能为负");
        }
        index = (count - index % count) % count;
        float stopRotate = (360 / count) * index;
        float random = new Random().nextFloat();
        if (isStopRandom) {
            stopRotate += random * (360 / count - 1) - 180 / count;
        }
        stopRotateAngle(stopRotate);
        return stopRotate;
    }

    /**
     * 重置
     */
    public void reset() {
        setRotation(0);
    }

    /**
     * 设置扇区数据
     *
     * @param count       扇区数量
     * @param bgColorList 扇区背景色
     * @param bitmapList  扇区图片
     */
    public void setSectorData(int count, ArrayList<Integer> bgColorList, ArrayList<Bitmap> bitmapList) {
        this.count = count;
        this.bgColorList = bgColorList == null ? new ArrayList<Integer>() : bgColorList;
        this.bitmapList = bitmapList == null ? new ArrayList<Bitmap>() : bitmapList;
        reset();
        invalidate();
    }
}
