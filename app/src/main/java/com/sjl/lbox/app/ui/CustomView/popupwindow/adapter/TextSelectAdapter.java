package com.sjl.lbox.app.ui.CustomView.popupwindow.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.popupwindow.listener.OnItemClickListener;

import java.util.List;

/**
 * TextSelectAdapter
 *
 * @author SJL
 * @date 2017/2/28
 */

public class TextSelectAdapter extends RecyclerView.Adapter<TextSelectAdapter.ViewHolder> {
    private Context context;
    private List<String> list;
    private OnItemClickListener onItemClickListener;

    public TextSelectAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pop_up_window_select, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvItemName.setText(list.get(position));
        holder.tvItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);
        }
    }
}
