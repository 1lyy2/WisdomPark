package com.zykj.carfigure.widget;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.zykj.carfigure.R;

public class ProgressDialog extends Dialog {

	private static ProgressDialog customProgressDialog = null;
	private static boolean canCancelTouchOuside;

	public ProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	protected ProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public ProgressDialog(Context context) {
		super(context);
	}

	@SuppressLint("InflateParams")
	public static ProgressDialog createDialog(Context context) {
		customProgressDialog = new ProgressDialog(context, R.style.ProgressDialog);
		View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog_layout, null);
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (customProgressDialog != null && canCancelTouchOuside) {
					if (customProgressDialog.isShowing()) {
						customProgressDialog.dismiss();
					}
				}
			}
		});
		customProgressDialog.setContentView(view);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		return customProgressDialog;
	}

	@Override
	public void setCanceledOnTouchOutside(boolean cancel) {
		// TODO Auto-generated method stub
		super.setCanceledOnTouchOutside(cancel);
		canCancelTouchOuside = cancel;
	}
	
	
}
