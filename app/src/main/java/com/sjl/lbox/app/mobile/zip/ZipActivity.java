package com.sjl.lbox.app.mobile.zip;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.config.CacheConfig;
import com.sjl.lbox.util.FileUtil;
import com.sjl.lbox.util.ToastUtil;
import com.sjl.lbox.util.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * 压缩文件常用工具类演示
 *
 * @author SJL
 * @date 2016/9/17 22:43
 */
public class ZipActivity extends BaseActivity implements View.OnClickListener {

    private Button btnZip;
    private Button btnUnZip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip);

        initView();
    }

    private void initView() {
        btnZip = (Button) findViewById(R.id.btnZip);
        btnUnZip = (Button) findViewById(R.id.btnUnZip);

        btnZip.setOnClickListener(this);
        btnUnZip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnZip:
                zip();
                break;
            case R.id.btnUnZip:
                unZip();
                break;
        }
    }

    /**
     * 压缩
     */
    private void zip() {
        //删除原有文件
        FileUtil.deleteFile(CacheConfig.ZIP_PATH);
        try {
            //创建要压缩的文件
            FileUtil.writeFile(CacheConfig.ZIP_PATH + "folder/" + System.currentTimeMillis() + ".txt", System.currentTimeMillis() + "", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<File> files = new ArrayList<File>();
        files.add(new File(CacheConfig.ZIP_PATH + "folder"));
        ZipUtil.zipFiles(files, new File(CacheConfig.ZIP_PATH + "zipFile.zip"), "comment", new ZipUtil.ZipListener() {
            @Override
            public void zipProgress(int zipProgress) {
                ToastUtil.showToast(mContext, zipProgress + "%", Gravity.CENTER);
            }
        });
    }

    /**
     * 解压
     */
    private void unZip() {
        //删除原有压缩文件
        FileUtil.deleteFile(CacheConfig.ZIP_PATH + "unZipFile");
        ZipUtil.upZipFile(new File(CacheConfig.ZIP_PATH + "zipFile.zip"), CacheConfig.ZIP_PATH + "unZipFile");
    }
}
