package com.beijing.beixin.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class HotProduct implements Parcelable {
	private String title;

	private String pageModuleId;

	public HotProduct(String title, String pageModuleId) {
		this.title = title;
		this.pageModuleId = pageModuleId;
	}

	protected HotProduct(Parcel in) {
		title = in.readString();
		pageModuleId = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(pageModuleId);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<HotProduct> CREATOR = new Creator<HotProduct>() {
		@Override
		public HotProduct createFromParcel(Parcel in) {
			return new HotProduct(in);
		}

		@Override
		public HotProduct[] newArray(int size) {
			return new HotProduct[size];
		}
	};

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPageModuleId() {
		return pageModuleId;
	}

	public void setPageModuleId(String pageModuleId) {
		this.pageModuleId = pageModuleId;
	}
}
