package com.sjl.lbox.app.ui.CustomView.magnifier.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义放大镜
 *
 * @author SJL
 * @date 2017/1/23
 */

public class Magnifier extends View {

    //半径
    private int radius;
    //放大因素
    private int factor;

    private Paint paint;

    private Bitmap bitmap;

    private Matrix matrix;

    private PointF pointF;
    private Path path;

    public Magnifier(Context context) {
        this(context, null);
    }

    public Magnifier(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Magnifier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initAttrs(attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        matrix = new Matrix();
        matrix.setScale(factor, factor);
        path = new Path();
        path.addCircle(radius,radius,radius, Path.Direction.CW);
    }

    private void initAttrs(AttributeSet attrs) {
        radius = 100;
        factor = 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawMagnifier(canvas);
    }

    /**
     * 绘制放大镜
     *
     * @param canvas
     */
    private void drawMagnifier(Canvas canvas) {
        if (bitmap != null) {
            //画底图
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }
        if(pointF!=null){
            //剪切
            canvas.translate(pointF.x-radius,pointF.y-radius);
            canvas.clipPath(path);

            //画放大后的图
            canvas.translate(radius-pointF.x*factor,radius-pointF.y*factor);
            canvas.drawBitmap(bitmap,matrix,paint);
        }
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                pointF = new PointF(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                pointF = null;
                break;
        }
        invalidate();
        return true;
    }
}
