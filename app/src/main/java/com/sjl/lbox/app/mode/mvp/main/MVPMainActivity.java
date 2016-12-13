package com.sjl.lbox.app.mode.mvp.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.mode.mvp.main.presenter.MVPMainPresenter;
import com.sjl.lbox.app.mode.mvp.main.presenter.MVPMainPresenterImp;
import com.sjl.lbox.app.mode.mvp.main.view.MVPMainView;
import com.sjl.lbox.app.mode.mvp.model.MVPUser;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ToastUtil;

/**
 * MVP首页
 * 
 * @author SJL
 * @date 2016/12/13 22:17
 */
public class MVPMainActivity extends BaseActivity implements MVPMainView, View.OnClickListener {

    private TextView tvMVPUsername;
    private TextView tvMVPPassword;
    private Button btnMVPLogout;

    private MVPMainPresenter mvpMainPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvpmain);

        initView();
    }

    @Override
    public void initView() {
        tvMVPUsername = (TextView) findViewById(R.id.tvMVPUsername);
        tvMVPPassword = (TextView) findViewById(R.id.tvMVPPassword);
        btnMVPLogout = (Button) findViewById(R.id.btnMVPLogout);

        btnMVPLogout.setOnClickListener(this);

        mvpMainPresenter = new MVPMainPresenterImp(this);

        initData();
    }

    private void initData() {
        MVPUser user = (MVPUser) getIntent().getExtras().get("user");
        tvMVPUsername.setText(String.format("帐号：%s",user.getUsername()));
        tvMVPPassword.setText(String.format("密码：%s",user.getPassword()));
    }

    @Override
    public void logoutSuccess() {
        ToastUtil.showToast(mContext,"注销成功");
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMVPLogout:
                mvpMainPresenter.logout();
                break;
        }
    }
}
