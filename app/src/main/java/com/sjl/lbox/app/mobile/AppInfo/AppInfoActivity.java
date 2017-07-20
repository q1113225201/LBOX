package com.sjl.lbox.app.mobile.AppInfo;

import android.content.pm.ApplicationInfo;
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
import com.sjl.lbox.config.CacheConfig;
import com.sjl.lbox.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * App信息
 * https://github.com/maoruibin/AppPlus/
 *
 * @author SJL
 * @date 2017/7/20
 */
public class AppInfoActivity extends BaseActivity {
    private EditText etKeyword;
    private RecyclerView rvApp;
    private CommonRVAdapter<AppInfo> adapter;
    private List<AppInfo> wholeList;
    private List<AppInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        initView();
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
        getAppList();
    }

    private void getAppList() {
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
                    appInfo.setSystemApp(isSystemApp(packageInfo.applicationInfo));
                    appInfo.setSrcPath(packageInfo.applicationInfo.sourceDir);
                    appInfo.setTotalSpace(new File(appInfo.getSrcPath()).getTotalSpace());
                    wholeList.add(appInfo);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showList("");
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

    /**
     * 判断是否是系统app
     *
     * @param info
     * @return
     */
    private Boolean isSystemApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            //原来是系统应用，用户手动升级
            return false;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            //用户自己安装的应用程序
            return false;
        }
        return true;
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
                        try {
                            FileUtil.copyFile(item.getSrcPath(), CacheConfig.PATH+"/"+item.getPackageName()+".apk");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        rvApp.setAdapter(adapter);
        rvApp.setLayoutManager(new LinearLayoutManager(this));
    }
}
