package com.sjl.lbox.app.mode.mvp.login.presenter;

import android.os.Handler;
import android.text.TextUtils;

import com.sjl.lbox.app.mode.mvp.login.view.MVPLoginView;
import com.sjl.lbox.app.mode.mvp.model.MVPUser;

/**
 * MVP登陆逻辑实现
 *
 * @author SJL
 * @date 2016/12/13 22:14
 */
public class MVPLoginPresenterImp implements MVPLoginPresenter {
    private MVPLoginView mvpLoginView;

    public MVPLoginPresenterImp(MVPLoginView mvpLoginView) {
        this.mvpLoginView = mvpLoginView;
    }

    @Override
    public void login(final String username, final String password) {
        if (TextUtils.isEmpty(username)) {
            mvpLoginView.loginFailure("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mvpLoginView.loginFailure("密码不能为空");
            return;
        }
        mvpLoginView.showProgressDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mvpLoginView.dismissProgressDialog();
                if ("mvp".equalsIgnoreCase(username) && "mvp".equalsIgnoreCase(password)) {
                    MVPUser user = new MVPUser(username, password);
                    mvpLoginView.loginSuccess(user, "登陆成功");
                } else {
                    mvpLoginView.loginFailure("用户名或密码错误");
                }
            }
        }, 2000);
    }
}
