package com.sjl.lbox.app.ui.CustomView.repeat.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sjl.lbox.R;

/**
 * 自定义分割布局
 *
 * @author 林zero
 * @date 2018/4/11
 */

public class CustomItemDecoration extends RecyclerView.ItemDecoration {
    private Context context;
    private int offsetTop = 2;
    private int offsetLeft = 100;
    private Paint paint;
    private Bitmap bitmap;

    public CustomItemDecoration(Context context) {
        this.context = context;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(2f);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //第一个不需要在上面绘制分割线
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = 0;
        } else {
            outRect.top = offsetTop;
        }
        outRect.left = offsetLeft;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //ItemView下方绘制，会被覆盖
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            float dividerTop = view.getTop() - offsetTop;
            float dividerLeft = parent.getPaddingLeft() + offsetLeft;
            float dividerBottom = view.getTop();
            float dividerRight = parent.getWidth() - parent.getPaddingRight();

            int index = parent.getChildAdapterPosition(view);
            if (index == 0) {
                //第一个不绘制
                dividerTop = view.getTop();
            }
            //绘制分割线
            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, paint);
            //绘制左上线
            c.drawLine(dividerLeft / 2, dividerTop, dividerLeft / 2, dividerTop + (view.getBottom() - dividerTop) / 3, paint);
            //绘制中间节点
            c.drawCircle(dividerLeft / 2, dividerTop + (view.getBottom() - dividerTop) / 2, (view.getBottom() - dividerTop) / 3 / 2, paint);
            //绘制左下线
            c.drawLine(dividerLeft / 2, dividerTop + (view.getBottom() - dividerTop) / 3 * 2, dividerLeft / 2, view.getBottom(), paint);

        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //ItemView上方绘制，不会被覆盖
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            //绘制图标
            c.drawBitmap(bitmap, null, new Rect(view.getRight() / 2 - 100, view.getTop() + offsetTop + 10, view.getRight() / 2, view.getBottom() - 10), paint);
        }
    }
}
