package com.sjl.lbox.app.ui.CustomView.progress.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.sjl.lbox.R;
import com.sjl.lbox.util.DensityUtil;

/**
 * 环形进度
 *
 * @author SJL
 * @date 2016/12/29
 */

public class CircleProgress extends View {
    private Context context;

    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    //偏移角度
    private float offsetAngle;
    //进度条画笔
    private Paint mPaintProgress;
    //文字画笔
    private Paint mPaintText;
    //进度条颜色
    private int progressColor;
    //进度条背景颜色
    private int progressBackgroundColor;
    //进度条宽度
    private float progressWidth;
    //进度最大值
    private float progressMax = 100f;
    //进度值
    private float progressValue = 0f;
    //字体颜色
    private int textColor;
    //字体大小
    private float textSize;
    //字体大小
    private String text = "";
    //宽度
    private int mWidth;
    //高度
    private int mHeight;

    private void init(AttributeSet attrs) {
        //初始化属性
        initAttrs(attrs);
        //初始化画笔
        initPaints();
    }

    /**
     * 初始化属性
     *
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        offsetAngle = typedArray.getFloat(R.styleable.CircleProgress_progressOffsetAngle, 90f);
        progressColor = typedArray.getColor(R.styleable.CircleProgress_progressColor, Color.BLACK);
        progressBackgroundColor = typedArray.getColor(R.styleable.CircleProgress_progressBackgroundColor, Color.GRAY);
        progressWidth = typedArray.getFloat(R.styleable.CircleProgress_progressWidth, 20.0f);
        progressMax = typedArray.getFloat(R.styleable.CircleProgress_progressMax, 100f);
        textColor = typedArray.getColor(R.styleable.CircleProgress_textColor, Color.BLACK);
        textSize = DensityUtil.sp2px(context, 18f);
        typedArray.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaints() {
        mPaintProgress = new Paint();
        mPaintProgress.setAntiAlias(true);
        mPaintProgress.setStyle(Paint.Style.STROKE);
        mPaintProgress.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaintProgress.setColor(progressColor);
        mPaintProgress.setStrokeWidth(progressWidth);
        mPaintProgress.setStrokeCap(Paint.Cap.ROUND);//设置画笔边角是圆角

        mPaintText = new Paint();
        mPaintText.setColor(textColor);//设置颜色
        mPaintText.setAntiAlias(true);//消锯齿
        mPaintText.setTextSize(textSize);//设置字体大小
        mPaintText.setTypeface(Typeface.DEFAULT_BOLD);//设置字体
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setStrokeWidth(progressWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        //画进度
        drawProgress(canvas);
    }

    /**
     * 画进度
     *
     * @param canvas
     */
    private void drawProgress(Canvas canvas) {
        /*int radius = (int) (Math.min(mWidth, mHeight) / 2 - progressWidth);
        //画进度背景
        mPaintProgress.setColor(progressBackgroundColor);
        canvas.drawCircle(mWidth / 2, mHeight / 2, radius, mPaintProgress);
        //画进度
        mPaintProgress.setColor(progressColor);
        RectF rectF = new RectF(mWidth / 2 - radius, mHeight / 2 - radius, mWidth / 2 + radius, mHeight / 2 + radius);
        canvas.drawArc(rectF, 0f, 360 * progressValue / progressMax, false, mPaintProgress);
*/
        float startAngle = 0 + offsetAngle;
        float sweepAngle = 360 * progressValue / progressMax;
        mPaintProgress.setStrokeWidth(progressWidth);
        //绘制进度背景
        mPaintProgress.setColor(progressBackgroundColor);
        canvas.drawArc(progressWidth, progressWidth, mWidth - progressWidth, mHeight - progressWidth, 0f, 360f, false, mPaintProgress);
        //绘制进度
        mPaintProgress.setColor(progressColor);
        canvas.drawArc(progressWidth, progressWidth, mWidth - progressWidth, mHeight - progressWidth, startAngle, sweepAngle, false, mPaintProgress);
        //画字体
        Rect rect = new Rect();
        mPaintText.getTextBounds(text, 0, text.length(), rect);
        //居中绘制进度，文字基线高度位置=高度中心点-文字高度/2
        //descent()基线下方距离
        //ascent()基线上方距离
        canvas.drawText(text, mWidth / 2, mHeight / 2 - (mPaintText.descent() + mPaintText.ascent()) / 2, mPaintText);

    }

    /**
     * 设置当前进度
     *
     * @param progressValue
     */
    public void setProgressValue(float progressValue) {
        this.progressValue = progressValue;
        invalidate();
    }

    /**
     * 设置文字
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    public void setProgressMax(float progressMax) {
        this.progressMax = progressMax;
        invalidate();
    }
}
