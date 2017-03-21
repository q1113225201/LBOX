package com.sjl.lbox.app.ui.animate.PathAnim.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * PathView
 *
 * @author SJL
 * @date 2017/3/21
 */

public class PathView extends View {
    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }


    //画笔
    private Paint paint;
    //线条粗细
    private float width = 4.0f;
    //线条颜色
    private int color = Color.BLACK;
    //路径
    private Path path;

    /**
     * 初始化
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        initTools();
        path = new Path();
    }

    /**
     * 初始化工具
     */
    private void initTools() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(width);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPath(canvas);
    }

    /**
     * 绘制路径
     *
     * @param canvas
     */
    private void drawPath(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    public void setPath(Path path) {
        this.path = path;
        invalidate();
    }
}
