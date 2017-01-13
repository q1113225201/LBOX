package com.sjl.lbox.app.mobile.compass.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 指南针View
 *
 * @author SJL
 * @date 2016/9/20 22:05
 */
public class CompassView extends ImageView {
    private float direction;
    private Drawable compass;

    public CompassView(Context context) {
        super(context);
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        if(compass==null){
            compass = getDrawable();
            //compass.setBounds(0,0,getWidth(),getWidth());
        }
        canvas.save();
        canvas.rotate(direction,getWidth()/2,getWidth()/2);
        compass.draw(canvas);
        canvas.restore();
    }

    /**
     * 同步方向
     * @param direction
     */
    public void updateDirection(float direction){
        this.direction = direction;
        invalidate();
    }
}
