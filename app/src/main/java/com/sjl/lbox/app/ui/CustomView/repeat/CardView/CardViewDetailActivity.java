package com.sjl.lbox.app.ui.CustomView.repeat.CardView;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 卡片视图CardView详情
 *
 * @author SJL
 * @date 2017/1/17
 */
public class CardViewDetailActivity extends BaseActivity {
    private CardView cardView;
    private TextView tvCardDetailTitle;
    private TextView tvCardDetailContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_detail);

        initView();
    }

    private void initView() {
        cardView = (CardView) findViewById(R.id.cardView);
        cardView.setTransitionName("card");

        tvCardDetailTitle = (TextView) findViewById(R.id.tvCardDetailTitle);
        tvCardDetailContent = (TextView) findViewById(R.id.tvCardDetailContent);
        int position = (int) getIntent().getExtras().get("position");
        tvCardDetailTitle.setText("详情标题"+position);
        tvCardDetailContent.setText("详情内容"+position);
    }
}