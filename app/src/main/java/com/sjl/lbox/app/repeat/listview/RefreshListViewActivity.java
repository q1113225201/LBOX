package com.sjl.lbox.app.repeat.listview;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉刷新列表
 *
 * @author SJL
 * @date 2016/12/18 21:29
 */
public class RefreshListViewActivity extends BaseActivity {

    private SwipeRefreshLayout srl;
    private ListView lv;
    private List<String> list;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list_view);

        initView();
    }

    private void initView() {
        srl = (SwipeRefreshLayout) findViewById(R.id.srl);
        lv = (ListView) findViewById(R.id.lv);

        //初始化ListView
        initData("item");
        adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showToast(mContext, list.get(position));
            }
        });

        //初始化SwipeRefreshLayout
        int[] colorResources = {android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light};
        //设置刷新进度样式
        srl.setColorSchemeResources(colorResources);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //模拟获取网络数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //停止刷新
                        srl.setRefreshing(false);
                        initData("refresh");
                        adapter.notifyDataSetChanged();
                    }
                }, 4000);
            }
        });
    }

    private void initData(String str) {
        if (list == null) {
            list = new ArrayList<String>();
        }
        list.clear();
        for (int i = 0; i < 20; i++) {
            list.add(str + i);
        }
    }
}
