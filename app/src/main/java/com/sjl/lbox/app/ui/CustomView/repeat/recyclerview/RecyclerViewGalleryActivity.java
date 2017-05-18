package com.sjl.lbox.app.ui.CustomView.repeat.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.lib.Glide.adapter.GlideAdapter;
import com.sjl.lbox.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView实现的Gallery画廊
 *
 * @author SJL
 * @date 2017/5/9
 */
public class RecyclerViewGalleryActivity extends BaseActivity {
    private ImageView iv;
    private RecyclerView rv;
    private GlideAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_gallery);

        initView();
    }

    private void initView() {
        iv = (ImageView) findViewById(R.id.iv);
        rv = (RecyclerView) findViewById(R.id.rv);

        initAdpter();
    }

    private void initAdpter() {
        List<String> imageList = new ArrayList<>();
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493701483398&di=69a08f25b245f6a6a489b85ff8a56ad4&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201704%2F15%2F065614ynk3in9yqtik8qss.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493701483398&di=e2ef2a0ebecdb86812e5b4f0c33ddfae&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fd%2F59014d3e879e7.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494296235&di=45ae16f56b4a28a2cf4e193769b25d96&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.pp3.cn%2Fuploads%2F201502%2F2015021215.jpg");
        imageList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3520066001,2415029280&fm=23&gp=0.jpg");
        imageList.add("http://img3.duitang.com/uploads/item/201311/18/20131118201009_nFLNJ.gif");
        imageList.add("http://ww1.sinaimg.cn/mw690/005F3jyTgw1ek61hxewtyg30ao06ok9t.gif");
        imageList.add("http://cdn.duitang.com/uploads/item/201505/26/20150526214813_H3TfR.gif");
        imageList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=905012644,2707244964&fm=23&gp=0.jpg");
        imageList.add("http://img.zcool.cn/community/01033456f114f932f875a94467912f.jpg@900w_1l_2o_100sh.jpg");

        adapter = new GlideAdapter(mContext, imageList);
        rv.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(linearLayoutManager);
        new CustomSnapHelper().attachToRecyclerView(rv);
    }
}
