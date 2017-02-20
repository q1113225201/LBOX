package com.sjl.lbox.app.ui.CustomView.EditText.PwdEditText;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.EditText.PwdEditText.view.PwdEditText;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ToastUtil;

/**
 * 仿支付宝密码输入框演示
 *
 * @author SJL
 * @date 2016/11/30 0:08
 */
public class PwdEditTextActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {
    private PwdEditText etPwd;

    private SeekBar seekBarCorner;
    private SeekBar seekBarLength;
    private SeekBar seekBarLine;
    private SeekBar seekRadius;
    private ToggleButton tbShowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_edit_text);
        initView();
    }

    private void initView() {
        etPwd = (PwdEditText) findViewById(R.id.etPwd);
        etPwd.setOnTextEndListener(new PwdEditText.OnTextEndListener() {
            @Override
            public void onTextEnd(String str) {
                ToastUtil.showToast(mContext, str);
            }
        });
        seekBarCorner = (SeekBar) findViewById(R.id.seekBarCorner);
        seekBarCorner.setOnSeekBarChangeListener(this);
        seekBarCorner.setProgress((int) (etPwd.getBorderCornerRadius() * 10));
        seekBarLength = (SeekBar) findViewById(R.id.seekBarLength);
        seekBarLength.setOnSeekBarChangeListener(this);
        seekBarLength.setProgress(etPwd.getPasswordLength());
        seekBarLine = (SeekBar) findViewById(R.id.seekBarLine);
        seekBarLine.setOnSeekBarChangeListener(this);
        seekBarLine.setProgress((int) (etPwd.getLineWidth() * 10));
        seekRadius = (SeekBar) findViewById(R.id.seekRadius);
        seekRadius.setOnSeekBarChangeListener(this);
        seekRadius.setProgress((int) (etPwd.getDotRadius() * 10));
        tbShowText = (ToggleButton) findViewById(R.id.tbShowText);
        tbShowText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                etPwd.setShowContent(isChecked);
            }
        });
    }

    public void clear(View view) {
        etPwd.clear();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekBarCorner://圆角
                etPwd.setBorderCornerRadius(progress / 10);
                break;
            case R.id.seekBarLength://密码长度
                etPwd.setPasswordLength(progress);
                break;
            case R.id.seekBarLine://线条粗细
                etPwd.setLineWidth(progress / 10);
                break;
            case R.id.seekRadius://点半径
                etPwd.setDotRadius(progress / 10);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
