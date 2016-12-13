package com.sjl.lbox.app.mode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.mode.mvp.MVPActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发模式
 *
 * @author SJL
 * @date 2016/12/13 21:18
 */
public class ModeActivity extends BaseActivity {

    private ListView lv;
    private ArrayAdapter adapter;
    private List<Module> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        initView();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);

        initData();
        adapter=new ArrayAdapter(mContext,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext,list.get(position).getAct()));
            }
        });
    }

    private void initData() {
        list = new ArrayList<Module>();
        list.add(new Module("MVP", MVPActivity.class));
    }
}
