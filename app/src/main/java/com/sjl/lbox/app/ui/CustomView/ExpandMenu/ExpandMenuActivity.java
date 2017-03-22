package com.sjl.lbox.app.ui.CustomView.ExpandMenu;

import android.os.Bundle;
import android.view.View;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.ExpandMenu.view.ExpandMenu;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ToastUtil;

/**
 * 展开菜单
 *
 * @author SJL
 * @date 2017/3/22
 */
public class ExpandMenuActivity extends BaseActivity implements ExpandMenu.OnMenuItemClickListener {

    private ExpandMenu expandMenu1;
    private ExpandMenu expandMenu2;
    private ExpandMenu expandMenu3;
    private ExpandMenu expandMenu4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_menu);

        initView();
    }

    private void initView() {
        expandMenu1 = (ExpandMenu) findViewById(R.id.expandMenu1);
        expandMenu1.setOnMenuItemClickListener(this);
        expandMenu2 = (ExpandMenu) findViewById(R.id.expandMenu2);
        expandMenu2.setOnMenuItemClickListener(this);
        expandMenu3 = (ExpandMenu) findViewById(R.id.expandMenu3);
        expandMenu3.setOnMenuItemClickListener(this);
        expandMenu4 = (ExpandMenu) findViewById(R.id.expandMenu4);
        expandMenu4.setOnMenuItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu11:
            case R.id.menu21:
            case R.id.menu31:
            case R.id.menu41:
                ToastUtil.showToast(mContext, "上一曲");
                break;
            case R.id.menu12:
            case R.id.menu22:
            case R.id.menu32:
            case R.id.menu42:
                ToastUtil.showToast(mContext, "播放");
                break;
            case R.id.menu13:
            case R.id.menu23:
            case R.id.menu33:
            case R.id.menu43:
                ToastUtil.showToast(mContext, "下一曲");
                break;
            case R.id.menu14:
            case R.id.menu24:
            case R.id.menu34:
            case R.id.menu44:
                ToastUtil.showToast(mContext, "停止");
                break;
        }
    }
}
