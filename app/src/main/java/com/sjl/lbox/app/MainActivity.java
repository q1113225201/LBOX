package com.sjl.lbox.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.DesignPattern.DesignPatternActivity;
import com.sjl.lbox.app.component.ComponentActivity;
import com.sjl.lbox.app.lib.LibActivity;
import com.sjl.lbox.app.lib.RxJava.RxBus;
import com.sjl.lbox.app.mobile.MobileActivity;
import com.sjl.lbox.app.mode.ModeActivity;
import com.sjl.lbox.app.network.NetworkActivity;
import com.sjl.lbox.app.ui.UIActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;
import com.sjl.lbox.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

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
        list.add(new Module("UI效果", UIActivity.class));
        list.add(new Module("开发模式", ModeActivity.class));
        list.add(new Module("设计模式", DesignPatternActivity.class));
        list.add(new Module("网络", NetworkActivity.class));
        list.add(new Module("组件", ComponentActivity.class));
        list.add(new Module("手机功能", MobileActivity.class));
        list.add(new Module("三方", LibActivity.class));
        LogUtil.delFile();
    }
}
