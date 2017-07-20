package com.sjl.lbox.app.lib.Glide;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.sjl.lbox.R;
import com.sjl.lbox.app.lib.Glide.adapter.GlideAdapter;
import com.sjl.lbox.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Glide图片加载
 * 支持加载动图
 *
 * @author SJL
 * @date 2017/4/28
 */
public class GlideActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivGlide;
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
        setContentView(R.layout.activity_glide);

        initView();
    }

    private void initView() {
        ivGlide = (ImageView) findViewById(R.id.ivGlide);
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
                Glide.with(mContext)
                        .load("http://img05.tooopen.com/images/20150531/tooopen_sy_127457023651.jpg")//要加载的图片
                        .placeholder(R.drawable.loading)//加载中显示动画
                        .error(R.drawable.network_error)//加载失败动画
                        .crossFade()//图片加载动画
                        .transform(new BitmapTransformation(mContext) {
                            @Override
                            protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                                Matrix matrix = new Matrix();
                                matrix.setRotate(index * 90);//旋转
                                return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
                            }

                            @Override
                            public String getId() {
                                return "transform:" + index;
                            }
                        })//图片变换效果
                        .into(ivGlide);
                index++;
                break;
            case R.id.btnLoadGif:
                Glide.with(mContext)
                        .load("http://s1.dwstatic.com/group1/M00/6A/99/020c07fb5680156e67e22c8056b2d699.gif")
                        .asGif()
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.network_error)
                        .override(300, 200)//重写图片宽高
                        .into(ivGlide);
                break;
            case R.id.btnLoadList:
                initAdapter();
                break;
        }
    }
}
