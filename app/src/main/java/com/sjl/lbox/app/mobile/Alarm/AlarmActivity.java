package com.sjl.lbox.app.mobile.Alarm;

import android.os.Bundle;
import android.view.View;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.CalendarUtil;
import com.sjl.lbox.util.PermisstionUtil;
import com.sjl.lbox.util.ToastUtil;

public class AlarmActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        initView();
    }

    private void initView() {
        findViewById(R.id.btnCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create();
            }
        });
    }

    private void create() {
        PermisstionUtil.requestPermissions(this, PermisstionUtil.CALENDAR, 100, "日历权限", new PermisstionUtil.OnPermissionResult() {
            @Override
            public void granted(int requestCode) {
                CalendarUtil.addCalendarEventRemind(mContext, "标题", "描述", System.currentTimeMillis() + 1000 * 15, System.currentTimeMillis() + 1000 * 25, 1, new CalendarUtil.onCalendarRemindListener() {
                    @Override
                    public void onFailed(Status error_code) {
                        ToastUtil.showToast(mContext, "Error:" + error_code);
                    }

                    @Override
                    public void onSuccess() {
                        ToastUtil.showToast(mContext, "设置成功");
                    }
                });
            }

            @Override
            public void denied(int requestCode) {
                ToastUtil.showToast(mContext, "权限被禁止");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermisstionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
