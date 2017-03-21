package com.sjl.lbox.app.ui.animate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.animate.PathAnim.PathAnimActivity;
import com.sjl.lbox.app.ui.animate.SceneAnim.SceneAnimActivity;
import com.sjl.lbox.app.ui.animate.TransitionAnim.TransitionAnimActivity;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * 动画
 *
 * @author SJL
 * @date 2016/11/2 20:46
 */
public class AnimateActivity extends BaseActivity {

    private ListView lv;
    private ArrayAdapter adapter;
    private List<Module> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);

        intiView();
    }

    private void intiView() {
        lv = (ListView) findViewById(R.id.lv);

        list = getData();
        adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(mContext, list.get(i).getAct()));
            }
        });
    }

    private List<Module> getData() {
        list = new ArrayList<Module>();
        list.add(new Module("逐帧(Frame)动画", FrameAnimateActivity.class));
        list.add(new Module("补间(Tween)动画", TweenAnimateActivity.class));
        list.add(new Module("属性(Property)动画", PropertyAnimateActivity.class));
        list.add(new Module("过渡动画", TransitionAnimActivity.class));
        list.add(new Module("场景动画", SceneAnimActivity.class));
        list.add(new Module("轨迹动画", PathAnimActivity.class));
        return list;
    }
}
