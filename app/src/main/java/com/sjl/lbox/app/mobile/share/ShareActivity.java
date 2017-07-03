package com.sjl.lbox.app.mobile.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sjl.lbox.BuildConfig;
import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.config.CacheConfig;
import com.sjl.lbox.util.FileUtil;
import com.sjl.lbox.util.LogUtil;
import com.sjl.lbox.util.ZipUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 分享
 *
 * @author SJL
 * @date 2017/7/3
 */
public class ShareActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ShareActivity";
    private Button btnShareText;
    private Button btnShareFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        initView();
    }

    private void initView() {
        btnShareText = (Button) findViewById(R.id.btnShareText);
        btnShareText.setOnClickListener(this);
        btnShareFile = (Button) findViewById(R.id.btnShareFile);
        btnShareFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        switch (v.getId()) {
            case R.id.btnShareText:
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "分享消息");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.btnShareFile:
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date());
                List<File> fileList = new ArrayList<>();
                if (BuildConfig.DEBUG) {
                    fileList.add(new File(LogUtil.LOG_FILEPATH));
                } else {
                    fileList.add(new File(LogUtil.LOG_FILEPATH_RELEASE));
                }
                File file = new File(CacheConfig.PATH + "/" + filename + ".zip");
                try {
                    FileUtil.writeFile(file + "", "1", false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ZipUtil.zipFiles(fileList, file, new ZipUtil.ZipListener() {
                    @Override
                    public void zipProgress(int zipProgress) {
                        LogUtil.i(TAG, "zipProgress:" + zipProgress);
                    }
                });
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
