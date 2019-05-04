package com.beijing.beixin.pojo;

import java.util.ArrayList;

import android.text.TextUtils;

public class ProductDetailMore {

	private Result[] result;

	public Result[] getResult() {
		return result;
	}

	public void setResult(Result[] result) {
		this.result = result;
	}

	public NameAndValue[] getAttrDicVoList() {
		if (result == null || result.length == 0) {
			return null;
		}

		ArrayList<NameAndValue> arrayList = new ArrayList<NameAndValue>();

		for (int i = 0; i < result.length; i++) {
			NameAndValue[] appAttrDicVoList = result[i].getAppAttrDicVoList();
			if (appAttrDicVoList == null || appAttrDicVoList.length == 0) {
				continue;
			}

			for (int j = 0; j < appAttrDicVoList.length; j++) {
				if (TextUtils.isEmpty(appAttrDicVoList[j].getName())) {
					continue;
				}

				if (TextUtils.isEmpty(appAttrDicVoList[j].getValue())) {
					continue;
				}

				arrayList.add(appAttrDicVoList[j]);
			}
		}

		NameAndValue[] nameAndValues = new NameAndValue[arrayList.size()];
		return arrayList.toArray(nameAndValues);

	}

}
