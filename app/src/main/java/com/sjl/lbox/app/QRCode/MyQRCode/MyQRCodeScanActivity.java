package com.sjl.lbox.app.QRCode.MyQRCode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.QRCode.MyQRCode.activity.CaptureActivity;
import com.sjl.lbox.base.BaseActivity;
/**
 * 二维码扫描
 *
 * @author SJL
 * @date 2016/8/22 21:20
 */
public class MyQRCodeScanActivity extends BaseActivity {

    private Button btnScan;
    private TextView tvScanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qrcode_scan);

        initView();
    }

    private void initView() {
        btnScan = (Button) findViewById(R.id.btnScan);
        tvScanResult = (TextView) findViewById(R.id.tvScanResult);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent, CaptureActivity.SCAN_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            tvScanResult.setText(data.getStringExtra(CaptureActivity.TEXT_RESULR));
        }
    }
}
