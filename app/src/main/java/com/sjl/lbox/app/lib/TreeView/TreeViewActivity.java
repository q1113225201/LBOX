package com.sjl.lbox.app.lib.TreeView;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.sjl.lbox.R;
import com.sjl.lbox.app.lib.TreeView.bean.BranchNode;
import com.sjl.lbox.app.lib.TreeView.bean.BranchViewBinder;
import com.sjl.lbox.app.lib.TreeView.bean.LeafNode;
import com.sjl.lbox.app.lib.TreeView.bean.LeafViewBinder;
import com.sjl.lbox.app.lib.TreeView.bean.RootNode;
import com.sjl.lbox.app.lib.TreeView.bean.RootViewBinder;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ToastUtil;
import com.sjl.libtreeview.TreeViewAdapter;
import com.sjl.libtreeview.bean.LayoutItem;
import com.sjl.libtreeview.bean.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeViewActivity extends BaseActivity {
    private RecyclerView rv;
    private List<TreeNode> list = new ArrayList<>();
    private TreeViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_view);

        initView();
    }

    private void initView() {
        rv = findViewById(R.id.rv);

        list.clear();
        list.addAll(initTree());
        initAdapter();
    }

    private void initAdapter() {
        adapter = new TreeViewAdapter(list, Arrays.asList(new RootViewBinder(), new BranchViewBinder(), new LeafViewBinder())) {
            @Override
            public void toggle(View view, TreeNode treeNode) {
                if (!(treeNode.getValue() instanceof LeafNode)) {
                    view.setRotation(view.getRotation() > 0 ? 0 : 90);
                }
            }

            @Override
            public void checked(View view, boolean checked, TreeNode treeNode) {
                //((TextView)view).setTextColor(view.getContext().getResources().getColor(checked?R.color.colorPrimary:R.color.colorAccent));
            }

            @Override
            public void itemClick(View view, TreeNode treeNode) {
                String name = null;
                LayoutItem item = treeNode.getValue();
                if (item instanceof RootNode) {
                    name = ((RootNode) item).getName();
                } else if (item instanceof BranchNode) {
                    name = ((BranchNode) item).getName();
                } else if (item instanceof LeafNode) {
                    name = ((LeafNode) item).getName();
                }
                ToastUtil.showToast(mContext,name);
            }
        };
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<TreeNode> initTree() {
        List<TreeNode> rootList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TreeNode<RootNode> node = new TreeNode<>(new RootNode("根" + i));
            if (i % 2 == 0) {
                node.setChildNodes(initBranchs(node, node.getValue().getName()));
            } else {
                node.setChildNodes(initLeaves(node, node.getValue().getName()));
            }
            rootList.add(node);
        }
        return rootList;
    }

    int count = 5;

    private List<TreeNode> initBranchs(TreeNode treeNode, String name) {
        List<TreeNode> branchList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TreeNode<BranchNode> node = new TreeNode<>(new BranchNode(name + "枝" + i));
            if (i % 2 == 0) {
                node.setChildNodes(initLeaves(node, node.getValue().getName()));
            } else {
                if (count > 0) {
                    count--;
                    node.setChildNodes(initBranchs(node, node.getValue().getName()));
                } else {
                    node.setChildNodes(initLeaves(node, node.getValue().getName()));
                }
            }
            branchList.add(node);
        }
        return branchList;
    }

    private List<TreeNode> initLeaves(TreeNode treeNode, String name) {
        List<TreeNode> leafList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TreeNode<LeafNode> node = new TreeNode<>(new LeafNode(name + "叶" + i));
            leafList.add(node);
        }
        return leafList;
    }
}
