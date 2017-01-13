package com.sjl.lbox.app.ui.CustomView.repeat.SwipeItemLayout.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.lbox.R;

import java.util.ArrayList;
import java.util.List;

/**
 * LVAdapter
 *
 * @author SJL
 * @date 2016/12/29
 */

public class LVAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public LVAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(int position,String item){
        if (list == null) {
            list = new ArrayList<String>();
        }
        position = position <= getCount() ? position : getCount();
        list.add(position, item);
        notifyDataSetChanged();
    }

    /**
     * 删除项
     *
     * @param position
     */
    public void removeItem(int position) {
        if (list == null) {
            list = new ArrayList<String>();
        }
        position = position > getCount() ? getCount() : position;
        if (list.size() > position) {
            list.remove(position);
        }
        notifyDataSetChanged();
    }

    /**
     * 刷新
     * @param refreshList
     */
    public void refresh(List<String> refreshList){
        this.list = refreshList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_swipe_layout, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
            holder.btnTop = (Button) convertView.findViewById(R.id.btnTop);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(list.get(position));
        holder.tvName.setTag(convertView);
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    //holder.itemView代表这一项的View用convertView，否则滑动出来的布局无法收回
                    //holder.getLayoutPosition()获取点击的是第几项
                    onItemClickListener.onItemClick((View) holder.tvName.getTag(), position);
                }
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemDelete((View) holder.tvName.getTag(), position);
                }
            }
        });
        holder.btnTop.setVisibility(View.GONE);
        return convertView;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemDelete(View view, int position);
    }

    class ViewHolder {
        TextView tvName;
        Button btnDelete;
        Button btnTop;
    }
}
