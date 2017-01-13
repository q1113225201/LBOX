package com.sjl.lbox.app.ui.CustomView.repeat.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.repeat.recyclerview.adapter.RVAdapter;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView
 *
 * @author SJL
 * @date 2016/12/25 15:44
 */
public class RecyclerViewActivity extends BaseActivity implements View.OnClickListener {

    private Button btnListView;
    private Button btnGridView;
    private Button btnAdd;
    private Button btnDelete;
    private RecyclerView recyclerView;
    private RVAdapter adapter;

    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initView();
    }

    private void initView() {
        btnListView = (Button) findViewById(R.id.btnListView);
        btnGridView = (Button) findViewById(R.id.btnGridView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        btnListView.setOnClickListener(this);
        btnGridView.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        initData();
        adapter = new RVAdapter(mContext, list);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showToast(mContext, adapter.getItem(position));
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        list = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            list.add("item" + i);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnListView:
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                break;
            case R.id.btnGridView:
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                break;
            case R.id.btnAdd:
                adapter.addItem(2, "add item");
                break;
            case R.id.btnDelete:
                adapter.removeItem(0);
                break;
        }
    }
}
