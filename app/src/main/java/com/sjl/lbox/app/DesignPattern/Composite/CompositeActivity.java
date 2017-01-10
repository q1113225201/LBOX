package com.sjl.lbox.app.DesignPattern.Composite;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
/**
 * 组合模式（Composite）
 * 
 * @author SJL
 * @date 2017/1/9
 */
public class CompositeActivity extends BaseActivity {
    private TextView tvDefine;
    private TextView tvUsage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composite);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("将对象组合成树形结构以表示“部分-整体”的层次结构，使得用户对单个对象和组合对象的使用具有一致性。");
        tvUsage.setText("1、将多个对象组合在一起进行操作，常用于表示树形结构中，例如二叉树，数等;");

        //简单使用
        TreeNode root = new TreeNode("root");
        TreeNode children1 = new TreeNode("children1");
        TreeNode children2 = new TreeNode("children2");
        TreeNode children11 = new TreeNode("children11");
        children1.add(children11);
        root.add(children1);
        root.add(children2);
    }
}
