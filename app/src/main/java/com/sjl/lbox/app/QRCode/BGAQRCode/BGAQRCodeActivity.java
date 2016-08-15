package com.sjl.lbox.app.QRCode.BGAQRCode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.BitmapUtil;
import com.sjl.lbox.util.ImageUtil;
import com.sjl.lbox.util.PermisstionsUtil;
import com.sjl.lbox.util.ToastUtil;

import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
/**
 * BGAQRCode扫码、编码
 *
 * @author SJL
 * @date 2016/8/15 22:42
 */
public class BGAQRCodeActivity extends BaseActivity implements View.OnClickListener {
    public static int SCAN_CODE = 1;
    public static int SCAN_RESULT_CODE = 2;
    public static int ENCODE = 3;
    public static int DECODE = 4;

    private Button btnEncode;
    private Button btnDecode;
    private Button btnScan;
    private ImageView ivEncodeResult;
    private EditText etText;
    private TextView tvDecodeResult;
    private TextView tvScanResult;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (ENCODE == msg.what) {
                ivEncodeResult.setImageBitmap((Bitmap) msg.obj);
            } else if (DECODE == msg.what) {
                tvDecodeResult.setText((CharSequence) msg.obj);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgaqrcode);

        initView();
    }

    private void initView() {
        btnEncode = (Button) findViewById(R.id.btnEncode);
        btnDecode = (Button) findViewById(R.id.btnDecode);
        btnScan = (Button) findViewById(R.id.btnScan);
        etText = (EditText) findViewById(R.id.etText);
        ivEncodeResult = (ImageView) findViewById(R.id.ivEncodeResult);
        tvDecodeResult = (TextView) findViewById(R.id.tvDecodeResult);
        tvScanResult = (TextView) findViewById(R.id.tvScanResult);

        btnEncode.setOnClickListener(this);
        btnDecode.setOnClickListener(this);
        btnScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEncode:
                encode(etText.getText().toString());
                break;
            case R.id.btnDecode:
                decode(ImageUtil.drawableToBitmap(ivEncodeResult.getDrawable()));
                break;
            case R.id.btnScan:
                scan();
                break;
        }
    }

    private void decode(final Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = DECODE;
                msg.obj = QRCodeDecoder.syncDecodeQRCode(bitmap);
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void encode(final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = ENCODE;
                msg.obj = QRCodeEncoder.syncEncodeQRCode(str, 200);
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void scan() {
        PermisstionsUtil.checkSelfPermission(mContext, PermisstionsUtil.CAMERA, PermisstionsUtil.CAMERA_CODE, "拍照权限", new PermisstionsUtil.PermissionResult() {
            @Override
            public void granted(int requestCode) {
                Intent intent = new Intent(mContext, ScanActivity.class);
                startActivityForResult(intent, SCAN_CODE);
            }

            @Override
            public void denied(int requestCode) {
                ToastUtil.showToast(mContext, "无拍照权限");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_CODE && resultCode == SCAN_RESULT_CODE) {
            tvScanResult.setText(data.getStringExtra("result"));
        }
    }
}
