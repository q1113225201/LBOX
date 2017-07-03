package com.sjl.lbox.app.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.mobile.QRCode.QRCodeActivity;
import com.sjl.lbox.app.mobile.compass.CompassActivity;
import com.sjl.lbox.app.mobile.image.ImageActivity;
import com.sjl.lbox.app.mobile.music.MusicActivity;
import com.sjl.lbox.app.mobile.pedometer.PedometerActivity;
import com.sjl.lbox.app.mobile.share.ShareActivity;
import com.sjl.lbox.app.mobile.signature.SignatureActivity;
import com.sjl.lbox.app.mobile.zip.ZipActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * 手机功能
 *
 * @author SJL
 * @date 2017/1/12
 */
public class MobileActivity extends BaseActivity {
    private ListView lv;
    private ArrayAdapter adapter;
    private List<Module> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        initView();
    }

    private void initView() {
        initData();
        lv = (ListView) findViewById(R.id.lv);
        adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, list.get(position).getAct());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        list = new ArrayList<Module>();
        list.add(new Module("指南针", CompassActivity.class));
        list.add(new Module("计步器", PedometerActivity.class));
        list.add(new Module("二维码扫描", QRCodeActivity.class));
        list.add(new Module("图片选择", ImageActivity.class));
        list.add(new Module("音乐选择", MusicActivity.class));
        list.add(new Module("App签名获取", SignatureActivity.class));
        list.add(new Module("Zip", ZipActivity.class));
        list.add(new Module("分享", ShareActivity.class));
    }
}
