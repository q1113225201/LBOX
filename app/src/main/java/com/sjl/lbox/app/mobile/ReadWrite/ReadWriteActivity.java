package com.sjl.lbox.app.mobile.ReadWrite;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.config.CacheConfig;
import com.sjl.lbox.util.FileUtil;

import java.io.IOException;

public class ReadWriteActivity extends BaseActivity implements View.OnClickListener {
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnWrite:
                try {
                    FileUtil.writeFile(CacheConfig.PATH+"/write.txt","write content",true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnRead:
                try {
                    tv.setText(FileUtil.readFile(CacheConfig.PATH+"/write.txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
