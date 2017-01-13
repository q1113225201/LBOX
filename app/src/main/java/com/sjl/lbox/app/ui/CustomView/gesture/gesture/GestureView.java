package com.sjl.lbox.app.ui.CustomView.gesture.gesture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.sjl.lbox.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 手势密码
 *
 * @author SJL
 * @date 2016/10/16 21:26
 */
public class GestureView extends View {
    private static final String tag = GestureView.class.getSimpleName();

    private Context mContext;

    public GestureView(Context context) {
        this(context, null);
    }

    public GestureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }

    private Paint mPaint;//画笔
    private Paint mPaintNormal;//普通画笔
    private Paint mPaintError;//错误画笔
    private Paint mPaintSuccess;//正确画笔

    private Bitmap mBitmapNormal;//正常点图片
    private Bitmap mBitmapSelect;//选中点图片
    private Bitmap mBitmapError;//错误点图片
    private Bitmap mBitmapSuccess;//正确点图片

    private int column = 3;//方正单行点数
    private int radius;//半径
    private int imgRadius;//图片半径
    private Point[][] points;//方正地图
    private List<Point> selectPoints;//选中点列表
    private Point currentPoint;//手指在手机上的位置
    private Boolean isDrawing;//是否在绘制手势密码

    private void init() {
        //初始化画笔
        mPaintNormal = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNormal.setColor(Color.parseColor("#00fff8"));
        mPaintNormal.setStrokeWidth(4);
        mPaintError = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintError.setColor(Color.RED);
        mPaintError.setStrokeWidth(6);
        mPaintSuccess = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSuccess.setColor(Color.GREEN);
        mPaintSuccess.setStrokeWidth(6);
        mPaint = mPaintNormal;

        //初始化点
        column = 3;
        points = new Point[column][column];
        int width = getWidth();
        int height = getHeight();
        if (width > height) {
            int tmp = width;
            width = height;
            height = tmp;
        }
        measure(0,0);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
        params.width = width;
        params.height = height;
        setLayoutParams(params);
        int itemWidth = width / (column + 1);
        radius = itemWidth / 2;
        imgRadius = radius/2;

        for (int i = 0; i < column; i++) {
            for (int j = 0; j < column; j++) {
                points[i][j] = new Point(itemWidth * (i + 1), itemWidth * (j + 1), i + j * column);
            }
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = itemWidth;
        options.outHeight = itemWidth;

        //初始化点图片
        mBitmapNormal = zoomImg(BitmapFactory.decodeResource(getResources(), R.drawable.gesture_point_normal),radius,radius);
        mBitmapSelect = zoomImg(BitmapFactory.decodeResource(getResources(), R.drawable.gesture_point_select),radius,radius);
        mBitmapError = zoomImg(BitmapFactory.decodeResource(getResources(), R.drawable.gesture_point_error),radius,radius);
        mBitmapSuccess = zoomImg(BitmapFactory.decodeResource(getResources(), R.drawable.gesture_point_success),radius,radius);

        selectPoints = new ArrayList<Point>();
        isDrawing = false;
        reset();
    }
    /**
     *  处理图片
     * @param bm 所要转换的bitmap
     * @param newWidth 新的宽
     * @param newHeight 新的高
     * @return 指定宽高的bitmap
     */
    private Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){
        newWidth = newWidth<=0?1:newWidth;
        newHeight = newHeight<=0?1:newHeight;

        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        drawPoints(canvas);
        drawLines(canvas);
    }

    /**
     * 绘制点
     *
     * @param canvas
     */
    private void drawPoints(Canvas canvas) {
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < column; j++) {
                Bitmap tmpBitmap = null;
                Point currentPoint = points[i][j];
                if (currentPoint.status == Point.NORMAL) {
                    tmpBitmap = mBitmapNormal;
                } else if (currentPoint.status == Point.SELECT) {
                    tmpBitmap = mBitmapSelect;
                } else if (currentPoint.status == Point.SUCCESS) {
                    tmpBitmap = mBitmapSuccess;
                } else if (currentPoint.status == Point.ERROR) {
                    tmpBitmap = mBitmapError;
                }
                canvas.drawBitmap(tmpBitmap, currentPoint.x-imgRadius, currentPoint.y-imgRadius, null);
            }
        }
    }

    /**
     * 重置
     */
    public void reset() {
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < column; j++) {
                points[i][j].status = Point.NORMAL;
            }
        }
        selectPoints.clear();
        invalidate();
    }

    public void success() {
        mPaint = mPaintSuccess;
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < column; j++) {
                if (points[i][j].status != Point.NORMAL) {
                    points[i][j].status = Point.SUCCESS;
                }
            }
        }
        invalidate();
    }

    public void error() {
        mPaint = mPaintError;
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < column; j++) {
                if (points[i][j].status != Point.NORMAL) {
                    points[i][j].status = Point.ERROR;
                }
            }
        }
        invalidate();
    }

    /**
     * 绘制线
     *
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        if (selectPoints.size() > 0) {
            Point tmpPoint = null;
            for (Point itemPoint : selectPoints) {
                if (tmpPoint != null) {
                    drawLine(canvas, tmpPoint, itemPoint);
                }
                tmpPoint = itemPoint;
            }
            if (isDrawing) {
                drawLine(canvas, tmpPoint, currentPoint);
            }
        }
    }

    /**
     * 绘制两点间的线
     *
     * @param canvas
     * @param startPoint
     * @param endPoint
     */
    private void drawLine(Canvas canvas, Point startPoint, Point endPoint) {
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentPoint = new Point((int) event.getX(), (int) event.getY());
        Log.i(tag, "onTouchEvent:" + event.getX() + "," + event.getY() + "," + currentPoint.x + "," + currentPoint.y);
        if (MotionEvent.ACTION_DOWN == event.getAction()) {//手指按下
            if (isSelectPoint(currentPoint)) {
                isDrawing = true;
                mPaint = mPaintNormal;
            }
        } else if (MotionEvent.ACTION_MOVE == event.getAction() && isDrawing) {//手指移动
            isSelectPoint(currentPoint);
        } else if (MotionEvent.ACTION_UP == event.getAction() && isDrawing) {//手指松开
            isDrawing = false;
            currentPoint = null;
            if (onDrawFinishedListener != null) {
                onDrawFinishedListener.onDrawFinished(this,GestureUtil.getSelectResult(selectPoints));
            }
        }
        invalidate();
        return true;
    }


    /**
     * 判断该点击的点是否能否成为选中点
     * 能，选中点并加如选中点列表
     *
     * @param point
     * @return
     */
    private Boolean isSelectPoint(Point point) {
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < column; j++) {
                if (points[i][j].status == Point.NORMAL && points[i][j].getDistance(point) < imgRadius) {
                    //当前点未被选中，并且该点位置和point点距离小于半径
                    points[i][j].status=Point.SELECT;
                    selectPoints.add(points[i][j]);
                    return true;
                }
            }
        }
        return false;
    }

    private OnDrawFinishedListener onDrawFinishedListener;

    public interface OnDrawFinishedListener {
        boolean onDrawFinished(GestureView gestureView,String selectNum);
    }

    /**
     * 设置绘制完成监听接口
     *
     * @param onDrawFinishedListener
     */
    public void setOnDrawFinishedListener(OnDrawFinishedListener onDrawFinishedListener) {
        this.onDrawFinishedListener = onDrawFinishedListener;
    }

    public class Point {
        public static final int NORMAL = 1;
        public static final int SELECT = 2;
        public static final int SUCCESS = 3;
        public static final int ERROR = 4;
        public int x;
        public int y;
        public int num;
        public int status;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
            this.status = NORMAL;
        }
        public Point(int x, int y,int num) {
            this.x = x;
            this.y = y;
            this.num = num;
            this.status = NORMAL;
        }

        /**
         * 获取两点间距离
         * @param point
         * @return
         */
        public float getDistance(Point point){
            return (float) Math.sqrt((x-point.x)*(x-point.x)+(y-point.y)*(y-point.y));
        }
    }
}
