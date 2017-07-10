package com.sjl.lbox.app.lib.Router;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sjl.lbox.R;
import com.sjl.lbox.app.MainActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.librouter.Router;
import com.sjl.librouter.rule.IntentRule;

/**
 * 路由简单实现
 *
 * @author SJL
 * @date 2017/7/10
 */
public class RouterActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router);

        initView();
    }

    private void initView() {
        findViewById(R.id.btnMyRouter).setOnClickListener(this);
        initMyRouter();
    }

    private void initMyRouter() {
        Router.getInstance().addRouter(IntentRule.ACTIVITY_SCHEME + "main", MainActivity.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnMyRouter) {
            Intent intent = Router.getInstance().invokeRouter(mContext, IntentRule.ACTIVITY_SCHEME + "main");
            startActivity(intent);
        }
    }
}
