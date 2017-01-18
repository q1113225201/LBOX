package com.sjl.lbox.app.ui.animate.SceneAnim.snow.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.sjl.lbox.app.ui.animate.SceneAnim.snow.entity.SnowEntity;

/**
 * 下雪场景
 *
 * @author SJL
 * @date 2017/1/17
 */

public class SnowView extends View {
    private static final int DELAY = 5;
    private static final int DEFINE_MAX_COUNT = 150;
    //最大雪花数
    private int maxCount;
    //所有雪花
    private SnowEntity[] snowEntities;
    //画笔
    private Paint paint;

    public SnowView(Context context) {
        this(context,null);
    }

    public SnowView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SnowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        maxCount = DEFINE_MAX_COUNT;
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initSnows(w,h);
    }

    private void initSnows(int width, int height) {
        snowEntities = new SnowEntity[maxCount];
        for (int i =0;i<maxCount;i++){
            snowEntities[i] = SnowEntity.create(new PointF(width,height),paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackground(null);
        for (SnowEntity snowEntity:snowEntities){
            snowEntity.draw(canvas);
        }
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        },DELAY);
    }
}