package com.sjl.lbox.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.Notification.NotificationActivity;
import com.sjl.lbox.app.ui.CustomView.CustomViewActivity;
import com.sjl.lbox.app.ui.FloatWindow.FloatWindowActivity;
import com.sjl.lbox.app.ui.animate.AnimateActivity;
import com.sjl.lbox.app.ui.slideMenu.SlideMenuActivity;
import com.sjl.lbox.app.ui.viewpager.ViewPagerActivity;
import com.sjl.lbox.app.ui.webview.WebViewActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * UI
 *
 * @author SJL
 * @date 2017/1/12
 */
public class UIActivity extends BaseActivity {
    private ListView lv;
    private ArrayAdapter adapter;
    private List<Module> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
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
        list.add(new Module("自定义View", CustomViewActivity.class));
        list.add(new Module("悬浮窗", FloatWindowActivity.class));
        list.add(new Module("侧滑菜单", SlideMenuActivity.class));
        list.add(new Module("webview", WebViewActivity.class));
        list.add(new Module("ViewPager", ViewPagerActivity.class));
        list.add(new Module("通知", NotificationActivity.class));
    }
}
