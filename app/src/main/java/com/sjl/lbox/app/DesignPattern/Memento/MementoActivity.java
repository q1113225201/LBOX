package com.sjl.lbox.app.DesignPattern.Memento;

import android.os.Bundle;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 备忘录模式（Memento）
 * 
 * @author SJL
 * @date 2017/1/6
 */
public class MementoActivity extends BaseActivity {
    private TextView tvDefine;
    private TextView tvUsage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memento);

        initView();
    }

    private void initView() {
        tvDefine = (TextView) findViewById(R.id.tvDefine);
        tvUsage = (TextView) findViewById(R.id.tvUsage);
        tvDefine.setText("在不破坏封闭的前提下，捕获一个对象的内部状态，并在对象之外保存这个状态，以后可将对象恢复到原先保存的状态中。");
        tvUsage.setText("1、需要保存和恢复数据的相关状态场景；");

        //简单使用
        //创建游戏
        Game game = new Game();
        game.setState("满血");
        //保存进度
        Storage storage = new Storage(new Memento(game.getState()));
        System.out.println("保存的状态："+game.getState());
        game.setState("死亡");
        System.out.println("游戏后的状态："+game.getState());
        game.restoreMemento(storage.getMemento());
        System.out.println("载入进度后的状态："+game.getState());
    }
}
