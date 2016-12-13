package com.sjl.lbox.app.mode.mvp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sjl.lbox.R;
import com.sjl.lbox.app.mode.mvp.login.presenter.MVPLoginPresenter;
import com.sjl.lbox.app.mode.mvp.login.presenter.MVPLoginPresenterImp;
import com.sjl.lbox.app.mode.mvp.main.MVPMainActivity;
import com.sjl.lbox.app.mode.mvp.model.MVPUser;
import com.sjl.lbox.app.mode.mvp.login.view.MVPLoginView;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.DialogUtil;
import com.sjl.lbox.util.ToastUtil;
/**
 * MVP登陆界面
 *
 * @author SJL
 * @date 2016/12/13 22:14
 */
public class MVPLoginActivity extends BaseActivity implements MVPLoginView, View.OnClickListener {

    private EditText etMVPUsername;
    private EditText etMVPPassword;
    private Button btnMVPLogin;
    private Button btnMVPReset;

    private MVPLoginPresenter mvpLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvplogin);

        initView();
    }

    @Override
    public void initView() {
        etMVPUsername = (EditText) findViewById(R.id.etMVPUsername);
        etMVPPassword = (EditText) findViewById(R.id.etMVPPassword);
        btnMVPLogin = (Button) findViewById(R.id.btnMVPLogin);
        btnMVPReset = (Button) findViewById(R.id.btnMVPReset);

        btnMVPLogin.setOnClickListener(this);
        btnMVPReset.setOnClickListener(this);

        mvpLoginPresenter = new MVPLoginPresenterImp(this);
    }

    @Override
    public void clearText() {
        etMVPUsername.setText("");
        etMVPPassword.setText("");
    }

    @Override
    public void loginSuccess(MVPUser user, String msg) {
        Intent intent = new Intent(mContext, MVPMainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        ToastUtil.showToast(mContext, msg);
    }

    @Override
    public void loginFailure(String msg) {
        ToastUtil.showToast(mContext, msg);
    }

    @Override
    public void showProgressDialog() {
        DialogUtil.showProgressDialog(mContext,false);
    }

    @Override
    public void dismissProgressDialog() {
        DialogUtil.dismissProgressImageDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMVPLogin:
                mvpLoginPresenter.login(etMVPUsername.getText().toString(), etMVPPassword.getText().toString());
                break;
            case R.id.btnMVPReset:
                clearText();
                break;
        }
    }
}
