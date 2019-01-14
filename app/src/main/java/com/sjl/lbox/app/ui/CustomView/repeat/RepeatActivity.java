package com.sjl.lbox.app.ui.CustomView.repeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.repeat.SwipeItemLayout.SwipeItemLayoutActivity;
import com.sjl.lbox.app.ui.CustomView.repeat.listview.ListViewActivity;
import com.sjl.lbox.app.ui.CustomView.repeat.recyclerview.RecyclerViewActivity;
import com.sjl.lbox.app.ui.CustomView.repeat.recyclerview.RecyclerViewGalleryActivity;
import com.sjl.lbox.app.ui.CustomView.repeat.recyclerview.RecyclerViewItemDecorationActivity;
import com.sjl.lbox.app.ui.CustomView.repeat.recyclerview.RecyclerViewStickyHeaderActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * 重复样式，列表、网状布局
 *
 * @author SJL
 * @date 2016/12/18 21:25
 */
public class RepeatActivity extends BaseActivity {
    private ListView lv;
    private ArrayAdapter adapter;
    private List<Module> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);

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
        list.add(new Module("ListView", ListViewActivity.class));
        list.add(new Module("RecyclerView 可拖拉排序", RecyclerViewActivity.class));
        list.add(new Module("RecyclerView Gallery", RecyclerViewGalleryActivity.class));
        list.add(new Module("RecyclerView ItemDecoration 分割线", RecyclerViewItemDecorationActivity.class));
        list.add(new Module("RecyclerView StickyHeader 悬停头部", RecyclerViewStickyHeaderActivity.class));
        list.add(new Module("SwipeItemLayout", SwipeItemLayoutActivity.class));
    }
}
