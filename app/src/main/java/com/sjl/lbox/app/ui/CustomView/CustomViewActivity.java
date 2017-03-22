package com.sjl.lbox.app.ui.CustomView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.EditText.EditTextActivity;
import com.sjl.lbox.app.ui.CustomView.ExpandMenu.ExpandMenuActivity;
import com.sjl.lbox.app.ui.CustomView.contact.ContactActivity;
import com.sjl.lbox.app.ui.CustomView.gesture.GestureActivity;
import com.sjl.lbox.app.ui.CustomView.indicator.IndicatorActivity;
import com.sjl.lbox.app.ui.CustomView.magnifier.MagnifierActivity;
import com.sjl.lbox.app.ui.CustomView.progress.ProgressActivity;
import com.sjl.lbox.app.ui.CustomView.repeat.RepeatActivity;
import com.sjl.lbox.app.ui.CustomView.wave.WaveActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义View
 *
 * @author SJL
 * @date 2017/1/12
 */
public class CustomViewActivity extends BaseActivity {
    private ListView lv;
    private ArrayAdapter adapter;
    private List<Module> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        initView();
    }

    private void initView() {
        initData();
        lv = (ListView) findViewById(R.id.lv);
        adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, list.get(position).getAct());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        list = new ArrayList<Module>();
        list.add(new Module("联系人列表", ContactActivity.class));
        list.add(new Module("输入框", EditTextActivity.class));
        list.add(new Module("手势密码", GestureActivity.class));
        list.add(new Module("进度条", ProgressActivity.class));
        list.add(new Module("列表、网状布局", RepeatActivity.class));
        list.add(new Module("波浪", WaveActivity.class));
        list.add(new Module("放大镜", MagnifierActivity.class));
        list.add(new Module("ViewPager指示器", IndicatorActivity.class));
        list.add(new Module("展开菜单", ExpandMenuActivity.class));
    }
}
