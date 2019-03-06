package com.sjl.lbox.app.lib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.lib.Charts.MPAndroidChartActivity;
import com.sjl.lbox.app.lib.EventBus.EventBusActivity;
import com.sjl.lbox.app.lib.Glide.GlideActivity;
import com.sjl.lbox.app.lib.Picasso.PicassoActivity;
import com.sjl.lbox.app.lib.Router.RouterActivity;
import com.sjl.lbox.app.lib.RxJava.RxJavaActivity;
import com.sjl.lbox.app.lib.TreeView.TreeViewActivity;
import com.sjl.lbox.app.lib.webview.WebViewActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;

import java.util.ArrayList;
import java.util.List;

public class LibActivity extends BaseActivity {

    private ListView lv;
    private ArrayAdapter adapter;
    private List<Module> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib);

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
        list.add(new Module("EventBus", EventBusActivity.class));
        list.add(new Module("RxJava2.0", RxJavaActivity.class));
        list.add(new Module("Glide", GlideActivity.class));
        list.add(new Module("Picasso", PicassoActivity.class));
        list.add(new Module("MPAndroidChart", MPAndroidChartActivity.class));
        list.add(new Module("Router（路由）", RouterActivity.class));
        list.add(new Module("树形列表", TreeViewActivity.class));
        list.add(new Module("X5WebView", WebViewActivity.class));
    }
}
