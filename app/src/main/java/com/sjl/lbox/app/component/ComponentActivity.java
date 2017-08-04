package com.sjl.lbox.app.component;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.component.AccessibilityService.AccessibilityServiceActivity;
import com.sjl.lbox.app.component.CorrdinatorLayout.CoordinatorLayoutActivity;
import com.sjl.lbox.app.component.FloatWindow.FloatWindowActivity;
import com.sjl.lbox.app.component.Notification.NotificationActivity;
import com.sjl.lbox.app.component.broadcastreceiver.BroadcastActivity;
import com.sjl.lbox.app.component.intentService.IntentServiceActivity;
import com.sjl.lbox.app.component.slideMenu.SlideMenuActivity;
import com.sjl.lbox.app.component.viewpager.ViewPagerActivity;
import com.sjl.lbox.app.component.webview.WebViewActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * 组件
 *
 * @author SJL
 * @date 2017/1/13
 */
public class ComponentActivity extends BaseActivity {
    private ListView lv;
    private ArrayAdapter adapter;
    private List<Module> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);
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
        list.add(new Module("IntentService和LocalBroadcastManager", IntentServiceActivity.class));
        list.add(new Module("无障碍服务", AccessibilityServiceActivity.class));
        list.add(new Module("通知", NotificationActivity.class));
        list.add(new Module("广播", BroadcastActivity.class));
        list.add(new Module("webview", WebViewActivity.class));
        list.add(new Module("悬浮窗", FloatWindowActivity.class));
        list.add(new Module("ViewPager", ViewPagerActivity.class));
        list.add(new Module("侧滑菜单", SlideMenuActivity.class));
        list.add(new Module("CoordinatorLayout效果", CoordinatorLayoutActivity.class));
    }
}
