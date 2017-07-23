package com.sjl.lbox.app.mobile.AppInfo;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.mobile.AppInfo.bean.AppInfo;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.AppUtil;
import com.sjl.lbox.util.FileUtil;
import com.sjl.lbox.util.LogUtil;
import com.sjl.lbox.util.StringUtil;
import com.sjl.lbox.util.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * App信息
 * packageManager.getPackageInfo(packageName,
 * PackageManager.GET_META_DATA|PackageManager.GET_ACTIVITIES|PackageManager.GET_RECEIVERS|PackageManager.GET_SERVICES|PackageManager.GET_PERMISSIONS)
 * https://github.com/maoruibin/AppPlus/
 *
 * @author SJL
 * @date 2017/7/20
 */
public class AppInfoActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "AppInfoActivity";
    private ImageView ivIcon;
    private TextView tvName;
    private TextView tvVersion;
    private TextView tvSignature;
    private Button btnCopy;
    private TextView tvDetail;
    private TextView tvShareApk;
    private TextView tvGetApk;
    private TextView tvUninstall;
    private TextView tvPermissions;
    private TextView tvActivitys;
    private TextView tvReceivers;
    private TextView tvServices;
    private TextView tvProviders;
    private AppInfo appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        tvName = (TextView) findViewById(R.id.tvName);
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvSignature = (TextView) findViewById(R.id.tvSignature);
        btnCopy = (Button) findViewById(R.id.btnCopy);
        tvDetail = (TextView) findViewById(R.id.tvDetail);
        tvDetail.setOnClickListener(this);
        tvShareApk = (TextView) findViewById(R.id.tvShareApk);
        tvShareApk.setOnClickListener(this);
        tvGetApk = (TextView) findViewById(R.id.tvGetApk);
        tvGetApk.setOnClickListener(this);
        tvUninstall = (TextView) findViewById(R.id.tvUninstall);
        tvUninstall.setOnClickListener(this);
        tvPermissions = (TextView) findViewById(R.id.tvPermissions);
        tvActivitys = (TextView) findViewById(R.id.tvActivitys);
        tvReceivers = (TextView) findViewById(R.id.tvReceivers);
        tvServices = (TextView) findViewById(R.id.tvServices);
        tvProviders = (TextView) findViewById(R.id.tvProviders);

        btnCopy.setOnClickListener(this);
    }

    private void initData() {
        String packageName = getIntent().getStringExtra("packageName");
        appInfo = getAppInfo(packageName);
        if (appInfo == null) {
            finish();
            return;
        }
        ivIcon.setImageDrawable(appInfo.getIcon());
        tvName.setText(String.format("%s%s", appInfo.getName(), appInfo.getSystemApp() ? "(系统应用)" : ""));
        tvVersion.setText(String.format("%s(%s)", appInfo.getVersion(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(appInfo.getLastModifyTime()))));
        tvSignature.setText(parseSignature(appInfo.getSignatures()[0].toByteArray()));
        LogUtil.i(TAG, "Permissions");
        for (String permissionInfo : appInfo.getPermissions()) {
            tvPermissions.append(permissionInfo + "\n");
            LogUtil.i(TAG, permissionInfo.toString());
        }
        LogUtil.i(TAG, "Activitys");
        for (ActivityInfo activityInfo : appInfo.getActivitys()) {
            tvActivitys.append(activityInfo.name + "\n");
            LogUtil.i(TAG, activityInfo.toString());
        }
        LogUtil.i(TAG, "Receivers");
        for (ActivityInfo activityInfo : appInfo.getReceivers()) {
            tvReceivers.append(activityInfo.name + "\n");
            LogUtil.i(TAG, activityInfo.toString());
        }
        LogUtil.i(TAG, "Services");
        for (ServiceInfo service : appInfo.getServices()) {
            tvServices.append(service.name + "\n");
            LogUtil.i(TAG, service.toString());
        }
        LogUtil.i(TAG, "tvProviders");
        for (ProviderInfo providerInfo : appInfo.getProviders()) {
            tvProviders.append(providerInfo.name + "\n");
            LogUtil.i(TAG, providerInfo.toString());
        }
    }

    /**
     * 获取App信息
     *
     * @param packageName
     * @return
     */
    private AppInfo getAppInfo(String packageName) {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName,
                    PackageManager.GET_ACTIVITIES | PackageManager.GET_RECEIVERS | PackageManager.GET_SERVICES | PackageManager.GET_PERMISSIONS | PackageManager.GET_SIGNATURES);
            AppInfo appInfo = new AppInfo();
            appInfo.setIcon(packageManager.getApplicationIcon(packageInfo.applicationInfo));
            appInfo.setName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
            appInfo.setPackageName(packageInfo.packageName);
            appInfo.setVersion(packageInfo.versionName);
            appInfo.setLastModifyTime(packageInfo.lastUpdateTime);
            appInfo.setSystemApp(AppUtil.isSystemApp(packageInfo.applicationInfo));
            appInfo.setSrcPath(packageInfo.applicationInfo.sourceDir);
            appInfo.setActivitys(packageInfo.activities == null ? new ActivityInfo[0] : packageInfo.activities);
            appInfo.setReceivers(packageInfo.receivers == null ? new ActivityInfo[0] : packageInfo.receivers);
            appInfo.setServices(packageInfo.services == null ? new ServiceInfo[0] : packageInfo.services);
            appInfo.setProviders(packageInfo.providers == null ? new ProviderInfo[0] : packageInfo.providers);
            appInfo.setPermissions(packageInfo.requestedPermissions == null ? new String[0] : packageInfo.requestedPermissions);
            appInfo.setSignatures(packageInfo.signatures);
            return appInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCopy:
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText("text", tvSignature.getText().toString()));
                ToastUtil.showToast(mContext, "签名已复制");
                break;
            case R.id.tvDetail:
                openDetail(appInfo.getPackageName());
                break;
            case R.id.tvShareApk:
                shareApk(new File(appInfo.getSrcPath()), appInfo.getName());
                break;
            case R.id.tvGetApk:
                try {
                    String path = Environment.getExternalStorageDirectory() + "/appinfo/" + appInfo.getPackageName() + ".apk";
                    FileUtil.copyFile(appInfo.getSrcPath(), path);
                    ToastUtil.showToast(mContext, "apk存放在" + path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvUninstall:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("是否卸载该App?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri packageURI = Uri.parse("package:" + appInfo.getPackageName());
                                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                                startActivity(uninstallIntent);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
    }

    private void shareApk(File file, String title) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType("application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, title));
    }

    /**
     * 查看详情
     *
     * @param packageName
     */
    private void openDetail(String packageName) {
        Intent intent = new Intent();
        final int apiLevel = Build.VERSION.SDK_INT;
        if (apiLevel >= 9) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Uri uri = Uri.fromParts("package", packageName, null);
            intent.setData(uri);
        } else {
            final String appPkgName = (apiLevel == 8 ? "pkg" : "com.android.settings.ApplicationPkgName");
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(appPkgName, packageName);
        }
        startActivity(intent);
    }

    /**
     * 获取App签名
     *
     * @param pkg 包名
     * @return
     */
    public String getSignature(String pkg) {
        if (StringUtil.isEmpty(pkg)) {
            ToastUtil.showToast(mContext, "包名不能为空");
            return null;
        }
        try {
            /** 通过包管理器获得指定包名包含签名的包信息 **/
            PackageInfo packageInfo = getPackageManager().getPackageInfo(pkg, PackageManager.GET_SIGNATURES);
            /******* 通过返回的包信息获得签名数组 *******/
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            return parseSignature(sign.toByteArray());
        } catch (Exception e) {
            LogUtil.i(TAG, "getSignature:" + e.getMessage());
        }
        return null;
    }

    public String parseSignature(byte[] signature) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(signature);
            byte[] digest = md.digest();
            return toHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            LogUtil.i(TAG, "parseSignature:" + e.getMessage());
        }
        return null;
    }

    private String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();
        int len = block.length;
        for (byte aBlock : block) {
            byte2hex(aBlock, buf);
        }
        return buf.toString();
    }

    private void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }
}
