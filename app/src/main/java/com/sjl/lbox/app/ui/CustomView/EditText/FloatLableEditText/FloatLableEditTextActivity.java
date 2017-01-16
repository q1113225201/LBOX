package com.sjl.lbox.app.ui.CustomView.EditText.FloatLableEditText;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.sjl.lbox.R;
import com.sjl.lbox.base.BaseActivity;

/**
 * 浮动提示输入框
 *
 * @author SJL
 * @date 2017/1/16
 */
public class FloatLableEditTextActivity extends BaseActivity {
    private TextInputLayout etTextInput;
    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_lable_edit_text);

        initView();
    }

    private void initView() {
        etTextInput = (TextInputLayout) findViewById(R.id.etTextInput);
        etInput = (EditText) findViewById(R.id.etInput);

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<2&&s.length()>0){
                    etTextInput.setError("不能少于两位");
                    etTextInput.setErrorEnabled(true);
                    etTextInput.setHint("错误");
                    etTextInput.setHintTextAppearance(R.style.ErrorStyle);
                }else{
                    etTextInput.setErrorEnabled(false);
                    etTextInput.setHint("姓名");
                    etTextInput.setHintTextAppearance(R.style.TextViewCommonStyle);
                }
            }
        });
    }
}
