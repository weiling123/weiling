package com.beijing.beixin.widget;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnScrollListener;

import com.beijing.beixin.R;
import com.beijing.beixin.pojo.AllCityList;
import com.beijing.beixin.pojo.AllProvinceList;
import com.beijing.beixin.pojo.ZoneBean;

/***
 * 日期布局类
 * 
 * @author liangshibin
 * 
 */
public class BankPicker extends FrameLayout {

	private NumberPicker mProvinceSpinner;
	private NumberPicker mCitySpinner;
	private NumberPicker mDistrictSpinner;
	private Context mContext;
	private Dialog dialog;
	private List<ZoneBean> list = new ArrayList<ZoneBean>();
	/**
	 * 省的名字
	 */
	private String[] provinceNames;
	/**
	 * 省的ID
	 */
	private int[] provinceIds;
	/**
	 * 市的名字
	 */
	private String[] cityNames;
	/**
	 * 市的ID
	 */
	private int[] cityIds;
	/**
	 * 区的名字
	 */
	private String[] districtNames;
	/**
	 * 区的ID
	 */
	private int[] districtIds;
	String[] wait = { "请选择" };
	/**
	 * 选中的省
	 */
	private String mProvinceName;
	/**
	 * 选中的市
	 */
	private String mCityName;
	/**
	 * 选中的区
	 */
	private String mDistrictName;
	/**
	 * 选中城市的ID
	 */
	private int mCitysId;
	/**
	 * 选中区的ID
	 */
	private int mDistrictId;
	private List<AllProvinceList> mList = new ArrayList<AllProvinceList>();

	public BankPicker(Context context, List<AllProvinceList> list) {
		super(context);
		mContext = context;
		mList = list;
		inflate(context, R.layout.view_bank_header, this);
		switchList(mList);
		initView();
		setListener();
	}

	/**
	 * 分离省list集合，变为数组
	 */
	private void switchList(List<AllProvinceList> list) {
		provinceNames = new String[list.size()];
		provinceIds = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			provinceNames[i] = list.get(i).getName();
			provinceIds[i] = list.get(i).getId();
		}
	}

	/**
	 * 分离省list集合，变为数组
	 */
	private void switchCityList(List<AllCityList> list) {
		cityNames = new String[list.size()];
		cityIds = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			cityNames[i] = list.get(i).getName();
			cityIds[i] = list.get(i).getId();
		}
	}

	/**
	 * 分离省list集合，变为数组
	 */
	private void switchDistrictList(List<ZoneBean> list) {
		districtNames = new String[list.size()];
		districtIds = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			districtNames[i] = list.get(i).getName();
			districtIds[i] = list.get(i).getId();
		}
	}

	/**
	 * 初始化组件，并且赋初始值
	 */
	private void initView() {
		mProvinceSpinner = (NumberPicker) this.findViewById(R.id.area_province);
		mProvinceSpinner.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		mCitySpinner = (NumberPicker) this.findViewById(R.id.area_city);
		mCitySpinner.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		mDistrictSpinner = (NumberPicker) this.findViewById(R.id.area_district);
		mDistrictSpinner.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		// mCitySpinner.setDisplayedValues(wait);
		// mDistrictSpinner.setDisplayedValues(wait);
		if (provinceNames.length > 0) {
			mProvinceName = provinceNames[0];
		}
		mProvinceSpinner.setDisplayedValues(provinceNames);
		mProvinceSpinner.setMinValue(0);
		mProvinceSpinner.setMaxValue(provinceNames.length - 1);
		switchCityList(mList.get(0).getList());
		switchDistrictList(mList.get(0).getList().get(0).getList());
		mCitySpinner.setDisplayedValues(cityNames);
		mCitySpinner.setMinValue(0);
		mCitySpinner.setMaxValue(cityNames.length - 1);
		mDistrictSpinner.setDisplayedValues(districtNames);
		mDistrictSpinner.setMinValue(0);
		mDistrictSpinner.setMaxValue(districtNames.length - 1);

		mProvinceName = provinceNames[mProvinceSpinner.getValue()];
		mCityName = cityNames[mCitySpinner.getValue()];
		mDistrictName = districtNames[mDistrictSpinner.getValue()];
		mDistrictId = districtIds[mDistrictSpinner.getValue()];
	}

	private void setListener() {
		mProvinceSpinner.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChange(NumberPicker view, int scrollState) {
				if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
					switchCityList(mList.get(mProvinceSpinner.getValue()).getList());
					switchDistrictList(mList.get(mProvinceSpinner.getValue()).getList().get(0).getList());
					mCitySpinner.setMinValue(0);
					if (cityNames.length - 1 > mCitySpinner.getMaxValue()) {
						mCitySpinner.setDisplayedValues(cityNames);
						mCitySpinner.setMaxValue(cityNames.length - 1);
					} else {
						mCitySpinner.setMaxValue(cityNames.length - 1);
						mCitySpinner.setDisplayedValues(cityNames);
					}
					mDistrictSpinner.setMinValue(0);
					if (districtNames.length - 1 > mDistrictSpinner.getMaxValue()) {
						mDistrictSpinner.setDisplayedValues(districtNames);
						mDistrictSpinner.setMaxValue(districtNames.length - 1);
					} else {
						mDistrictSpinner.setMaxValue(districtNames.length - 1);
						mDistrictSpinner.setDisplayedValues(districtNames);
					}

					mProvinceName = provinceNames[mProvinceSpinner.getValue()];
					mCityName = cityNames[mCitySpinner.getValue()];
					mDistrictName = districtNames[mDistrictSpinner.getValue()];
					mDistrictId = districtIds[mDistrictSpinner.getValue()];
				}
			}
		});
		mCitySpinner.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChange(NumberPicker view, int scrollState) {
				if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
					switchDistrictList(
							mList.get(mProvinceSpinner.getValue()).getList().get(mCitySpinner.getValue()).getList());

					mDistrictSpinner.setMinValue(0);
					if (districtNames.length - 1 > mDistrictSpinner.getMaxValue()) {
						mDistrictSpinner.setDisplayedValues(districtNames);
						mDistrictSpinner.setMaxValue(districtNames.length - 1);
					} else {
						mDistrictSpinner.setMaxValue(districtNames.length - 1);
						mDistrictSpinner.setDisplayedValues(districtNames);
					}

					mCityName = cityNames[mCitySpinner.getValue()];
					mDistrictName = districtNames[mDistrictSpinner.getValue()];
					mDistrictId = districtIds[mDistrictSpinner.getValue()];
				}
			}
		});
		mDistrictSpinner.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChange(NumberPicker view, int scrollState) {
				if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
					mDistrictName = districtNames[mDistrictSpinner.getValue()];
					mDistrictId = districtIds[mDistrictSpinner.getValue()];
				}
			}
		});
	}

	public String getmProvinceName() {
		return mProvinceName;
	}

	public void setmProvinceName(String mProvinceName) {
		this.mProvinceName = mProvinceName;
	}

	public String getmCityName() {
		return mCityName;
	}

	public void setmCityName(String mCityName) {
		this.mCityName = mCityName;
	}

	public String getmDistrictName() {
		return mDistrictName;
	}

	public void setmDistrictName(String mDistrictName) {
		this.mDistrictName = mDistrictName;
	}

	public int getmCitysId() {
		return mCitysId;
	}

	public void setmCitysId(int mCitysId) {
		this.mCitysId = mCitysId;
	}

	public int getmDistrictId() {
		return mDistrictId;
	}

	public void setmDistrictId(int mDistrictId) {
		this.mDistrictId = mDistrictId;
	}

}
