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

public class CoordinatorLayoutActivity2 extends BaseActivity {
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout2);

        initView();
    }

    private void initView() {

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
