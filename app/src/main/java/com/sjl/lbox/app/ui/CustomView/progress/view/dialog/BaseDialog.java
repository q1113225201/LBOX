package com.sjl.lbox.app.ui.CustomView.progress.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.listener.BaseListener;
import com.sjl.lbox.listener.ItemClickListener;

public class BaseDialog extends Dialog {

    private String tag = BaseDialog.class.getSimpleName();

    private Context mContext;

    private View view;

    private TextView tvTitle;

    private TextView tvContent;

    private EditText etContent;

    private Button btnConfirm;

    private Button btnCancel;

    private View viewCancel;

    private BaseListener confirmListener;

    private BaseListener cancelListener;

    private LinearLayout llContent;

    private LinearLayout llLvContent;

    private LinearLayout llBottom;

    private ListView lv;

    private ScrollView scrollView;

    private ItemClickListener itemClickListener;

    public BaseDialog(Context context) {
        super(context, R.style.baseDialogStyle);
        this.mContext = context;
        setCanceledOnTouchOutside(false);
    }

    public BaseDialog(Context context, BaseListener confirmListener) {
        super(context);
        this.confirmListener = confirmListener;
        setCanceledOnTouchOutside(false);
    }

    public BaseDialog(Context context, String title, String content, BaseListener confirmListener) {
        this(context, confirmListener);
        setTitle(title);
        setContent(content);
        setCanceledOnTouchOutside(false);
    }

    public BaseDialog(Context context, boolean cancelableOnTouchOutside) {
        super(context, R.style.baseDialogStyle);
        this.mContext = context;
        setCanceledOnTouchOutside(cancelableOnTouchOutside);
    }

    public void setLayout(int id) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(id, null);
    }

    @Override
    public void show() {
        if (view == null) {
            setDefalutLayout();
        }
        setContentView(view);
        Window window = this.getWindow();
        WindowManager.LayoutParams windowWM = window.getAttributes();
        window.setAttributes(windowWM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        findView();
        super.show();
    }

    private void setDefalutLayout() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.dialog_base, null);
    }

    private void findView() {
        tvTitle = (TextView) view.findViewById(R.id.base_dialog_title_tv);
        tvContent = (TextView) view.findViewById(R.id.base_dialog_content_tv);
        etContent = (EditText) view.findViewById(R.id.base_dialog_content_et);
        btnConfirm = (Button) view.findViewById(R.id.base_dialog_confirm_btn);
        btnCancel = (Button) view.findViewById(R.id.base_dialog_cancel_btn);
        viewCancel = view.findViewById(R.id.base_dialog_cancel_view);
        llContent = (LinearLayout) view.findViewById(R.id.base_dialog_content_ll);
        llLvContent = (LinearLayout) view.findViewById(R.id.base_dialog_content_lv_ll);
        llBottom = (LinearLayout) view.findViewById(R.id.base_dialog_bottom_ll);
        lv = (ListView) view.findViewById(R.id.base_dialog_content_lv);
        scrollView = (ScrollView) view.findViewById(R.id.base_dialog_sv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                if (itemClickListener != null) {
                    itemClickListener.itemClickListener(view, position);
                }
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (confirmListener != null) {
                    confirmListener.baseListener(v, etContent.getText().toString().trim());
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (cancelListener != null) {
                    cancelListener.baseListener(v, etContent.getText().toString().trim());
                }
            }
        });
    }

    public void setTitle(String title) {
        if (title != null) {
            tvTitle.setText(title);
        }
    }

    public void setContent(CharSequence content) {
        if (content != null) {
            tvContent.setText(content);
        }
    }

    public String getContent() {
        return tvContent.getText().toString();
    }

    public void setContentEt(String content) {
        if (content != null) {
            etContent.setText(content);
            etContent.setSelection(0, content.length());
        }
    }

    public String getContentEt() {
        return etContent.getText().toString();
    }

    public void setContentEtHint(String hint) {
        if (hint != null) {
            etContent.setHint(hint);
        }
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public TextView getTvContent() {
        return tvContent;
    }

    public void setTvContent(TextView tvContent) {
        this.tvContent = tvContent;
    }

    public EditText getEtContent() {
        return etContent;
    }

    public void setEtContent(EditText etContent) {
        this.etContent = etContent;
    }

    public Button getBtnConfirm() {
        return btnConfirm;
    }

    public void setBtnConfirm(Button btnConfirm) {
        this.btnConfirm = btnConfirm;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public View getViewCancel() {
        return viewCancel;
    }

    public void setBtnCancel(Button btnCancel) {
        this.btnCancel = btnCancel;
    }

    public BaseListener getConfirmListener() {
        return confirmListener;
    }

    public void setConfirmListener(BaseListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    public BaseListener getCancelListener() {
        return cancelListener;
    }

    public void setCancelListener(BaseListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public LinearLayout getLlContent() {
        return llContent;
    }

    public LinearLayout getLlLvContent() {
        return llLvContent;
    }

    public LinearLayout getLlBottom() {
        return llBottom;
    }

    public ListView getLv() {
        return lv;
    }

    public ScrollView getScrollView() {
        return scrollView;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            dismiss();
            if (cancelListener != null) {
                cancelListener.baseListener(btnCancel, etContent.getText().toString().trim());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
