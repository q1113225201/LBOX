package com.sjl.lbox.app.lib.Glide.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sjl.lbox.R;

import java.util.List;

/**
 * GlideAdapter
 *
 * @author SJL
 * @date 2017/5/2
 */

public class GlideAdapter extends RecyclerView.Adapter<GlideAdapter.ViewHolder> {
    private Context context;
    private List<String> list;

    public GlideAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_single_image,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position))
                .placeholder(R.drawable.loading)
                .error(R.drawable.network_error)
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        }
    }
}
