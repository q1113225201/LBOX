package com.sjl.lbox.app.ui.CustomView.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sjl.lbox.R;


public class BaseProgressDialog extends Dialog {
	private Context mContext;

	private View view;
	
	private ProgressBar pb;
	
	private TextView tvMsg;
	
	public BaseProgressDialog(Context context) {
		super(context, R.style.progressDialogStyle);
		this.mContext = context;
		setCanceledOnTouchOutside(false);
	}

	public BaseProgressDialog(Context context, boolean cancelableOnTouchOutside) {
		super(context, R.style.progressDialogStyle);
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
		window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		window.setGravity(Gravity.CENTER);
		findView();
		super.show();
	}

	private void setDefalutLayout() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		view = inflater.inflate(R.layout.app_base_progress_dialog, null);
	}

	private void findView() {
		pb=(ProgressBar) view.findViewById(R.id.base_progress_pb);
		tvMsg=(TextView) view.findViewById(R.id.base_progress_message_tv);
	}

	public ProgressBar getPb() {
		return pb;
	}

	public TextView getTvMsg() {
		return tvMsg;
	}

	public void setMessage(String msg) {
		this.tvMsg.setText(msg);
	}
}
