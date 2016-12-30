package com.sjl.lbox.app.repeat.SwipeItemLayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.repeat.SwipeItemLayout.adapter.LVAdapter;
import com.sjl.lbox.app.repeat.SwipeItemLayout.view.SwipeItemLayout;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 滑动Item在ListView中演示
 *
 * @author SJL
 * @date 2016/12/29
 */
public class SwipeListViewActivity extends BaseActivity implements View.OnClickListener {

    private Button btnAdd;
    private Button btnDelete;
    private SwipeRefreshLayout refresh;
    private ListView lv;
    private LVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_list_view);

        initView();
    }

    private void initView() {
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        lv = (ListView) findViewById(R.id.lv);

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        initListView();
    }

    private void initListView() {
        adapter = new LVAdapter(mContext, getList("item"));
        lv.setAdapter(adapter);
        adapter.setOnItemClickListener(new LVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showToast(mContext, adapter.getItem(position).toString());
            }

            @Override
            public void onItemDelete(View view, int position) {
                ((SwipeItemLayout)view).closeSwipe();
                ToastUtil.showToast(mContext, adapter.getItem(position) + "删除成功");
                adapter.removeItem(position);
            }
        });

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
        switch (v.getId()){
            case R.id.btnAdd:
                adapter.addItem(0,"add");
                break;
            case R.id.btnDelete:
                adapter.removeItem(0);
                break;
        }
    }
}
