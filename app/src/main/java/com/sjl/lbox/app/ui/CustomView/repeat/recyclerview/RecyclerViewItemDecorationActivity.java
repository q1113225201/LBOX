package com.sjl.lbox.app.ui.CustomView.repeat.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.repeat.recyclerview.adapter.RVAdapter;
import com.sjl.lbox.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 分割线
 *
 * @author 林zero
 * @date 2018/4/11
 */
public class RecyclerViewItemDecorationActivity extends BaseActivity {
    private RVAdapter adapter;
    private List<String> list = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_item_decoration);

        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        for (int i=0;i<20;i++) {
            list.add("item"+i);
        }
        adapter = new RVAdapter(this,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new CustomItemDecoration(this));
    }
}
