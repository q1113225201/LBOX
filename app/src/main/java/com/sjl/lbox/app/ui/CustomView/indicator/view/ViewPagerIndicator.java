package com.sjl.lbox.app.ui.CustomView.indicator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.sjl.lbox.R;

/**
 * ViewPagerIndicator
 *
 * @author SJL
 * @date 2017/3/19 14:55
 */

public class ViewPagerIndicator extends LinearLayout {
    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    //指示器半径
    private int radius = 10;
    //画笔
    private Paint paint;
    //普通指示器颜色
    private int colorNormal = Color.GRAY;
    //选中指示器颜色
    private int colorSelected = Color.RED;
    //间距
    private int padding = 20;
    //个数
    private int num = 3;
    //选中的指示器
    private SelectView selectView;
    //当前选中页
    private int pager;
    //偏移
    private float offset;

    /**
     * 初始化
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        //初始化属性
        initAttrs(attrs);
        //初始化工具
        initTools();
        setWillNotDraw(false);
    }

    /**
     * 初始化属性
     *
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        radius = typedArray.getInt(R.styleable.ViewPagerIndicator_radius, 5);
        colorNormal = typedArray.getColor(R.styleable.ViewPagerIndicator_colorNormal, Color.GRAY);
        colorSelected = typedArray.getColor(R.styleable.ViewPagerIndicator_colorSelected, Color.RED);
        padding = typedArray.getInt(R.styleable.ViewPagerIndicator_padding, 10);
        num = typedArray.getInt(R.styleable.ViewPagerIndicator_num, 3);
        typedArray.recycle();
    }

    /**
     * 初始化工具
     */
    private void initTools() {
        //初始化画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(colorNormal);
        //初始化选中点
        selectView = new SelectView(getContext());
        addView(selectView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(padding + (padding + 2 * radius) * num, (padding + radius) * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        drawBackground(canvas);
    }

    /**
     * 绘制背景指示器
     *
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        paint.setColor(colorNormal);
        for (int i = 0; i < num; i++) {
            canvas.drawCircle(
                    padding + radius + i * (2 * radius + padding),
                    padding + radius,
                    radius,
                    paint
            );
        }
    }

    public void setNum(int num) {
        this.num = num;
    }

    /**
     * 设置页数和偏移
     *
     * @param pager
     * @param offset
     */
    public void setPagerAndOffset(int pager, float offset) {
        this.pager = pager;
        this.offset = offset;
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        selectView.layout(
                (int) (padding + (padding + 2 * radius) * (pager + offset)),
                padding,
                (int) (padding + (padding + 2 * radius) * (pager + 1 + offset)),
                padding + radius * 2
        );
    }

    /**
     * 选中的指示器
     */
    class SelectView extends View {
        private Paint mPaint;

        public SelectView(Context context) {
            super(context);

            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(colorSelected);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(radius * 2, radius * 2);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            paint.setColor(colorSelected);
            canvas.drawCircle(radius, radius, radius, mPaint);
        }
    }
}
