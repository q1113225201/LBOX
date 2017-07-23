package com.sjl.lbox.app.mobile.AppInfo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.mobile.AppInfo.bean.AppInfo;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.base.adapter.CommonRVAdapter;
import com.sjl.lbox.util.AppUtil;
import com.sjl.lbox.util.DialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * App列表
 * http://blog.csdn.net/q1113225201/article/details/75892115
 *
 * @author SJL
 * @date 2017/7/21
 */
public class AppInfoListActivity extends BaseActivity {

    private EditText etKeyword;
    private RecyclerView rvApp;
    private CommonRVAdapter<AppInfo> adapter;
    private List<AppInfo> wholeList;
    private List<AppInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info_list);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAppList();
    }

    private void initView() {
        rvApp = (RecyclerView) findViewById(R.id.rvApp);
        etKeyword = (EditText) findViewById(R.id.etKeyword);
        etKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        initAdapter();
    }

    private void getAppList() {
        DialogUtil.showProgressDialog(mContext,false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                wholeList = new ArrayList<AppInfo>();
                PackageManager packageManager = getPackageManager();
                List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
                for (PackageInfo packageInfo : packageInfoList) {
                    AppInfo appInfo = new AppInfo();
                    appInfo.setIcon(packageManager.getApplicationIcon(packageInfo.applicationInfo));
                    appInfo.setName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
                    appInfo.setPackageName(packageInfo.packageName);
                    appInfo.setVersion(packageInfo.versionName);
                    appInfo.setLastModifyTime(packageInfo.lastUpdateTime);
                    appInfo.setSystemApp(AppUtil.isSystemApp(packageInfo.applicationInfo));
                    appInfo.setSrcPath(packageInfo.applicationInfo.sourceDir);
                    wholeList.add(appInfo);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogUtil.dismissProgressImageDialog();
                        showList(etKeyword.getText().toString());
                    }
                });
            }
        }).start();
    }

    private void showList(String key) {
        list = new ArrayList<>();
        if (TextUtils.isEmpty(key)) {
            list.addAll(wholeList);
        } else {
            for (AppInfo appInfo : wholeList) {
                if (appInfo.getName().contains(key) || appInfo.getPackageName().contains(key)) {
                    list.add(appInfo);
                }
            }
        }
        adapter.flush(list);
    }

    private void initAdapter() {
        adapter = new CommonRVAdapter<AppInfo>(mContext, list, R.layout.item_appinfo, R.layout.item_empty) {
            @Override
            protected void onBindNullViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, AppInfo item, List<AppInfo> list) {

            }

            @Override
            protected void onBindViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, final AppInfo item, List<AppInfo> list) {
                ((ImageView) viewHolder.findViewById(R.id.ivItemIcon)).setImageDrawable(item.getIcon());
                ((TextView) viewHolder.findViewById(R.id.tvItemInfo)).setText(String.format("%s(%s)%s", item.getName(), item.getVersion(), item.getSystemApp() ? "(系统应用)" : ""));
                ((TextView) viewHolder.findViewById(R.id.tvItemPkg)).setText(item.getPackageName());
                viewHolder.findViewById(R.id.llParent).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, AppInfoActivity.class);
                        intent.putExtra("packageName", item.getPackageName());
                        startActivity(intent);
                    }
                });
            }
        };
        rvApp.setAdapter(adapter);
        rvApp.setLayoutManager(new LinearLayoutManager(this));
    }
}
