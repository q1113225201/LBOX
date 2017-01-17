package com.sjl.lbox.app.ui.CustomView.repeat.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.repeat.CardView.adapter.ItemCardAdapter;
import com.sjl.lbox.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡片视图CardView
 *
 * @author SJL
 * @date 2017/1/17
 */
public class CardViewActivity extends BaseActivity {
    private ListView lv;
    private List<String> list;
    private ItemCardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        initView();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);

        initData();
        adapter = new ItemCardAdapter(mContext, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(CardViewActivity.this,view,"card");
                Intent intent = new Intent(mContext,CardViewDetailActivity.class);
                intent.putExtra("position",position);
                startActivity(intent,activityOptionsCompat.toBundle());
            }
        });
    }

    private void initData() {
        list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
    }
}
