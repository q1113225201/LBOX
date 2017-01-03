package com.sjl.lbox.app.progress.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.sjl.lbox.R;
import com.sjl.lbox.util.LogUtil;

/**
 * 节点进度
 *
 * @author SJL
 * @date 2017/1/3
 */

public class NodeProgress extends View {
    private static final String TAG = "NodeProgress";
    //弧形
    public static final int ARC = 0;
    //线形
    public static final int LINE = 1;

    private Context context;

    public NodeProgress(Context context) {
        this(context, null);
    }

    public NodeProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NodeProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        //初始化属性
        initAttrs(attrs);
        //初始化画笔
        initPaints();
    }

    //偏移角度
    private float offsetAngle;
    //进度颜色
    private int progressColor;
    //进度背景颜色
    private int progressBackgroundColor;
    //进度条宽度
    private float progressWidth;
    //最大进度
    private float progressMax;
    //当前进度
    private float currentProgress;
    //进度条类型
    private int progressStyle;
    //节点个数
    private int nodeCount;
    //节点宽度
    private float nodeWidth;

    /**
     * 初始化属性
     *
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NodeProgress);
        offsetAngle = typedArray.getFloat(R.styleable.NodeProgress_nodeProgressOffsetAngle, 90f);
        progressColor = typedArray.getColor(R.styleable.NodeProgress_nodeProgressColor, Color.BLACK);
        progressBackgroundColor = typedArray.getColor(R.styleable.NodeProgress_nodeProgressBackgroundColor, Color.GRAY);
        progressWidth = typedArray.getFloat(R.styleable.NodeProgress_nodeProgressWidth, 20.0f);
        progressMax = typedArray.getFloat(R.styleable.NodeProgress_nodeProgressMax, 100f);
        progressStyle = typedArray.getInt(R.styleable.NodeProgress_nodeProgressStyle, 0);
        nodeCount = typedArray.getInt(R.styleable.NodeProgress_nodeCount, 0);
        nodeWidth = typedArray.getFloat(R.styleable.NodeProgress_nodeWidth, progressWidth);
        currentProgress = 0f * 360 / progressMax;
        typedArray.recycle();
    }

    //进度画笔
    private Paint mPaint;
    //进度背景画笔
//    private Paint progressBackgroundPaint;

    /**
     * 初始化画笔
     */
    private void initPaints() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//消锯齿
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setStrokeWidth(progressWidth);//画笔宽度
        mPaint.setColor(progressColor);//画笔颜色
        mPaint.setStyle(progressStyle == LINE ? Paint.Style.FILL : Paint.Style.STROKE);
    }

    //宽度
    private int mWidth;
    //高度
    private int mHeight;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        if (progressStyle == ARC) {
            drawArcProgress(canvas);
        } else if (progressStyle == LINE) {
            drawLineProgress(canvas);
        } else {
            drawArcProgress(canvas);
        }
    }

    /**
     * 绘制弧形进度
     *
     * @param canvas
     */
    private void drawArcProgress(Canvas canvas) {
        int radius = Math.min(mWidth, mHeight) / 2;
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(progressWidth);
        //绘制进度背景
        mPaint.setColor(progressBackgroundColor);
        canvas.drawArc(nodeWidth, nodeWidth, mWidth - nodeWidth, mHeight - nodeWidth, 0f, 360f, false, mPaint);
        //绘制进度
        mPaint.setColor(progressColor);
        canvas.drawArc(nodeWidth, nodeWidth, mWidth - nodeWidth, mHeight - nodeWidth, 0f + offsetAngle, currentProgress, false, mPaint);
        //绘制节点
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(nodeWidth);
        for (int i = 0; i <= nodeCount; i++) {
            float angle = 360 * i / (nodeCount + 1) + offsetAngle;
            if (currentProgress + offsetAngle <= angle) {
                mPaint.setColor(progressBackgroundColor);
            } else {
                mPaint.setColor(progressColor);
            }
            //cos和sin里面传的都是弧度
            float cx = (float) (mWidth / 2 + (radius - nodeWidth) * Math.cos(Math.PI*angle/180));
            float cy = (float) (mHeight / 2 + (radius - nodeWidth) * Math.sin(Math.PI*angle/180));
            canvas.drawCircle(cx, cy, nodeWidth, mPaint);
        }
    }

    /**
     * 绘制线形进度
     *
     * @param canvas
     */
    private void drawLineProgress(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(progressWidth);
        //绘制背景
        mPaint.setColor(progressBackgroundColor);
        canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, mPaint);
        //绘制进度
        mPaint.setColor(progressColor);
        canvas.drawLine(0, mHeight / 2, currentProgress / progressMax * mWidth, mHeight / 2, mPaint);
        //绘制节点
        mPaint.setStrokeWidth(nodeWidth);
        for (int i = 0; i < nodeCount; i++) {
            if (currentProgress <= progressMax * (i + 1) / (nodeCount + 1)) {
                mPaint.setColor(progressBackgroundColor);
            } else {
                mPaint.setColor(progressColor);
            }
            canvas.drawCircle(mWidth / (nodeCount + 1) * (i + 1), mHeight / 2, nodeWidth, mPaint);
        }
    }

    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress * 360 / progressMax;
        invalidate();
    }

    public void setProgressMax(float progressMax) {
        this.progressMax = progressMax;
        invalidate();
    }

    public void setProgressStyle(int progressStyle) {
        this.progressStyle = progressStyle;
        mPaint.setStyle(progressStyle == LINE ? Paint.Style.FILL : Paint.Style.STROKE);
        invalidate();
    }
}
