package com.sjl.lbox.app.ui.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sjl.lbox.R;
import com.sjl.lbox.adapter.ModuleAdapter;
import com.sjl.lbox.app.ui.view.CardView.CardViewActivity;
import com.sjl.lbox.app.ui.view.ViewFlipper.ViewFlipperActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends BaseActivity {
    RecyclerView recyclerView;
    private List<Module> list = new ArrayList<>();
    ModuleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        initView();
        initData();
    }

    private void initData() {
        list.add(new Module("ViewFlipper", ViewFlipperActivity.class));
        list.add(new Module("CardView", CardViewActivity.class));
        adapter = new ModuleAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
    }
}
