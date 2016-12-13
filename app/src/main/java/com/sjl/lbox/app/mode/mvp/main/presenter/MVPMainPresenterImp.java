package com.sjl.lbox.app.mode.mvp.main.presenter;

import com.sjl.lbox.app.mode.mvp.main.view.MVPMainView;

/**
 * MVP首页逻辑接口实现
 * 
 * @author SJL
 * @date 2016/12/13 22:25
 */
public class MVPMainPresenterImp implements MVPMainPresenter {
    private MVPMainView mvpMainView;

    public MVPMainPresenterImp(MVPMainView mvpMainView) {
        this.mvpMainView = mvpMainView;
    }

    @Override
    public void logout() {
        mvpMainView.logoutSuccess();
    }
}
