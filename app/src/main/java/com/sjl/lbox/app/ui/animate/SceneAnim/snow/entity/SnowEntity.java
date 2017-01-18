package com.sjl.lbox.app.ui.animate.SceneAnim.snow.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.Random;

/**
 * SnowEntity
 *
 * @author SJL
 * @date 2017/1/17
 */

public class SnowEntity {
    //半径范围7~20
    private static final float RADIUS_MIN = 7f;
    private static final float RADIUS_MAX = 20f;
    //速度范围7~20
    private static final float SPEED_MIN = 2f;
    private static final float SPEED_MAX = 4f;
    //角度范围60~120
    private static final float ANGLE_MIN = 60f;
    private static final float ANGLE_MAX = 120f;
    //初始范围
    private static final float INIT_MIN = -0.1f;
    private static final float INIT_MAX = 0.1f;

    //场景大小
    private PointF scenePointF;
    //半径
    private float radius;
    //速度
    private float speed;
    //角度
    private float angle;
    //当前位置
    private PointF currentPointF;
    //画笔
    private Paint paint;

    public SnowEntity(PointF scenePointF, float radius, float speed, float angle, PointF currentPointF, Paint paint) {
        this.scenePointF = scenePointF;
        this.radius = radius;
        this.speed = speed;
        this.angle = angle;
        this.currentPointF = currentPointF;
        this.paint = paint;
    }

    public static SnowEntity create(PointF scenePointF, Paint paint) {
        Random random = new Random();
        float radius = random.nextFloat() * (RADIUS_MAX - RADIUS_MIN) + RADIUS_MIN;
        float speed = random.nextFloat() * (SPEED_MAX - SPEED_MIN) + SPEED_MIN;
        float angle = random.nextFloat() * (ANGLE_MAX - ANGLE_MIN) + ANGLE_MIN;
        float x = random.nextFloat() * scenePointF.x;
        float y = random.nextFloat() * (scenePointF.y * (INIT_MAX - INIT_MIN)) - scenePointF.y * INIT_MAX;
        PointF currentPointF = new PointF(x, y);
        return new SnowEntity(scenePointF, radius, speed, angle, currentPointF, paint);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(currentPointF.x, currentPointF.y, radius, paint);
        move();
    }

    private void move() {
        //angle = new Random().nextFloat() * (ANGLE_MAX - ANGLE_MIN) + ANGLE_MIN;
        currentPointF.set((float)(currentPointF.x + speed * Math.cos(Math.PI * (angle) / 180)), (float)(currentPointF.y + speed * Math.sin(Math.PI * (angle) / 180)));
        if (!inSide()) {
            reset();
        }
    }

    /**
     * 是否在屏幕范围内
     *
     * @return
     */
    private boolean inSide() {
        return (currentPointF.x >= 0 && currentPointF.x <= scenePointF.x) && (currentPointF.y >= scenePointF.y * INIT_MIN && currentPointF.y <= scenePointF.y);
    }

    /**
     * 重置位置
     */
    private void reset() {
        Random random = new Random();
        radius = random.nextFloat() * (RADIUS_MAX - RADIUS_MIN) + RADIUS_MIN;
        speed = random.nextFloat() * (SPEED_MAX - SPEED_MIN) + SPEED_MIN;
        angle = random.nextFloat() * (ANGLE_MAX - ANGLE_MIN) + ANGLE_MIN;
        float x = random.nextFloat() * scenePointF.x;
        float y = random.nextFloat() * (scenePointF.y * (INIT_MAX - INIT_MIN)) - scenePointF.y * INIT_MAX;
        currentPointF = new PointF(x, y);
    }
}
