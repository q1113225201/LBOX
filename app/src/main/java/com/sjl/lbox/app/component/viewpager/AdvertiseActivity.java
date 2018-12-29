package com.sjl.lbox.app.component.viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.sjl.lbox.R;
import com.sjl.lbox.app.component.viewpager.adapter.ViewPagerAdapter;
import com.sjl.lbox.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * viewpager无限轮播广告
 * 
 * @author SJL
 * @date 2016/11/1 22:53
 */
public class AdvertiseActivity extends BaseActivity {

    private LinearLayout llDot;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private List<ImageView> list;
    private List<ImageView> dotList;

    private int[] images = {R.drawable.frame1,R.drawable.frame2,R.drawable.frame3,R.drawable.frame4};

    private int SCOLLING = 1;
    private int TIME = 3000;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==SCOLLING){
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                handler.sendEmptyMessageDelayed(SCOLLING,TIME);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise);

        initView();
    }

    private void initView() {
        llDot = (LinearLayout) findViewById(R.id.llDot);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        initData();
        adapter = new ViewPagerAdapter(mContext,list);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0;i<dotList.size();i++){
                    dotList.get(i).setImageResource(R.drawable.dot_normal);
                }
                dotList.get(position%dotList.size()).setImageResource(R.drawable.dot_light);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(Integer.MAX_VALUE/2-(Integer.MAX_VALUE/2)%list.size());
        handler.sendEmptyMessageDelayed(SCOLLING,TIME);
    }

    private void initData() {
        List<String> imageList = new ArrayList<>();
        imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493701483398&di=e2ef2a0ebecdb86812e5b4f0c33ddfae&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fd%2F59014d3e879e7.jpg");
        imageList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=905012644,2707244964&fm=23&gp=0.jpg");
        imageList.add("http://img.zcool.cn/community/01033456f114f932f875a94467912f.jpg@900w_1l_2o_100sh.jpg");
        //初始化滚动图片
        list = new ArrayList<ImageView>();
        for (int i=0;i<imageList.size();i++) {
            ImageView imageView = new ImageView(mContext);
//            imageView.setImageResource(images[i]);
            Glide.with(this).load(imageList.get(i)).into(imageView);
            list.add(imageView);
        }
        //初始化点
        dotList = new ArrayList<ImageView>();
        for (int i=0;i<list.size();i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(10,10);
            lp.setMargins(5,5,5,5);
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(lp);
            imageView.setImageResource(R.drawable.dot_normal);
            //添加到指示器列表中
            dotList.add(imageView);
            //添加到LinearLayout容器中
            llDot.addView(imageView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(SCOLLING,null);
    }
}
