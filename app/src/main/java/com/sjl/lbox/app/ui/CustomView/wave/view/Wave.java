package com.sjl.lbox.app.ui.CustomView.wave.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Wave
 *
 * @author SJL
 * @date 2017/1/19
 */

public class Wave extends View {
    public Wave(Context context) {
        this(context, null);
    }

    public Wave(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Wave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private List<PointF> pathList;
    private Path path = new Path();
    private Paint paint;
    private int width;
    private int height;
    private int waveHeight = 200;
    private int waveWidth = 500;
    private int moveWidth = 0;
    private int speedX = 10;
    private int time = 10;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
            handler.postDelayed(runnable, time);
        }
    };

    private void init(AttributeSet attrs) {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        pathList = new ArrayList<>();
        int length = width / waveWidth * 2 + 4;
        for (int i = 0; i < length + 4; i++) {
            float y = waveHeight;
            switch (Math.abs(i % 4)) {
                case 0:
                case 2:
                    y = waveHeight;
                    break;
                case 1:
                    y = 0;
                    break;
                case 3:
                    y = waveHeight * 2;
                    break;
            }
            pathList.add(new PointF(i * waveWidth / 2 - 2 * waveWidth, y));
        }
    }

    PointF pointF;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        moveWidth += speedX;
        if (moveWidth >= waveWidth * 2) {
            moveWidth = 0;
        }
        path.reset();
        pointF = pathList.get(0);
        path.moveTo(pointF.x + moveWidth, pointF.y);
        for (int i = 1; i < pathList.size() - 1; i += 2) {
            path.quadTo(pathList.get(i).x + moveWidth, pathList.get(i).y, pathList.get(i + 1).x + moveWidth, pathList.get(i + 1).y);
        }
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.close();
        if (path != null) {
            canvas.drawPath(path, paint);
        }
    }

    public void start() {
        handler.postDelayed(runnable, time);
    }
}
