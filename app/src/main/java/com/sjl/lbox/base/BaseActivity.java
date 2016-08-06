package com.sjl.lbox.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/**
 * Activity基类
 *
 * @author SJL
 * @date 2016/8/6 14:19
 */
public class BaseActivity extends Activity {

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }
}
