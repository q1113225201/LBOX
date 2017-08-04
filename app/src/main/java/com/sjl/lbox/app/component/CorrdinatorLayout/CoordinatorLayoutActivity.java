package com.sjl.lbox.app.component.CorrdinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.base.adapter.CommonRVAdapter;
import com.sjl.lbox.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * CoordinatorLayout效果
 * AppBarLayout的子布局有5中滚动标识（属性app:layout_scrollFlags或代码设置setScrollFlags()）
 * scroll：所有想滚出屏幕的view都需要设置；
 * enterAlways：任意向下的滚动都会导致该view变为可见；
 * enterAlwaysCollapsed：假设定义了最小高度minHeight，同时设置了enterAlways，view将在到达最小高度的时候显示；
 * exitUntilCollapsed：当定义了最小高度minHeight，布局会在滚动到达最小高度的时候折叠；
 * snap：滚动事件结束后，如果视图部分可见，将完全展开或收缩；
 * @author SJL
 * @date 2017/8/4
 */
public class CoordinatorLayoutActivity extends BaseActivity {
    private FloatingActionButton fab;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);

        initView();
    }

    private void initView() {
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "你点击了添加按钮", Snackbar.LENGTH_LONG)
                        .setAction("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtil.showToast(mContext, "你点击了取消");
                            }
                        })
                        .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                fab.setSelected(false);
                            }

                            @Override
                            public void onShown(Snackbar transientBottomBar) {
                                super.onShown(transientBottomBar);
                                fab.setSelected(true);
                            }
                        })
                        .show();
            }
        });
        initRV();
    }

    private void initRV() {
        rv = findViewById(R.id.rv);
        rv.setAdapter(new CommonRVAdapter<String>(mContext,getDataList(),R.layout.item_single_text,R.layout.item_empty) {
            @Override
            protected void onBindNullViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, String item, List<String> list) {

            }

            @Override
            protected void onBindViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, final String item, List<String> list) {
                ((TextView)viewHolder.findViewById(R.id.single_text)).setText(item);
                viewHolder.findViewById(R.id.single_text).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext,CoordinatorLayoutActivity2.class));
                    }
                });
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    public List<String> getDataList(){
        List<String> list = new ArrayList<>();
        for (int i=0;i<30;i++){
            list.add("item"+i);
        }
        return list;
    }
}
