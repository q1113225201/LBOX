package com.sjl.lbox.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.FloatWindow.FloatWindowActivity;
import com.sjl.lbox.app.Notification.NotificationActivity;
import com.sjl.lbox.app.QRCode.QRCodeActivity;
import com.sjl.lbox.app.animate.AnimateActivity;
import com.sjl.lbox.app.compass.CompassActivity;
import com.sjl.lbox.app.contact.ContactActivity;
import com.sjl.lbox.app.gesture.GestureActivity;
import com.sjl.lbox.app.http.HttpActivity;
import com.sjl.lbox.app.image.ImageActivity;
import com.sjl.lbox.app.music.MusicActivity;
import com.sjl.lbox.app.network.monitor.NetworkMonitorActivity;
import com.sjl.lbox.app.pedometer.PedometerActivity;
import com.sjl.lbox.app.progress.ProgressActivity;
import com.sjl.lbox.app.signature.SignatureActivity;
import com.sjl.lbox.app.viewpager.ViewPagerActivity;
import com.sjl.lbox.app.webview.WebViewActivity;
import com.sjl.lbox.app.zip.ZipActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity
 *
 * @author SJL
 * @date 2016/8/6 14:19
 */
public class MainActivity extends BaseActivity {
    private ListView lv;
    private ArrayAdapter adapter;
    private List<Module> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        list.add(new Module("动画", AnimateActivity.class));
        list.add(new Module("悬浮窗", FloatWindowActivity.class));
        list.add(new Module("图片选择", ImageActivity.class));
        list.add(new Module("音乐选择", MusicActivity.class));
        list.add(new Module("联系人列表", ContactActivity.class));
        list.add(new Module("二维码扫描", QRCodeActivity.class));
        list.add(new Module("App签名获取", SignatureActivity.class));
        list.add(new Module("进度条", ProgressActivity.class));
        list.add(new Module("通知", NotificationActivity.class));
        list.add(new Module("webview", WebViewActivity.class));
        list.add(new Module("网络信息及网络监听", NetworkMonitorActivity.class));
        list.add(new Module("HTTP", HttpActivity.class));
        list.add(new Module("Zip", ZipActivity.class));
        list.add(new Module("指南针", CompassActivity.class));
        list.add(new Module("计步器", PedometerActivity.class));
        list.add(new Module("手势密码", GestureActivity.class));
        list.add(new Module("ViewPager", ViewPagerActivity.class));
    }
}
