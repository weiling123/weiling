package com.beijing.beixin.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 * @author ouyanghao
 * 
 */
public class MyDialog extends Dialog {

	public MyDialog(Context context, View layout, int style) {
		super(context, style);
		setContentView(layout);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		params.x = 0;
		params.y = 0;
		params.gravity = Gravity.CENTER;
		params.width = (int) (display.getWidth() * 0.7);
		params.height = 500;
		window.setAttributes(params);
	}
}
