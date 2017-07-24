package com.sjl.lbox.app.lib.Router;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

@Route(path = "/app/router")
public class ARouterActivity extends BaseActivity {
    @Autowired(name = "key")
    public String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Autowired注解需要添加这句
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_arouter);

        initView();
    }

    private void initView() {
        ((TextView)findViewById(R.id.tvShow)).setText(TextUtils.isEmpty(key)?"null":key);
    }
}
