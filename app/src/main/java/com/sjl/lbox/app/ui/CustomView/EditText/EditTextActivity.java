package com.sjl.lbox.app.ui.CustomView.EditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.EditText.FloatLableEditText.FloatLableEditTextActivity;
import com.sjl.lbox.app.ui.CustomView.EditText.HintImgEditText.HintImgEditTextActivity;
import com.sjl.lbox.app.ui.CustomView.EditText.PwdEditText.PwdEditTextActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;

import java.util.ArrayList;
import java.util.List;

public class EditTextActivity extends BaseActivity {
    private ListView lv;
    private ArrayAdapter adapter;
    private List<Module> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
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
        list.add(new Module("仿支付宝密码输入框", PwdEditTextActivity.class));
        list.add(new Module("图片提示输入框", HintImgEditTextActivity.class));
        list.add(new Module("浮动提示输入框", FloatLableEditTextActivity.class));
    }
}
