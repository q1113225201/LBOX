package com.sjl.lbox.app.ui.CustomView.repeat.SwipeItemLayout.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.lbox.R;

import java.util.ArrayList;
import java.util.List;

/**
 * RVAdapter
 *
 * @author SJL
 * @date 2016/12/29
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    private Context context;
    private List<String> list;

    public RVAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_swipe_layout, parent, false));
    }

    /**
     * 添加项
     *
     * @param position
     * @param item
     */
    public void addItem(int position, String item) {
        if (list == null) {
            list = new ArrayList<String>();
        }
        position = position <= getItemCount() ? position : getItemCount();
        list.add(position, item);
        notifyItemInserted(position);
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
        position = position > getItemCount() ? getItemCount() : position;
        if (list.size() > position) {
            list.remove(position);
        }
        notifyItemRemoved(position);
    }

    /**
     * 获取项
     *
     * @param position
     * @return
     */
    public String getItem(int position) {
        if (position > getItemCount()) {
            return null;
        }
        return list.get(position);
    }

    /**
     * 置顶项
     *
     * @param position
     */
    public void topItem(int position) {
        String item = list.get(position);
        list.remove(position);
        notifyItemRemoved(position);
        list.add(0, item);
        notifyItemInserted(0);
    }

    /**
     * 刷新列表
     *
     * @param refreshList
     */
    public void refresh(List<String> refreshList) {
        list.clear();
        list.addAll(refreshList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvName.setText(list.get(position));
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    //holder.itemView代表这一项的View
                    //holder.getLayoutPosition()获取点击的是第几项
                    onItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemDelete(holder.itemView, holder.getLayoutPosition());
                }
            }
        });
        holder.btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemTop(holder.itemView, holder.getLayoutPosition());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemDelete(View view, int position);

        void onItemTop(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        Button btnDelete;
        Button btnTop;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            btnTop = (Button) itemView.findViewById(R.id.btnTop);
        }
    }
}
