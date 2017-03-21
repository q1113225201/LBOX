package com.sjl.lbox.app.component.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;

import java.util.ArrayList;
import java.util.List;
/**
 * ViewPager使用
 * 
 * @author SJL
 * @date 2016/11/1 22:54
 */
public class ViewPagerActivity extends BaseActivity {

    private ListView lv;
    private List<Module> list;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        initView();
    }

    private void initView() {
        lv= (ListView) findViewById(R.id.lv);

        adapter=new ArrayAdapter(mContext,android.R.layout.simple_list_item_1,getData());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext,list.get(position).getAct()));
            }
        });
    }

    private List<Module> getData() {
        list=new ArrayList<Module>();
        list.add(new Module("轮播广告",AdvertiseActivity.class));
        return list;
    }
}
