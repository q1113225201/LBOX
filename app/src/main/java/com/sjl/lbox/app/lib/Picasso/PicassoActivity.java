package com.sjl.lbox.app.lib.Picasso;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.lib.Glide.adapter.GlideAdapter;
import com.sjl.lbox.base.BaseActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

public class PicassoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivShow;
    private Button btnLoadImg;
    private Button btnLoadGif;
    private Button btnLoadList;

    private List<String> imageList;
    private GlideAdapter glideAdapter;
    private RecyclerView rv;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);

        initView();
    }

    private void initView() {
        ivShow = (ImageView) findViewById(R.id.ivShow);
        btnLoadImg = (Button) findViewById(R.id.btnLoadImg);
        btnLoadImg.setOnClickListener(this);
        btnLoadGif = (Button) findViewById(R.id.btnLoadGif);
        btnLoadGif.setOnClickListener(this);
        btnLoadList = (Button) findViewById(R.id.btnLoadList);
        btnLoadList.setOnClickListener(this);
        rv = (RecyclerView) findViewById(R.id.rv);
    }

    private void initAdapter() {
        imageList = new ArrayList<>();
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493701483398&di=69a08f25b245f6a6a489b85ff8a56ad4&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201704%2F15%2F065614ynk3in9yqtik8qss.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493701483398&di=e2ef2a0ebecdb86812e5b4f0c33ddfae&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fd%2F59014d3e879e7.jpg");
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494296235&di=45ae16f56b4a28a2cf4e193769b25d96&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.pp3.cn%2Fuploads%2F201502%2F2015021215.jpg");
        imageList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3520066001,2415029280&fm=23&gp=0.jpg");
        imageList.add("http://img3.duitang.com/uploads/item/201311/18/20131118201009_nFLNJ.gif");
        imageList.add("http://ww1.sinaimg.cn/mw690/005F3jyTgw1ek61hxewtyg30ao06ok9t.gif");
        imageList.add("http://cdn.duitang.com/uploads/item/201505/26/20150526214813_H3TfR.gif");
        imageList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=905012644,2707244964&fm=23&gp=0.jpg");
        imageList.add("http://img.zcool.cn/community/01033456f114f932f875a94467912f.jpg@900w_1l_2o_100sh.jpg");

        glideAdapter = new GlideAdapter(mContext, imageList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(glideAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadImg:
                Picasso.with(mContext)
                        .load("http://img05.tooopen.com/images/20150531/tooopen_sy_127457023651.jpg")//要加载的图片
                        .placeholder(R.drawable.loading)//加载中显示动画
                        .error(R.drawable.network_error)//加载失败动画
                        .transform(new Transformation() {
                            @Override
                            public Bitmap transform(Bitmap source) {
                                Matrix matrix = new Matrix();
                                matrix.setRotate(index * 90);//旋转
                                Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
                                //转换后需要回收原图
                                source.recycle();
                                return result;
                            }

                            @Override
                            public String key() {
                                return "picasso";
                            }
                        })
                        .into(ivShow);
                index++;
                break;
            case R.id.btnLoadGif:
                Picasso.with(mContext)
                        .load("http://s1.dwstatic.com/group1/M00/6A/99/020c07fb5680156e67e22c8056b2d699.gif")
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.network_error)
                        .resize(300,200)
                        .into(ivShow);
                break;
            case R.id.btnLoadList:
                initAdapter();
                break;
        }
    }
}
