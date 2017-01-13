package com.sjl.lbox.app.mobile.QRCode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.mobile.QRCode.BGAQRCode.BGAQRCodeActivity;
import com.sjl.lbox.app.mobile.QRCode.MyQRCode.MyQRCodeScanActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;

import java.util.ArrayList;
import java.util.List;
/**
 * 二维码扫描
 *
 * @author SJL
 * @date 2016/8/15 22:46
 */
public class QRCodeActivity extends BaseActivity {
    private ListView lv;
    private ArrayAdapter adapter;
    private List<Module> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        initView();
    }

    private void initView() {
        initData();
        lv= (ListView) findViewById(R.id.lv);
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
        list.add(new Module("BGAQRCode", BGAQRCodeActivity.class));
        list.add(new Module("MyQRCode", MyQRCodeScanActivity.class));
    }
}
