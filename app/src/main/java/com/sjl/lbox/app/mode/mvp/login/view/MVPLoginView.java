package com.sjl.lbox.app.mode.mvp.login.view;

import com.sjl.lbox.app.mode.mvp.model.MVPUser;

/**
 * MVP登陆视图接口
 *
 * @author SJL
 * @date 2016/12/13 21:35
 */
public interface MVPLoginView {
    //初始化视图
    void initView();

    //清空输入框
    void clearText();

    //登陆成功
    void loginSuccess(MVPUser user, String msg);

    //登录失败
    void loginFailure(String msg);

    //显示进度框
    void showProgressDialog();

    //取消进度框
    void dismissProgressDialog();
}
