package com.sjl.lbox.app.mode.mvp;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.mode.mvp.login.MVPLoginActivity;
import com.sjl.lbox.base.BaseActivity;

/**
 * MVP模式
 *
 * @author SJL
 * @date 2016/12/13 21:25
 */
public class MVPActivity extends BaseActivity {

    private Button btnEnter;
    private TextView tvLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        initView();
    }

    private void initView() {
        btnEnter = (Button) findViewById(R.id.btnEnter);
        tvLink = (TextView) findViewById(R.id.tvLink);
        tvLink.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MVPLoginActivity.class));
            }
        });
        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(tvLink.getText().toString()));
                startActivity(intent);
            }
        });
    }
}
