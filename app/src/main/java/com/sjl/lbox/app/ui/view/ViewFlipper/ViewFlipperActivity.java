package com.sjl.lbox.app.ui.view.ViewFlipper;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

public class ViewFlipperActivity extends BaseActivity {
    ViewFlipper viewFlipperVertical;
    ViewFlipper viewFlipperHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);

        initView();
        initData();
    }

    private void initView() {
        viewFlipperVertical = findViewById(R.id.view_flipper_vertical);
        viewFlipperHorizontal = findViewById(R.id.view_flipper_horizontal);
    }

    private void initData() {
        initVertical();
        initHorizontal();
    }

    private void initVertical() {
        viewFlipperVertical.addView(buildTextView("vertical1"));
        viewFlipperVertical.addView(buildTextView("vertical2"));
        viewFlipperVertical.addView(buildTextView("vertical3"));
        viewFlipperVertical.startFlipping();
    }

    private void initHorizontal() {
        viewFlipperHorizontal.addView(buildImageView(R.drawable.frame1));
        viewFlipperHorizontal.addView(buildImageView(R.drawable.frame2));
        viewFlipperHorizontal.addView(buildImageView(R.drawable.frame3));
        viewFlipperHorizontal.addView(buildImageView(R.drawable.frame4));
        viewFlipperHorizontal.addView(buildImageView(R.drawable.frame5));
        viewFlipperHorizontal.addView(buildImageView(R.drawable.frame6));
        viewFlipperHorizontal.setAutoStart(true);
        viewFlipperHorizontal.startFlipping();
    }

    private View buildImageView(int res) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(res);
        return imageView;
    }

    private View buildTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.mipmap.ic_launcher), null, null, null);
        return textView;
    }
}
