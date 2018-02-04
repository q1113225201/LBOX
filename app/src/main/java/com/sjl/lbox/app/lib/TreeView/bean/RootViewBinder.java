package com.sjl.lbox.app.lib.TreeView.bean;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.libtreeview.bean.TreeNode;
import com.sjl.libtreeview.bean.TreeViewBinder;

/**
 * RootViewBinder
 *
 * @author 林zero
 * @date 2018/2/3
 */

public class RootViewBinder extends TreeViewBinder<RootViewBinder.ViewHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.item_root;
    }

    @Override
    public int getToggleId() {
        return R.id.ivNode;
    }

    @Override
    public int getCheckedId() {
        return R.id.ivCheck;
    }

    @Override
    public int getClickId() {
        return R.id.tvName;
    }

    @Override
    public ViewHolder creatViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position, TreeNode treeNode) {
        ((TextView) holder.findViewById(R.id.tvName)).setText(((RootNode) treeNode.getValue()).getName());
        holder.findViewById(R.id.ivNode).setRotation(treeNode.isExpanded() ? 90 : 0);
        ((ImageView) holder.findViewById(R.id.ivCheck)).setImageResource(treeNode.isChecked() ? R.drawable.ic_checked : R.drawable.ic_uncheck);
        holder.findViewById(R.id.llParent).setBackgroundColor(holder.itemView.getContext().getResources().getColor(treeNode.isChecked() ? R.color.gray : R.color.white));
    }

    class ViewHolder extends TreeViewBinder.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
