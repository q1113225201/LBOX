package com.sjl.lbox.app.ui.CustomView.indicator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.indicator.view.ViewPagerIndicator;
import com.sjl.lbox.app.ui.viewpager.adapter.ViewPagerAdapter;
import com.sjl.lbox.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 指示器
 *
 * @author SJL
 * @date 2017/3/19 14:54
 */
public class IndicatorActivity extends BaseActivity {
    private ViewPager viewPager;
    private ViewPagerIndicator viewPagerIndicator;

    private List<ImageView> list;
    private ViewPagerAdapter adapter;

    private int[] images = {R.drawable.frame1, R.drawable.frame2, R.drawable.frame3, R.drawable.frame4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);

        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.viewPagerIndicator);

        initData();
        adapter = new ViewPagerAdapter(mContext,list);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                viewPagerIndicator.setPagerAndOffset(position%list.size(),positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        //初始化滚动图片
        list = new ArrayList<ImageView>();
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(images[i]);
            list.add(imageView);
        }
        //初始化点
        viewPagerIndicator.setNum(list.size());
    }
}
