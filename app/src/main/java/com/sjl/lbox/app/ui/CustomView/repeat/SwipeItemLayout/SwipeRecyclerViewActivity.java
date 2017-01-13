package com.sjl.lbox.app.ui.CustomView.repeat.SwipeItemLayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.repeat.SwipeItemLayout.adapter.RVAdapter;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 滑动Item在RecyclerView中演示
 *
 * @author SJL
 * @date 2016/12/29
 */
public class SwipeRecyclerViewActivity extends BaseActivity implements View.OnClickListener {
    private Button btnLV;
    private Button btnGV;
    private Button btnAdd;
    private Button btnDelete;
    private SwipeRefreshLayout refresh;
    private RecyclerView recyclerView;

    private RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_recycler_view);

        initView();
    }

    private void initView() {
        btnLV = (Button) findViewById(R.id.btnLV);
        btnGV = (Button) findViewById(R.id.btnGV);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        initRecyclerView();

        btnLV.setOnClickListener(this);
        btnGV.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new RVAdapter(mContext, getList("item"));
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showToast(mContext, adapter.getItem(position));
            }

            @Override
            public void onItemDelete(View view, int position) {
                ToastUtil.showToast(mContext, adapter.getItem(position) + "删除成功");
                adapter.removeItem(position);
            }

            @Override
            public void onItemTop(View view, int position) {
                ToastUtil.showToast(mContext, adapter.getItem(position) + "置顶");
                adapter.topItem(position);
                recyclerView.scrollToPosition(0);
            }
        });
        recyclerView.setAdapter(adapter);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.refresh(getList("refresh"));
                        refresh.setRefreshing(false);
                    }
                },2000);
            }
        });
    }

    private List<String> getList(String str) {
        List<String> tmp = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            tmp.add(str + i);
        }
        return tmp;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLV:
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                break;
            case R.id.btnGV:
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                break;
            case R.id.btnAdd:
                adapter.addItem(0,"add");
                break;
            case R.id.btnDelete:
                adapter.removeItem(0);
                break;
        }
    }
}
