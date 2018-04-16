package com.sjl.lbox.app.ui.CustomView.repeat.recyclerview.StickyHeader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.util.DensityUtil;
import com.sjl.lbox.util.LogUtil;

/**
 * SectionDecoration
 *
 * @author 林zero
 * @date 2018/4/11
 */

public class SectionDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "SectionDecoration";

    private Context context;
    private GroupInfoCallback groupInfoCallback;
    private int headerHeight = 50;
    private int dividerHeight = 1;
    private int textSize = 14;
    private Paint paint;
    private Paint textPaint;

    public SectionDecoration(Context context, GroupInfoCallback groupInfoCallback) {
        this.context = context;
        this.groupInfoCallback = groupInfoCallback;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#ebebeb"));
        paint.setStrokeWidth(2f);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(DensityUtil.dp2px(context, textSize));
        textPaint.getTextBounds("A", 0, 1, new Rect());

        headerHeight = DensityUtil.dp2px(context, 25);
    }

    public interface GroupInfoCallback {
        GroupInfo getGroupInfo(int position);

        View getHeaderView();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        if (groupInfoCallback != null) {
            GroupInfo groupInfo = groupInfoCallback.getGroupInfo(position);
            if (groupInfo != null && groupInfo.isFirstViewInGroup()) {
                //如果是组内第一个撑开header高度
                outRect.top = headerHeight;
            } else {
                //否则就是分割线高度
                outRect.top = dividerHeight;
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        /*int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);

            int index = parent.getChildAdapterPosition(view);
            if (groupInfoCallback != null) {
                GroupInfo groupInfo = groupInfoCallback.getGroupInfo(index);

                if (groupInfo.isFirstViewInGroup()) {
                    //如果是组内第一个ItemView之上才绘制
                    int left = parent.getPaddingLeft();
                    int top = view.getTop() - headerHeight;
                    int right = parent.getWidth() - parent.getPaddingRight();
                    int bottom = view.getTop();

                    //自定义布局头部
                    View groupView = groupInfoCallback.getHeaderView();
                    ((TextView) groupView.findViewById(R.id.tvName)).setText(groupInfo.getTitle());
                    if (groupView == null) return;
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerHeight);
                    groupView.setLayoutParams(layoutParams);
                    groupView.setDrawingCacheEnabled(true);
                    groupView.measure(
                            View.MeasureSpec.makeMeasureSpec(right, View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    //指定高度、宽度的groupView
                    groupView.layout(0, 0, right, headerHeight);
                    groupView.buildDrawingCache();
                    Bitmap bitmap = groupView.getDrawingCache();
                    LogUtil.i(TAG, "right----------------------------------" + right);
                    c.drawBitmap(bitmap, left, top, null);
                }
            }
        }*/
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);

            int index = parent.getChildAdapterPosition(view);
            if (groupInfoCallback != null) {
                GroupInfo groupInfo = groupInfoCallback.getGroupInfo(index);
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();
                int top = Math.max(headerHeight,view.getTop()+parent.getPaddingTop());

                //自定义布局头部
                View groupView = groupInfoCallback.getHeaderView();

                if (groupInfo.isFirstViewInGroup()||i==0) {
                    //如果是组内第一个ItemView之上才绘制
                    ((TextView) groupView.findViewById(R.id.tvName)).setText(groupInfo.getTitle());
                    if (groupView == null) return;
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerHeight);
                    groupView.setLayoutParams(layoutParams);
                    groupView.setDrawingCacheEnabled(true);
                    groupView.measure(
                            View.MeasureSpec.makeMeasureSpec(right, View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(headerHeight, View.MeasureSpec.EXACTLY));
                    //指定高度、宽度的groupView
                    groupView.layout(0, 0, right,headerHeight);
                    groupView.buildDrawingCache();
                    Bitmap bitmap = groupView.getDrawingCache();
                    GroupInfo next = groupInfoCallback.getGroupInfo(index+1);
                    LogUtil.i(TAG,view.getBottom()+"---------------------"+top);
                    if(next!=null&&next.isFirstViewInGroup()&&view.getBottom()<top){
                        top = view.getBottom();
                    }
                    c.drawBitmap(bitmap, left, top-headerHeight, null);
                }
            }
        }
    }
}
