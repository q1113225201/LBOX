package com.sjl.lbox.app.component.slideMenu;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
/**
 * DrawerLayout和ListView实现侧滑菜单
 * 
 * @author SJL
 * @date 2016/12/18 21:15
 */
public class ListMenuActivity extends BaseActivity implements View.OnClickListener {

    private DrawerLayout dl;
    private Button btnSlide;
    private TextView tvSelect;
    private ListView lvMenu;

    private ImageView ivHeaderImg;
    private TextView tvHeaderTitle;

    private ArrayAdapter adapter;
    private String[] items={"menu1","menu2","menu3","menu4"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);

        initView();
    }

    private void initView() {
        dl = (DrawerLayout) findViewById(R.id.dl);
        btnSlide = (Button) findViewById(R.id.btnSlide);
        tvSelect = (TextView) findViewById(R.id.tvSelect);
        lvMenu = (ListView) findViewById(R.id.lvMenu);

        btnSlide.setOnClickListener(this);

        initMenu();
    }

    private void initMenu() {
        //添加ListView头部
        View header = LayoutInflater.from(mContext).inflate(R.layout.navigation_view_header,null);
        lvMenu.addHeaderView(header,null,false);
        ivHeaderImg = (ImageView) header.findViewById(R.id.ivHeaderImg);
        ivHeaderImg.setOnClickListener(this);
        tvHeaderTitle = (TextView) header.findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setOnClickListener(this);

        adapter=new ArrayAdapter(mContext,android.R.layout.simple_list_item_1,items);
        lvMenu.setAdapter(adapter);
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvSelect.setText(items[position-1]);
                dl.closeDrawers();
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
