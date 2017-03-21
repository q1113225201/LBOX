package com.sjl.lbox.app.component.slideMenu;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * DrawerLayout和NavigationView实现侧滑菜单
 *
 * @author SJL
 * @date 2016/12/18 17:10
 */
public class NavigationViewActivity extends BaseActivity implements View.OnClickListener {

    private DrawerLayout dl;
    private NavigationView navView;
    private Button btnSlide;
    private TextView tvSelect;

    private ImageView ivHeaderImg;
    private TextView tvHeaderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);

        initView();
    }

    private void initView() {
        dl = (DrawerLayout) findViewById(R.id.dl);
        navView = (NavigationView) findViewById(R.id.navView);
        btnSlide = (Button) findViewById(R.id.btnSlide);
        tvSelect = (TextView) findViewById(R.id.tvSelect);

        btnSlide.setOnClickListener(this);

        initMenu();
    }

    private void initMenu() {
        //获取NavigationView头部
        View header = navView.getHeaderView(0);
        ivHeaderImg = (ImageView) header.findViewById(R.id.ivHeaderImg);
        ivHeaderImg.setOnClickListener(this);
        tvHeaderTitle = (TextView) header.findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setOnClickListener(this);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //菜单选中
                item.setChecked(true);
                //关闭侧滑
                dl.closeDrawers();
                tvSelect.setText(item.getTitle());
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivHeaderImg:
                tvSelect.setText("ivHeaderImg");
                //关闭侧滑
                dl.closeDrawers();
                break;
            case R.id.tvHeaderTitle:
                tvSelect.setText("tvHeaderTitle");
                //关闭侧滑
                dl.closeDrawers();
                break;
            case R.id.btnSlide:
                //打开侧滑
                dl.openDrawer(GravityCompat.START);
                break;
        }
    }
}
