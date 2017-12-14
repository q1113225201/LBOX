package com.sjl.lbox.app.component.WallpaperService.service;

import android.hardware.Camera;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.io.IOException;
/**
 * 相机壁纸服务
 *
 * @author SJL
 * @date 2017/8/23
 */
public class CameraWallpaperService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new CameraEngine();
    }

    class CameraEngine extends Engine {
        Camera camera;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            startPreview();
            //接受触摸事件
            setTouchEventsEnabled(true);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible) {
                startPreview();
            } else {
                stopPreview();
            }
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            //可以添加触摸事件
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            stopPreview();
        }

        /**
         * 开始预览
         */
        private void startPreview() {
            camera = Camera.open();
            camera.setDisplayOrientation(90);
            try {
                camera.setPreviewDisplay(getSurfaceHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
        }

        /**
         * 关闭预览
         */
        private void stopPreview() {
            if (camera != null) {
                camera.stopPreview();
                camera.setPreviewCallback(null);
                camera.release();
            }
            camera = null;
        }

    }
}
