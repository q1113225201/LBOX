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
        imageList.add("http://example-pictures.oss-cn-hangzhou.aliyuncs.com/%E8%8E%B2%E6%AD%A3.png");
        imageList.add("http:\\/\\/example-pictures.oss-cn-hangzhou.aliyuncs.com\\/%E6%B8%85%E5%BB%89%E4%B8%BD%E6%B0%B4.png");
        imageList.add("http:\\/\\/example-pictures.oss-cn-hangzhou.aliyuncs.com\\/%E6%B8%85%E6%AD%A3%E5%BB%89%E6%B4%81.png");
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
