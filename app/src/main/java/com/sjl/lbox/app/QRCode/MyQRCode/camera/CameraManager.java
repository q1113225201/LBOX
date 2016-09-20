package com.sjl.lbox.app.QRCode.MyQRCode.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;

/**
 * 二维码相机管理类
 *
 * @author SJL
 * @date 2016/8/22 21:20
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class CameraManager implements Camera.AutoFocusCallback, Camera.PreviewCallback {


    private enum CameraState {
        CLOSED, OPEN, PREVIEW
    }

    private Camera camera;
    private Size screenSize;
    private Size cameraSize;
    private CameraState cameraState;
    private PreviewFrameShotListener frameShotListener;

    public void setFrameShotListener(PreviewFrameShotListener frameShotListener) {
        this.frameShotListener = frameShotListener;
    }

    private static final int REQUEST_AUTO_FOCUS_INTERVAL = 1000;
    private static final int MESSAGE_REQUEST_AUTO_FOCUS = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_REQUEST_AUTO_FOCUS:
                    if (camera != null && cameraState == CameraState.PREVIEW) {
                        camera.autoFocus(CameraManager.this);
                    }
                    break;
            }
        }
    };

    public CameraManager(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        screenSize = new Size(display.getWidth(), display.getHeight());
        cameraState = CameraState.CLOSED;
    }

    public Boolean initCamera(SurfaceHolder holder) {
        camera = Camera.open();
        if (camera == null) {
            return false;
        }
        cameraState = CameraState.OPEN;
        camera.setDisplayOrientation(90);
        Camera.Parameters parameters = camera.getParameters();
        cameraSize = getBestPreviewSize(parameters, screenSize);
        parameters.setPreviewSize(cameraSize.height, cameraSize.width);
        parameters.setPreviewFormat(ImageFormat.NV21);//Default
        camera.setParameters(parameters);
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public Boolean isCameraAvailable() {
        return camera != null;
    }

    public Boolean isFlashLightAvailable() {
        if (camera == null) {
            return false;
        }
        Camera.Parameters parameters = camera.getParameters();
        List<String> flashModes = parameters.getSupportedFlashModes();
        Boolean isFlashOnAvailable = false;
        Boolean isFlashOffAvailable = false;
        for (String flashMode : flashModes) {
            if (Camera.Parameters.FLASH_MODE_TORCH.equals(flashMode)) {
                isFlashOnAvailable = true;
            }
            if (Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
                isFlashOffAvailable = true;
            }
            if (isFlashOnAvailable && isFlashOffAvailable) {
                return true;
            }
        }
        return false;
    }

    public void enableFlashlight() {
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
    }

    public void disableFlashlight() {
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
    }

    public void startPreview() {
        if (camera != null) {
            cameraState = CameraState.PREVIEW;
            camera.startPreview();
            camera.autoFocus(CameraManager.this);
        }
    }

    public void stopPreview() {
        if (camera != null) {
            camera.stopPreview();
            cameraState = CameraState.OPEN;
        }
    }

    public void release() {
        if (camera != null) {
            camera.setOneShotPreviewCallback(null);
            camera.release();
            cameraState = CameraState.CLOSED;
        }
    }

    public void requestPreviewFrameShot() {
        camera.setOneShotPreviewCallback(CameraManager.this);
    }

    /**
     * 获取与屏幕大小最相近的预览图像大小
     *
     * @param parameters
     * @param screenSize
     * @return
     */
    private Size getBestPreviewSize(Camera.Parameters parameters, Size screenSize) {
        Size size = new Size(screenSize);
        int diff = Integer.MAX_VALUE;
        List<Camera.Size> previewList = parameters.getSupportedPreviewSizes();
        for (Camera.Size previewSize : previewList) {
            int previewWidth = previewSize.height;
            int previewHeight = previewSize.width;
            int newDiff = Math.abs(previewWidth - screenSize.width) * Math.abs(previewWidth - screenSize.width)
                    + Math.abs(previewHeight - screenSize.height) * Math.abs(previewHeight - screenSize.height);
            if (newDiff == 0) {
                size.width = previewWidth;
                size.height = previewHeight;
                return size;
            } else if (newDiff < diff) {
                diff = newDiff;
                size.width = previewWidth;
                size.height = previewHeight;
            }
        }
        return size;
    }
    /**
     * 因为预览图像和屏幕大小可能不一样，所以屏幕上的区域要根据比例转化为预览图像上对应的区域
     */
    public Rect getPreviewFrameRect(Rect screenFrameRect) {
        if (camera == null) {
            throw new IllegalStateException("在此方法之前需要线调用initCamera()");
        }
        Rect previewRect = new Rect();
        previewRect.left = screenFrameRect.left * cameraSize.width / screenSize.width;
        previewRect.right = screenFrameRect.right * cameraSize.width / screenSize.width;
        previewRect.top = screenFrameRect.top * cameraSize.height / screenSize.height;
        previewRect.bottom = screenFrameRect.bottom * cameraSize.height / screenSize.height;
        return previewRect;
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (cameraState == CameraState.PREVIEW) {
            handler.sendEmptyMessageDelayed(MESSAGE_REQUEST_AUTO_FOCUS, REQUEST_AUTO_FOCUS_INTERVAL);
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (frameShotListener != null) {
            data = rotateYUVdata90(data);
            frameShotListener.onPreviewFrame(data, cameraSize);
        }
    }

    private byte[] rotateYUVdata90(byte[] srcData) {
        byte[] desData = new byte[srcData.length];
        int srcWidth = cameraSize.height;
        int srcHeight = cameraSize.width;

        // Only copy Y
        int i = 0;
        for (int x = 0; x < srcWidth; x++) {
            for (int y = srcHeight - 1; y >= 0; y--) {
                desData[i++] = srcData[y * srcWidth + x];
            }
        }
        return desData;
    }
}
