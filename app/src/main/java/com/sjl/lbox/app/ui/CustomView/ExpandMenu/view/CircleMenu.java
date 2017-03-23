package com.sjl.lbox.app.ui.CustomView.ExpandMenu.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.sjl.lbox.R;
import com.sjl.lbox.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * CircleMenu
 *
 * @author SJL
 * @date 2017/3/23
 */

public class CircleMenu extends ViewGroup {
    private static final String TAG = "CircleMenu";
    public CircleMenu(Context context) {
        this(context, null);
    }

    public CircleMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    //菜单列表
    private List<View> menuList = new ArrayList<>();
    //中间菜单
    private FloatingActionButton centerMenu;
    //菜单图片
    private Drawable menuDrawable;
    //菜单背景色
    private Drawable menuBackground;
    //是否展开
    private boolean isExpanded = false;
    //是否正在播放动画
    private boolean isAnimating = false;

    private void init(AttributeSet attrs) {
        initAttrs(attrs);

        centerMenu = new FloatingActionButton(getContext());
        centerMenu.setImageDrawable(menuDrawable);
    }

    /**
     * 初始化属性
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        menuDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_open_close);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initMenus();
    }

    private void initMenus() {
        menuList = new ArrayList<>();
        LogUtil.i(TAG,"getChildCount()="+getChildCount());
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            menuList.add(childView);
        }
        addView(centerMenu,super.generateDefaultLayoutParams());
        centerMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnimating) {
                    return;
                }
                //切换菜单状态
                if (isExpanded) {
                    closeMenu();
                } else {
                    openMenu();
                }
                isExpanded = !isExpanded;
            }
        });
    }

    /**
     * 展开菜单
     */
    private void openMenu() {
        for (int i=0;i<getChildCount();i++){
            ViewCompat.setTranslationX(getChildAt(i),100*i*(i%2==0?1:-1));
        }
        requestLayout();
    }

    /**
     * 关闭菜单
     */
    private void closeMenu() {

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        int height = getHeight();
        int centerMenuWidth = centerMenu.getMeasuredWidth();
        int centerMenuHeight = centerMenu.getMeasuredHeight();
        centerMenu.layout(
                (width - centerMenuWidth) / 2,
                (height - centerMenuHeight) / 2,
                (width + centerMenuWidth) / 2,
                (height + centerMenuHeight) / 2
        );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int maxWidth=0;
        int maxHeight=0;
        if(isExpanded){
            //已展开
            maxWidth = centerMenu.getMeasuredWidth()*3;
            maxHeight = centerMenu.getMeasuredHeight()*3;
        }else{
            //未展开
            maxWidth = centerMenu.getMeasuredWidth();
            maxHeight = centerMenu.getMeasuredHeight();
        }
        //LogUtil.i(TAG,"onMeasure width="+maxWidth+",height="+maxHeight);
        setMeasuredDimension(maxWidth,maxHeight);
    }
}
