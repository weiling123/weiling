package com.beijing.beixin.widget;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import com.beijing.beixin.R;
import com.beijing.beixin.pojo.AllProvinceList;
import com.beijing.beixin.utils.Utils;

/***
 * 日期控件
 * 
 * @author xk.hou
 * 
 */
public class AreaDialog extends Dialog implements android.view.View.OnClickListener {

	private BankPicker picker;
	private Context context;

	private Button ok;

	private Button cache;

	public AreaDialog(Context context, boolean cancelable, String[] city, int[] cityId,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.context = context;
		// initView(list);
	}

	public AreaDialog(Context context, List<AllProvinceList> list) {
		super(context, R.style.dialog_choose_photo);
		this.context = context;
		initView(list);
	}
	// public AreaDialog(Context context,String[] city,int[] cityId) {
	// super(context, R.style.dialog_choose_photo);
	// this.context = context;
	// initView(city,cityId);
	// }

	private void initView(List<AllProvinceList> list) {
		picker = new BankPicker(context, list);
		setCancelable(true);
		this.setContentView(picker);
		Window window = getWindow();
		LayoutParams attributes = window.getAttributes();
		attributes.gravity = Gravity.BOTTOM;
		attributes.height = -2;
		attributes.width = Utils.getWidth(context);
		window.setAttributes(attributes);
		ok = (Button) picker.findViewById(R.id.dialog_ok);
		cache = (Button) picker.findViewById(R.id.dialog_cache);
		cache.setOnClickListener(this);
		ok.setOnClickListener(this);
	}

	public interface OnDateTimeSetListener {
		void OnDateTimeSet(Dialog dialog, long date);
	}

	public Button getOkButton() {
		return ok;
	}

	@Override
	public void onClick(View v) {
		AreaDialog.this.dismiss();
	}

	public String getProvinceName() {
		String mProvinceName = picker.getmProvinceName();

		if (picker != null) {
			return mProvinceName;
		}
		return "";
	}

	public String getmCityName() {
		String mCityName = picker.getmCityName();

		if (picker != null) {
			return mCityName;
		}
		return "";
	}

	public String getmDistrictName() {
		String mDistrictName = picker.getmDistrictName();

		if (picker != null) {
			return mDistrictName;
		}
		return "";
	}

	public int getmDistrictId() {
		int mDistrictId = picker.getmDistrictId();
		int mCitysId = picker.getmCitysId();

		if (picker != null && mDistrictId != 0) {
			return mDistrictId;
		}
		if (picker != null && mCitysId != 0) {
			return mCitysId;
		}
		return 0;
	}

}
