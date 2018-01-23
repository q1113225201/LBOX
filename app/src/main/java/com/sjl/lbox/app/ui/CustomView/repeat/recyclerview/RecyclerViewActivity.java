package com.sjl.lbox.app.ui.CustomView.repeat.recyclerview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.repeat.recyclerview.adapter.RVAdapter;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.LogUtil;
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
    private static final String TAG = "RecyclerViewActivity";
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

            @Override
            public void onItemLongClick(RVAdapter.ViewHolder viewHolder, int position) {
                itemTouchHelper.startDrag(viewHolder);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initTouchHelper();
    }

    private ItemTouchHelper itemTouchHelper;

    /**
     * 初始化拖动
     */
    private void initTouchHelper() {
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    return makeMovementFlags(dragFlags, 0);
                } else {
                    int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    return makeMovementFlags(dragFlags, 0);
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //获取当前要拖动的viewHolder的position
                int fromPosition = viewHolder.getAdapterPosition();
                //获取拖动到新位置的position
                int toPosition = target.getAdapterPosition();
                //交换位置,参见Collections#swap方法
                list.set(toPosition,list.set(fromPosition,list.get(toPosition)));
                adapter.notifyItemMoved(fromPosition,toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //滑动
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if(actionState!=ItemTouchHelper.ACTION_STATE_IDLE) {
                    //选中可拖拉的时候背景高亮
                    viewHolder.itemView.setBackgroundColor(Color.DKGRAY);
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                //松开的时候背景还原
                viewHolder.itemView.setBackgroundColor(0);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initData() {
        list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
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
