package com.sjl.lbox.app.ui.view.CardView.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sjl.lbox.R;

import java.util.List;

/**
 * ItemCardAdapter
 *
 * @author SJL
 * @date 2017/1/17
 */

public class ItemCardAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public ItemCardAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_card_view, null);
            holder.tvCardTitle = (TextView) convertView.findViewById(R.id.tvCardTitle);
            holder.tvCardContent = (TextView) convertView.findViewById(R.id.tvCardContent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvCardTitle.setText("标题" + position);
        holder.tvCardContent.setText("内容" + position);
        return convertView;
    }

    class ViewHolder {
        TextView tvCardTitle;
        TextView tvCardContent;
    }
}
