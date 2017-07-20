package com.sjl.lbox.app.mobile.AppInfo;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.sjl.lbox.R;
import com.sjl.lbox.app.mobile.AppInfo.bean.AppInfo;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.base.adapter.CommonRVAdapter;

import java.util.List;

/**
 * App信息
 * https://github.com/maoruibin/AppPlus/
 * @author SJL
 * @date 2017/7/20
 */
public class AppInfoActivity extends BaseActivity {
    private EditText etKeyword;
    private RecyclerView rvApp;
    private CommonRVAdapter<AppInfo> adapter;
    private List<AppInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        initView();
    }

    private void initView() {
        etKeyword = (EditText) findViewById(R.id.etKeyword);
        rvApp = (RecyclerView) findViewById(R.id.rvApp);

        initAdapter();
    }

    private void initAdapter() {
        adapter = new CommonRVAdapter<AppInfo>(mContext,list,R.layout.item_appinfo,R.layout.item_empty) {
            @Override
            protected void onBindNullViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, AppInfo item, List<AppInfo> list) {

            }

            @Override
            protected void onBindViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, AppInfo item, List<AppInfo> list) {

            }
        };
    }
}
