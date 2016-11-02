package com.sjl.lbox.app.signature;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.signature.adapter.AppInfoAdapter;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.AppInfo;
import com.sjl.lbox.util.DialogUtil;
import com.sjl.lbox.util.LogUtil;
import com.sjl.lbox.util.StringUtil;
import com.sjl.lbox.util.ToastUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * App签名获取
 * 需要在application节点声明
 * android:sharedUserId="android.uid.system"
 *
 * @author SJL
 * @date 2016/8/17 23:06
 */
public class SignatureActivity extends BaseActivity implements View.OnClickListener {

    private String tag = SignatureActivity.class.getSimpleName();
    private final int GET_LIST_SUCCESS = 0;
    private EditText etPkg;
    private Button btnGetSignature;
    private Button btnCopy;
    private TextView tvSignature;
    private ListView lv;
    private List<AppInfo> list;
    private AppInfoAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GET_LIST_SUCCESS) {
                DialogUtil.dismissProgressImageDialog();
                initAppList();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        initView();
    }

    private void initView() {
        etPkg = (EditText) findViewById(R.id.etPkg);
        btnGetSignature = (Button) findViewById(R.id.btnGetSignature);
        btnCopy = (Button) findViewById(R.id.btnCopy);
        tvSignature = (TextView) findViewById(R.id.tvSignature);
        lv = (ListView) findViewById(R.id.lv);

        getPkgList();
        btnGetSignature.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetSignature:
                String signature = getSignature(etPkg.getText().toString());
                if (!StringUtil.isEmpty(signature)) {
                    tvSignature.setText(signature);
                }
                break;
            case R.id.btnCopy:
                copySignature();
                break;
        }
    }

    /**
     * 复制签名
     */
    private void copySignature() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("text", tvSignature.getText().toString()));
        ToastUtil.showToast(mContext,"签名已复制");
    }

    /**
     * 初始化设备列表
     */
    private void initAppList() {
        adapter = new AppInfoAdapter(mContext, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etPkg.setText(list.get(position).getPackageName());
                btnGetSignature.performClick();
            }
        });
    }

    /**
     * 获取应用程序列表
     */
    private void getPkgList() {
        DialogUtil.showProgressDialog(mContext, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                list = new ArrayList<AppInfo>();
                PackageManager pm = getPackageManager();

                List<ApplicationInfo> applicationInfoList = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
                for (ApplicationInfo info : applicationInfoList) {
                    AppInfo appInfo = new AppInfo();
                    appInfo.setIcon(info.loadIcon(pm));
                    appInfo.setName(info.loadLabel(pm).toString());
                    appInfo.setPackageName(info.packageName);
                    try {
                        //获取应用的版本号
                        PackageInfo packageInfo = pm.getPackageInfo(appInfo.getPackageName(), 0);
                        String app_version = packageInfo.versionName;
                        appInfo.setVersion(app_version);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    appInfo.setSystemApp(isSystemApp(info));
                    list.add(appInfo);
                }
                handler.sendEmptyMessage(GET_LIST_SUCCESS);
            }
        }).start();
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
            LogUtil.i(tag, "getSignature:" + e.getMessage());
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
            LogUtil.i(tag, "parseSignature:" + e.getMessage());
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

    @Override
    protected void onDestroy() {
        handler.removeMessages(GET_LIST_SUCCESS);
        super.onDestroy();
    }
}
