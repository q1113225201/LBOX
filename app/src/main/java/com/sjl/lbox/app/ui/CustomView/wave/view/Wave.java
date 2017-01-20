package com.sjl.lbox.app.ui.CustomView.wave.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Wave
 *
 * @author SJL
 * @date 2017/1/19
 */

public class Wave extends View {
    public Wave(Context context) {
        this(context,null);
    }

    public Wave(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Wave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    private float mWaveWidth = 100;
    private float mWaveHeight = 60;
    Path path;
    Paint paint;
    private void init(AttributeSet attrs) {
        path = new Path();
        path.moveTo(0,0);
        for (int i=0;i<12;i+=2){
            float height = 100;
            float height1 = 100;
            switch (i%4){
                case 0:
                    height=100;
                    height1+=mWaveHeight;
                    break;
                case 1:
                    height+=mWaveHeight;
                    height1 = 100;
                    break;
                case 2:
                    height=100;
                    height1-=mWaveHeight;
                    break;
                case 3:
                    height-=mWaveHeight;
                    height1=100;
                    break;
            }
            path.quadTo(mWaveWidth*i,height,mWaveWidth*(i+1),height1);
        }
        path.lineTo(400,500);
        path.lineTo(100,400);
        path.lineTo(0,0);
        path.close();

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
    }
}
