package com.sjl.lbox.app.mobile.QRCode.MyQRCode.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.sjl.lbox.R;
import com.sjl.lbox.app.mobile.QRCode.MyQRCode.camera.CameraManager;
import com.sjl.lbox.app.mobile.QRCode.MyQRCode.camera.PreviewFrameShotListener;
import com.sjl.lbox.app.mobile.QRCode.MyQRCode.camera.Size;
import com.sjl.lbox.app.mobile.QRCode.MyQRCode.decode.DecodeListener;
import com.sjl.lbox.app.mobile.QRCode.MyQRCode.decode.DecodeThread;
import com.sjl.lbox.app.mobile.QRCode.MyQRCode.decode.LuminanceSource;
import com.sjl.lbox.app.mobile.QRCode.MyQRCode.decode.PlanarYUVLuminanceSource;
import com.sjl.lbox.app.mobile.QRCode.MyQRCode.decode.RGBLuminanceSource;
import com.sjl.lbox.app.mobile.QRCode.MyQRCode.view.CaptureView;

/**
 * 二维码扫描框界面
 * 需权限
 * <uses-permission android:name="android.permission.CAMERA" />
 * <uses-permission android:name="android.permission.FLASHLIGHT" />
 * <uses-permission android:name="android.permission.VIBRATE" />
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-feature android:name="android.hardware.camera" />
 * <uses-feature android:name="android.hardware.camera.autofocus" />
 * <uses-feature android:name="android.hardware.camera.flash" />
 *
 * @author SJL
 * @date 2016/8/22 21:20
 */
public class CaptureActivity extends Activity {
    public static int SCAN_CODE = 0;
    public static int SCAN_RESULT_CODE = 1;

    public static String TEXT_RESULR = "text_result";
    public static String BITMAP_RESULR = "bitmap_result";

    private SurfaceView svPreview;
    private CaptureView captureView;

    private Context context;

    private CameraManager cameraManager;
    private DecodeThread decodeThread;
    private Rect previewFrameRect = null;
    private Boolean isDecoding = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        initView();
    }

    private void initView() {
        svPreview = (SurfaceView) findViewById(R.id.svPreview);
        captureView = (CaptureView) findViewById(R.id.captureView);

        initData();
    }

    private void initData() {
        context = this;
        initCameraManager();
        initSVCallBack();
    }

    /**
     * 初始化CameraManager
     */
    private void initCameraManager() {
        cameraManager = new CameraManager(this);
        cameraManager.setFrameShotListener(new PreviewFrameShotListener() {
            @Override
            public void onPreviewFrame(byte[] data, Size frameSize) {
                if (decodeThread != null) {
                    decodeThread.cancel();
                }
                if (previewFrameRect == null) {
                    previewFrameRect = cameraManager.getPreviewFrameRect(captureView.getFrameRect());
                }
                PlanarYUVLuminanceSource luminanceSource = new PlanarYUVLuminanceSource(data, frameSize, previewFrameRect);
                initDecodeThread(luminanceSource);
            }
        });
    }

    private void initSVCallBack() {
        svPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                cameraManager.initCamera(holder);
                if (!cameraManager.isCameraAvailable()) {
                    Toast.makeText(context, "相机不可用", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                cameraManager.startPreview();
                if (!isDecoding) {
                    cameraManager.requestPreviewFrameShot();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraManager.stopPreview();
                if (decodeThread != null) {
                    decodeThread.cancel();
                }
                cameraManager.release();
            }
        });
    }

    /**
     * 初始化解码
     *
     * @param luminanceSource
     */
    private void initDecodeThread(PlanarYUVLuminanceSource luminanceSource) {
        decodeThread = new DecodeThread(luminanceSource, new DecodeListener() {
            @Override
            public void onDecodeSuccess(Result result, LuminanceSource source, Bitmap bitmap) {
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(200);
                isDecoding = false;
                if (bitmap.getWidth() > 100 || bitmap.getHeight() > 100) {
                    Matrix matrix = new Matrix();
                    matrix.postScale(100f / bitmap.getWidth(), 100f / bitmap.getHeight());
                    Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    bitmap.recycle();
                    bitmap = resizeBmp;
                }
                Intent intent = getIntent();
                intent.putExtra(TEXT_RESULR, result.getText());
                intent.putExtra(BITMAP_RESULR, bitmap);
                setResult(SCAN_RESULT_CODE, intent);
                finish();
            }

            @Override
            public void onDecodeFailed(LuminanceSource source) {
                if (source instanceof RGBLuminanceSource) {
                    Toast.makeText(CaptureActivity.this, "未找到二维码", Toast.LENGTH_SHORT).show();
                }
                isDecoding = false;
                cameraManager.requestPreviewFrameShot();
            }

            @Override
            public void foundPossibleResultPoint(ResultPoint resultPoint) {
                captureView.addPossibleResultPoint(resultPoint);
            }
        });
        isDecoding = true;
        decodeThread.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
