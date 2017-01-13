package com.sjl.lbox.app.ui.CustomView.contact.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 侧边栏导航栏
 *
 * @author SJL
 * @date 2016/12/11 16:21
 */
public class SectionIndexBar extends View {
    //索引
    private String[] indexs = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private int selectIndex = -1;

    private Paint paintNormal;

    private Paint paintSelect;
    //普通色
    private int colorNormal = Color.WHITE;
    //选中色
    private int colorSelect = Color.BLACK;
    //字体大小
    private float textSize = 40f;

    private int mWidth;

    private int itemHeight;
    //背景色
    private int colorBackground = Color.GRAY;

    private OnIndexListener onIndexListener;

    public void setOnIndexListener(OnIndexListener onIndexListener) {
        this.onIndexListener = onIndexListener;
    }

    public interface OnIndexListener {
        void onIndexSelect(String str);

        void onIndexChange(String str);
    }

    public SectionIndexBar(Context context) {
        this(context, null);
    }

    public SectionIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setClickable(true);

        paintNormal = new Paint();
        paintNormal.setColor(colorNormal);
        paintNormal.setAntiAlias(true);
        paintNormal.setTextSize(textSize);

        paintSelect = new Paint();
        paintSelect.setColor(colorSelect);
        paintNormal.setAntiAlias(true);
        paintSelect.setTextSize(textSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置背景
        setBackgroundColor(colorBackground);
        mWidth = getWidth();
        itemHeight = getHeight() / indexs.length;

        drawChars(canvas);

        invalidate();
    }

    /**
     * 绘制字母
     *
     * @param canvas
     */
    private void drawChars(Canvas canvas) {
        for (int i = 0; i < indexs.length; i++) {
            if (selectIndex == i) {
                //绘制选中字母
                canvas.drawText(indexs[i], (mWidth - paintNormal.measureText(indexs[i])) / 2, (i + 1) * itemHeight, paintSelect);
            } else {
                //绘制普通字母
                canvas.drawText(indexs[i], (mWidth - paintSelect.measureText(indexs[i])) / 2, (i + 1) * itemHeight, paintNormal);
            }
        }
    }

    /**
     * 手势控制
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int count = (int) event.getY() / itemHeight;
        count = count < 0 ? 0 : (count >= indexs.length ? indexs.length - 1 : count);
        selectIndex = count;
        if (onIndexListener != null) {
            onIndexListener.onIndexChange(indexs[selectIndex]);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (onIndexListener != null) {
                onIndexListener.onIndexSelect(indexs[selectIndex]);
            }
        }
        return super.onTouchEvent(event);
    }
}
