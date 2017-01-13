package com.sjl.lbox.app.ui.CustomView.repeat.SwipeItemLayout.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import com.sjl.lbox.R;

/**
 * SwipeItemLayout
 *
 * @author SJL
 * @date 2016/12/28 21:13
 */

public class SwipeItemLayout extends ViewGroup {
    public SwipeItemLayout(Context context) {
        this(context, null);
    }

    public SwipeItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private Context context;
    //处理单击事件的冲突
    private int mScaleTouchSlop;
    //计算滑动速度
    private int mMaxVelocity;
    //能否滑动
    private boolean isSwipeEnable;
    //是否左滑
    private boolean isLeftSwipe;
    //可以滑动的最大距离
    private int mMaxSwipeWidth;
    //滑动限制
    private int mSwipeLimitWidth;
    //控件高度
    private int mHeight;
    //显示的布局
    private View mContentView;

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
        mScaleTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();

        //初始化属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeItemLayout);
        isSwipeEnable = typedArray.getBoolean(R.styleable.SwipeItemLayout_isSwipeEnable, true);
        isLeftSwipe = typedArray.getBoolean(R.styleable.SwipeItemLayout_isLeftSwipe, true);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //令自己可点击，获取点击事件
        setClickable(true);
        //由于ViewHolder的复用机制，每次都需要手动恢复初始化
        mMaxSwipeWidth = 0;
        mSwipeLimitWidth = 0;
        mHeight = 0;
        //显示布局的宽度
        int contentWidth = 0;
        //子控件数量
        int childCount = getChildCount();
        //为了子控件的高度，可以MATCH_PARENT(参考的FrameLayout 和LinearLayout的Horizontal)
        boolean measureMatchParentChildren = MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
        boolean isNeedMeasureChildHeight = false;

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.setClickable(true);
            if (childView.getVisibility() != GONE) {
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);

                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                //是否需要设置高度
                if (measureMatchParentChildren && layoutParams.height == LayoutParams.MATCH_PARENT) {
                    isNeedMeasureChildHeight = true;
                }
                //获取最大高度
                mHeight = Math.max(mHeight, childView.getMeasuredHeight());

                if (i > 0) {
                    //其他布局计算滑动的最大宽度
                    mMaxSwipeWidth += childView.getMeasuredWidth();
                } else {
                    //第一个是显示的布局
                    mContentView = childView;
                    contentWidth = childView.getMeasuredWidth();
                }
            }
        }
        //设置控件宽高
        setMeasuredDimension(getPaddingLeft() + getPaddingRight() + contentWidth, getPaddingTop() + getPaddingBottom() + mHeight);
        //滑动临界值
        mSwipeLimitWidth = mMaxSwipeWidth / 2;
        //如果子控件中有MATCH_PARENT属性，设置子View高度
        if (isNeedMeasureChildHeight) {
            forceUniformHeight(childCount, widthMeasureSpec);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //里面用的LayoutParams是MarginLayoutParams类的需要设置下，不然类型转换出问题会直接崩溃
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * 给MATCH_PARENT的子控件设置高度
     *
     * @param count
     * @param widthMeasureSpec
     * @see android.widget.LinearLayout 的同名方法
     */
    private void forceUniformHeight(int count, int widthMeasureSpec) {
        // Pretend that the linear layout has an exact size. This is the measured height of
        // ourselves. The measured height should be the max height of the children, changed
        // to accommodate the heightMeasureSpec from the parent
        //为父布局高度构建一个Exactly的测量参数
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY);
        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                if (lp.height == LayoutParams.MATCH_PARENT) {
                    // Temporarily force children to reuse their old measured width
                    // FIX ME: this may not be right for something like wrapping text?
                    int oldWidth = lp.width;
                    lp.width = child.getMeasuredWidth();
                    // Remeasure with new dimensions
                    measureChildWithMargins(child, widthMeasureSpec, 0, uniformMeasureSpec, 0);
                    lp.width = oldWidth;
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = getPaddingLeft();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != GONE) {
                if (i == 0) {
                    //第一个显示布局设置成填充父窗体
                    childView.layout(left, getPaddingTop(), left + childView.getMeasuredWidth(), getPaddingTop() + childView.getMeasuredHeight());
                    left += childView.getMeasuredWidth();
                } else {
                    if (isLeftSwipe) {
                        //每个控件的
                        // 左边位置 = 之前每个控件的宽度与paddingLeft之和
                        // 上边位置 = paddingTop
                        // 右边位置 = 之前每个控件的宽度与paddingLeft之和 + 当前控件宽度
                        // 下边位置 = paddingTop + 当前控件高度
                        childView.layout(left, getPaddingTop(), left + childView.getMeasuredWidth(), getPaddingTop() + childView.getMeasuredHeight());
                        left += childView.getMeasuredWidth();
                    }
                }
            }
        }
    }

    //是否触摸,如果已经触摸，再有其他手指不响应
    private boolean isTouching;
    //正在滑动，滑动时屏蔽所有点击事件
    private boolean isSwiping;
    //移动过,如果每移动需要关闭滑动
    private boolean isMoved;
    //第一个手指落点
    private PointF mFirstPoint = new PointF();
    //最近一个落点位置
    private PointF mLastPoint = new PointF();
    //记录最后滑动的Item
    private static SwipeItemLayout mLastSwipeItemLayout;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isSwipeEnable) {
            acquireVelocityTracker(ev);
            VelocityTracker velocityTracker = mVelocityTracker;
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isSwiping = false;
                    isMoved = false;
                    //如果已经触摸，再有其他手指不响应
                    if (isTouching) {
                        return false;
                    } else {
                        isTouching = true;
                    }
                    //记录第一个手指落点
                    mFirstPoint.set(ev.getRawX(), ev.getRawY());
                    if (mLastSwipeItemLayout != null) {
                        if (mLastSwipeItemLayout != this) {
                            mLastSwipeItemLayout.closeSwipe();
                        }
                        //屏蔽父布局点击事件
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    float gap = mLastPoint.x - ev.getRawX();
                    //是屏蔽父布局点击事件更加灵敏
                    if (Math.abs(gap) > 10 || Math.abs(getScaleX()) > 10) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    //滑动展开时，点击内容区域需要关闭
                    if (Math.abs(gap) > mScaleTouchSlop) {
                        isMoved = true;
                    }
                    //滑动
                    scrollBy((int) gap, 0);
                    if (isLeftSwipe) {
                        //越界修正
                        if (gap < 0) {
                            scrollTo(0, 0);
                        }
                        if (getScrollX() > mMaxSwipeWidth) {
                            scrollTo(mMaxSwipeWidth, 0);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    //判断手指起落点如果距离属于滑动，屏蔽一切点击事件
                    if (Math.abs(ev.getRawX() - mFirstPoint.x) > mScaleTouchSlop) {
                        isSwiping = true;
                    }
                    //获取伪瞬时速度
                    velocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                    float velocityX = velocityTracker.getXVelocity((int) mFirstPoint.x);
                    if (Math.abs(velocityX) > 1000) {
                        //超过滑动阈值
                        if (isLeftSwipe) {
                            openSwipe();
                        } else {
                            closeSwipe();
                        }
                    } else {
                        //否则判断滑动距离
                        if (Math.abs(getScrollX()) >= mSwipeLimitWidth) {
                            //超过展开
                            openSwipe();
                        } else {
                            closeSwipe();
                        }
                    }
                    //释放
                    releaseVelocityTracker();
                    isTouching = false;
                    break;
            }
        }
        //记录最近一个落点位置
        mLastPoint.set(ev.getRawX(), ev.getRawY());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //禁止滑动时，点击事件不受干扰
        if (isSwipeEnable) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    //滑动时屏蔽点击事件
                    if (Math.abs(ev.getRawX() - mFirstPoint.x) > mScaleTouchSlop) {
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (isLeftSwipe) {
                        if (getScrollX() > mScaleTouchSlop) {
                            if (ev.getX() < getWidth() - getScrollX()) {
                                //点击在内容区域外
                                if (!isMoved) {
                                    //点击内容区域，关闭滑动
                                    closeSwipe();
                                }
                                return true;
                            }
                        }
                    }
                    //如果属于滑动，屏蔽点击事件
                    if (isSwiping) {
                        return true;
                    }
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private VelocityTracker mVelocityTracker;

    /**
     * 设置速度追踪
     *
     * @param ev
     */
    private void acquireVelocityTracker(MotionEvent ev) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
    }

    /**
     * * 释放VelocityTracker
     *
     * @see VelocityTracker#clear()
     * @see VelocityTracker#recycle()
     */
    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * 展开滑动
     */
    public void openSwipe() {
        mLastSwipeItemLayout = this;
        if (mContentView != null) {
            mContentView.setLongClickable(false);
        }
        playOpenAnim();
    }

    /**
     * 关闭滑动
     */
    public void closeSwipe() {
        mLastSwipeItemLayout = null;
        if (mContentView != null) {
            mContentView.setLongClickable(true);
        }
        playCloseAnim();
    }

    //滑动打开动画
    private ValueAnimator openAnim;
    //滑动关闭动画
    private ValueAnimator closeAnim;
    //动画持续时间
    private final int DURATION = 300;

    /**
     * 播放滑动打开动画
     */
    private void playOpenAnim() {
        cancelAnim();
        if (openAnim == null) {
            openAnim = ValueAnimator.ofInt();
            openAnim.setInterpolator(new AccelerateInterpolator());
            openAnim.setDuration(DURATION);
            openAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scrollTo((Integer) animation.getAnimatedValue(), 0);
                }
            });
        }
        int startValue = getScrollX() > mMaxSwipeWidth ? 0 : getScrollX();
        openAnim.setIntValues(startValue, mMaxSwipeWidth);
        openAnim.start();
    }

    /**
     * 播放滑动关闭动画
     */
    private void playCloseAnim() {
        cancelAnim();
        if (closeAnim == null) {
            closeAnim = ValueAnimator.ofInt();
            closeAnim.setInterpolator(new AccelerateInterpolator());
            closeAnim.setDuration(DURATION);
            closeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scrollTo((Integer) animation.getAnimatedValue(), 0);
                }
            });
        }
        closeAnim.setIntValues(getScrollX(), 0);
        closeAnim.start();
    }

    /**
     * 取消所有动画
     */
    private void cancelAnim() {
        if (openAnim != null && openAnim.isRunning()) {
            openAnim.cancel();
        }
        if (closeAnim != null && closeAnim.isRunning()) {
            closeAnim.cancel();
        }
    }

    /**
     * 快速关闭。
     * 用于 点击侧滑菜单上的选项,同时想让它快速关闭(删除 置顶)。
     * 这个方法在ListView里是必须调用的，
     * 在RecyclerView里，视情况而定，如果是mAdapter.notifyItemRemoved(pos)方法不用调用。
     */
    public void quickClose() {
        if (this == mLastSwipeItemLayout) {
            //先取消展开动画
            cancelAnim();
            mLastSwipeItemLayout.scrollTo(0, 0);//关闭
            mLastSwipeItemLayout = null;
        }
    }

    /**
     * 每次控件从界面消失时判断，如果最后滑动的是自己，关闭滑动
     * 1、防止内存泄漏
     * 2、View被recycler回收复用，下一个应该是普通状态
     */
    @Override
    protected void onDetachedFromWindow() {
        if (mLastSwipeItemLayout == this) {
            mLastSwipeItemLayout.closeSwipe();
            mLastSwipeItemLayout = null;
        }
        super.onDetachedFromWindow();
    }

    /**
     * 展开时，禁止长按
     *
     * @return
     */
    @Override
    public boolean performClick() {
        if (Math.abs(getScrollX()) > mScaleTouchSlop) {
            return false;
        }
        return super.performClick();
    }
}
