package com.sjl.lbox.app.ui.CustomView.Turntable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.Turntable.view.TurntableView;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ToastUtil;

import java.util.ArrayList;

/**
 * 转盘
 *
 * @author SJL
 * @date 2017/9/14
 */
public class TurntableActivity extends BaseActivity {
    private static final String TAG = "TurntableActivity";
    private TurntableView turntableView;
    private EditText etCount;
    private EditText etAngle;
    private EditText etSector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turntable);

        initView();
    }

    private void initView() {
        turntableView = findViewById(R.id.turntableView);
        etCount = findViewById(R.id.etCount);
        etAngle = findViewById(R.id.etAngle);
        etSector = findViewById(R.id.etSector);
        findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turntableView.reset();
            }
        });
        findViewById(R.id.ivRotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turntableView.startRotate();
            }
        });
        findViewById(R.id.btnRedraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSectorData();
            }
        });
        findViewById(R.id.btnStopAngle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(mContext, "停止扇区：" + turntableView.stopRotateAngle(Float.valueOf(etAngle.getText().toString())));
            }
        });
        findViewById(R.id.btnStopSector).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(mContext, "停止角度：" + turntableView.stopRotateSector(Integer.valueOf(etSector.getText().toString()), true));
            }
        });
        setSectorData();
    }

    private void setSectorData() {
        ArrayList<Integer> colorList = new ArrayList<>();
        colorList.add(Color.YELLOW);
        colorList.add(Color.GREEN);
        ArrayList<Bitmap> bitmapList = new ArrayList<>();
        bitmapList.add(BitmapFactory.decodeResource(getResources(),R.mipmap.iphone));
        bitmapList.add(BitmapFactory.decodeResource(getResources(),R.mipmap.meizu));
        bitmapList.add(BitmapFactory.decodeResource(getResources(),R.mipmap.huawei));
        turntableView.setSectorData(Integer.valueOf(etCount.getText().toString()), colorList, bitmapList);
    }
}
