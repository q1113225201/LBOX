package com.sjl.lbox.app.ui.CustomView.repeat.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjl.lbox.R;

import java.util.ArrayList;
import java.util.List;

/**
 * RVAdapter
 *
 * @author SJL
 * @date 2016/12/25 15:59
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    private Context context;
    private List<String> list;

    public RVAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_single_text, parent, false));
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
        return list.get(position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.single_text.setText(list.get(position));
        holder.single_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    //holder.itemView代表这一项的View
                    //holder.getLayoutPosition()获取点击的是第几项
                    onItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            }
        });
        holder.single_text.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemLongClick(holder, holder.getLayoutPosition());
                }
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(ViewHolder viewHolder, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView single_text;

        public ViewHolder(View itemView) {
            super(itemView);
            single_text = (TextView) itemView.findViewById(R.id.single_text);
        }
    }
}
