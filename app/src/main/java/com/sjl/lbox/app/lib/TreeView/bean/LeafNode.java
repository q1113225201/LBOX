package com.sjl.lbox.app.lib.TreeView.bean;

import com.sjl.lbox.R;
import com.sjl.libtreeview.bean.LayoutItem;

/**
 * LeafNode
 *
 * @author 林zero
 * @date 2018/2/3
 */

public class LeafNode implements LayoutItem {
    private String name;

    public LeafNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_leaf;
    }

    @Override
    public int getToggleId() {
        return 0;
    }

    @Override
    public int getCheckedId() {
        return R.id.ivCheck;
    }

    @Override
    public int getClickId() {
        return R.id.tvName;
    }
}
