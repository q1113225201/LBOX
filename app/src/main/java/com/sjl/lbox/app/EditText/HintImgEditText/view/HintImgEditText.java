package com.sjl.lbox.app.EditText.HintImgEditText.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

import com.sjl.lbox.R;

/**
 * HintImgEditText
 *
 * @author SJL
 * @date 2017/1/12
 */

public class HintImgEditText extends EditText {
    private Context context;

    public HintImgEditText(Context context) {
        this(context, null);
    }

    public HintImgEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HintImgEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        //设置获取焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        //移除自带光标
        setCursorVisible(false);
        //初始化属性
        initAttrs(attrs);
        //初始化工具
        initTools();
    }

    private Paint mPaint;

    /**
     * 初始化工具
     */
    private void initTools() {
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(4f);
    }

    private int hintImg;
    private boolean showHintImg = true;

    /**
     * 初始化属性
     *
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HintImgEditText);
        hintImg = typedArray.getResourceId(R.styleable.HintImgEditText_hintImg, 0);
        typedArray.recycle();
    }

    private int mWidth;
    private int mHeight;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        mHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        drawHintImg(canvas);
    }

    private void drawHintImg(Canvas canvas) {
        if (showHintImg && hintImg != 0) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), hintImg, options);
            int outWidth = options.outWidth;
            int outHeight = options.outHeight;
            int realWidth = 0;
            int realHeight = 0;
            if (outHeight > mHeight) {
                realHeight = mHeight;
                realWidth = realHeight * outWidth / outHeight;
            }
            if (realWidth > mWidth) {
                realWidth = mWidth;
                realHeight = realWidth * outHeight / outWidth;
            }

            options.inJustDecodeBounds = false;
            options.inSampleSize = realWidth / outWidth;
            bitmap = BitmapFactory.decodeResource(getResources(), hintImg, options);

            Rect rect = new Rect(getMeasuredWidth() / 2 - realWidth / 2, getPaddingTop(), getMeasuredWidth() / 2 + realWidth / 2, getMeasuredHeight() / 2 + realHeight / 2);
            canvas.drawBitmap(bitmap, null, rect, mPaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        showHintImg = text.length() == 0;
        invalidate();
    }
}
