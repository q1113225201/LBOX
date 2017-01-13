package com.sjl.lbox.app.mobile.QRCode.BGAQRCode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ToastUtil;

import cn.bingoogolapple.qrcode.core.QRCodeView;
/**
 * 扫码
 *
 * @author SJL
 * @date 2016/8/15 22:42
 */
public class ScanActivity extends BaseActivity {

    private QRCodeView qrCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        initView();
    }

    private void initView() {
        qrCodeView= (QRCodeView) findViewById(R.id.zxingview);
        qrCodeView.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                vibrate();
                qrCodeView.stopSpot();
                Intent intent=getIntent();
                intent.putExtra("result",result);
                setResult(BGAQRCodeActivity.SCAN_RESULT_CODE,intent);
                finish();
            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                ToastUtil.showToast(mContext,"打开相机出错");
            }
        });
        initData();
    }

    private void initData() {
        qrCodeView.startSpot();
    }
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
    @Override
    protected void onStop() {
        qrCodeView.stopSpot();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        qrCodeView.onDestroy();
        super.onDestroy();
    }
}
