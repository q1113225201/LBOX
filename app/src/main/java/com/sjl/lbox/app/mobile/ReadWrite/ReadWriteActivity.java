package com.sjl.lbox.app.mobile.ReadWrite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.config.CacheConfig;
import com.sjl.lbox.util.FileUtil;
import com.sjl.lbox.util.LogUtil;
import com.sjl.lbox.util.UriUtil;

import java.io.File;
import java.io.IOException;

public class ReadWriteActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ReadWriteActivity";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_write);

        initView();
    }

    private void initView() {
        tv = findViewById(R.id.tv);
        findViewById(R.id.btnWrite).setOnClickListener(this);
        findViewById(R.id.btnRead).setOnClickListener(this);
        findViewById(R.id.btnOpen).setOnClickListener(this);
        findViewById(R.id.btnOpen2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btnWrite:
                try {
                    FileUtil.writeFile(CacheConfig.PATH + "/write.txt", "write content", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnRead:
                try {
                    tv.setText(FileUtil.readFile(CacheConfig.PATH + "/write.txt", "write content"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnOpen:
                File file = new File(CacheConfig.PATH + "/test.doc");
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uriFile = UriUtil.getFileUri(mContext,file);
//                intent.setDataAndType(uriFile, "application/msword");
                intent.setDataAndType(Uri.parse("content://"+CacheConfig.PATH + "/test.doc"), "application/msword");
                startActivity(intent);
                break;
            case R.id.btnOpen2:
//                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("ms-word:ofe|u|"+UriUtil.getFileUri(mContext, new File(Environment.getExternalStorageDirectory() + "/cache/test.doc"))));
//                Uri uri = Uri.parse("ms-word:ofe|u|" + CacheConfig.PATH + "/test.doc");
//                Uri uri = Uri.parse("ms-word:ofe|u|" + new File(CacheConfig.PATH + "/test.doc"));
//                Uri uri = Uri.parse("ms-word:ofe|u|"+Uri.parse(CacheConfig.PATH + "/test.doc"));
                Uri uri = Uri.parse("ms-word:ofe|u|http://localhost:8080/Web20170802/test.doc");
                LogUtil.i(TAG,uri);
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
    }
}
