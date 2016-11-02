package com.sjl.lbox.app.viewpager.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * ViewPager适配器
 *
 * @author SJL
 * @date 2016/11/1 22:54
 */
public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<ImageView> list;

    public ViewPagerAdapter(Context context, List<ImageView> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position%list.size();
        if(list.get(position).getParent()!=null){
            container.removeView(list.get(position));
        }
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
