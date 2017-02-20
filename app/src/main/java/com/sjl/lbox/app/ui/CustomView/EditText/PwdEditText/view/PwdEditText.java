package com.sjl.lbox.app.ui.CustomView.EditText.PwdEditText.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.EditText;

import com.sjl.lbox.R;


/**
 * 仿支付宝密码输入框
 *
 * @author SJL
 * @date 2016/11/29 21:30
 */
public class PwdEditText extends EditText {

    private Context context;

    private int mWidth;
    private int mHeight;

    //圆角半径
    private float borderCornerRadius = 10f;
    //密码长度
    private int passwordLength = 6;

    //背景的笔
    private Paint bgPaint;
    //背景颜色
    private int bgColor = Color.WHITE;

    //边框的笔
    private Paint borderPaint;
    //画线的笔
    private Paint linePaint;
    //线宽
    private float lineWidth = 4f;
    //线颜色
    private int lineColor = Color.GRAY;

    //画点的笔
    private Paint dotPaint;
    //点半径
    private float dotRadius = 12f;
    //点颜色
    private int dotColor = Color.BLACK;

    //画文字的笔
    private Paint textPaint;
    //点颜色
    private int textColor = Color.BLACK;
    //字体大小
    private float textSize;

    //是否显示内容
    private boolean showContent = false;

    private OnTextEndListener onTextEndListener;

    public void setOnTextEndListener(OnTextEndListener onTextEndListener) {
        this.onTextEndListener = onTextEndListener;
    }

    public interface OnTextEndListener {
        void onTextEnd(String str);
    }

    public PwdEditText(Context context) {
        this(context, null);
    }

    public PwdEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PwdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
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

    /**
     * 初始化属性
     *
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PwdEditText);

        bgColor = typedArray.getColor(R.styleable.PwdEditText_bgColor, Color.WHITE);
        borderCornerRadius = typedArray.getFloat(R.styleable.PwdEditText_borderCornerRadius, 20f);
        passwordLength = typedArray.getInteger(R.styleable.PwdEditText_passwordLength, 6);
        lineWidth = typedArray.getFloat(R.styleable.PwdEditText_lineWidth, 4f);
        lineColor = typedArray.getColor(R.styleable.PwdEditText_lineColor, Color.GRAY);
        dotRadius = typedArray.getFloat(R.styleable.PwdEditText_dotRadius, 12f);
        dotColor = typedArray.getColor(R.styleable.PwdEditText_lineColor, Color.BLACK);
        textColor = dotColor;
        textSize = getTextSize();
        showContent = typedArray.getBoolean(R.styleable.PwdEditText_showContent, false);
        typedArray.recycle();
    }

    /**
     * 初始化工具
     */
    private void initTools() {
        //设置输入框最大输入长度
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(passwordLength)});

        //背景画笔初始化
        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(bgColor);

        //画边框笔初始化
        borderPaint = new Paint();
        borderPaint.setStrokeWidth(lineWidth);
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(lineColor);
        borderPaint.setStyle(Paint.Style.STROKE);

        //画线笔初始化
        linePaint = new Paint();
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setAntiAlias(true);
        linePaint.setColor(lineColor);

        //画点笔初始化
        dotPaint = new Paint();
        dotPaint.setStrokeWidth(dotRadius);
        dotPaint.setAntiAlias(true);
        dotPaint.setColor(dotColor);

        //画文字笔初始化
        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        textSize = getTextSize();
        textPaint.setTextSize(textSize);

        //画边框
        drawBorder(canvas);
        //画分割线
        drawLines(canvas);
        if (showContent) {
            //画文字
            drawText(canvas);
        } else {
            //画点
            drawDots(canvas);
        }
    }

    /**
     * 画边框
     *
     * @param canvas
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawBorder(Canvas canvas) {
        //绘制背景
        canvas.drawRoundRect(0, 0, mWidth, mHeight, borderCornerRadius, borderCornerRadius, bgPaint);
        //绘制边框
        canvas.drawRoundRect(0, 0, mWidth, mHeight, borderCornerRadius, borderCornerRadius, borderPaint);
    }

    /**
     * 画分割线
     *
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        //每个密码框宽度
        int itemWidth = (int) ((mWidth - 2 * lineWidth - (passwordLength - 1) * lineWidth) / passwordLength);
        for (int i = 0; i < passwordLength - 1; i++) {
            //第i条线分割线
            //起点x坐标=（i+1）个边框宽度和密码框宽度
            //起点y坐标= 0
            //终点x坐标=（i+1）个边框宽度和密码框宽度
            //终点y坐标=输入框下边框处
            canvas.drawLine((i + 1) * (lineWidth + itemWidth), 0, (i + 1) * (lineWidth + itemWidth), mHeight, linePaint);
        }
    }

    /**
     * 画点
     *
     * @param canvas
     */
    private void drawDots(Canvas canvas) {
        int length = getText().length();
        //每个密码框宽度
        int itemWidth = (int) ((mWidth - 2 * lineWidth - (passwordLength - 1) * lineWidth) / passwordLength);
        for (int i = 0; i < length; i++) {
            //第i个绘制点
            //x坐标=(i + 1)个边框线宽度和密码框宽度-半个密码框宽度
            //y坐标=输入框高度的一半
            canvas.drawCircle((i + 1) * (lineWidth + itemWidth) - 0.5f * itemWidth, mHeight / 2, dotRadius / 2, dotPaint);
        }
    }

    /**
     * 画文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        int length = getText().length();
        String str = getText().toString();
        //每个密码框宽度
        int itemWidth = (int) ((mWidth - 2 * lineWidth - (passwordLength - 1) * lineWidth) / passwordLength);
        for (int i = 0; i < length; i++) {
            Rect rect = new Rect();
            textPaint.getTextBounds(str, i, i + 1, rect);
            canvas.drawText(str, i, i + 1, (i + 1) * (lineWidth + itemWidth) - 0.5f * itemWidth-rect.width()/2, mHeight / 2 + rect.height() / 2, textPaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (text.toString().length() == passwordLength && onTextEndListener != null) {
            onTextEndListener.onTextEnd(text.toString());
        }
        invalidate();
    }

    /**
     * 清空输入框
     */
    public void clear() {
        setText("");
        invalidate();
    }

    /**
     * 代码设置属性后更新画笔工具
     */
    public void updateTools() {
        initTools();
        invalidate();
    }

    public float getBorderCornerRadius() {
        return borderCornerRadius;
    }

    public void setBorderCornerRadius(float borderCornerRadius) {
        this.borderCornerRadius = borderCornerRadius;
        updateTools();
    }

    public int getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
        updateTools();
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        updateTools();
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        updateTools();
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        updateTools();
    }

    public Paint getDotPaint() {
        return dotPaint;
    }

    public void setDotPaint(Paint dotPaint) {
        this.dotPaint = dotPaint;
        updateTools();
    }

    public float getDotRadius() {
        return dotRadius;
    }

    public void setDotRadius(float dotRadius) {
        this.dotRadius = dotRadius;
        updateTools();
    }

    public int getDotColor() {
        return dotColor;
    }

    public void setDotColor(int dotColor) {
        this.dotColor = dotColor;
        updateTools();
    }

    public boolean isShowContent() {
        return showContent;
    }

    public void setShowContent(boolean showContent) {
        this.showContent = showContent;
        updateTools();
    }
}
