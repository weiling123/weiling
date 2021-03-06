package com.beijing.beixin.ui.myself.address;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.beijing.beixin.R;
import com.beijing.beixin.R.layout;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.ui.base.BaseActivity;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 选择联系人页面
 * 
 * @author ouyanghao
 * 
 */
public class SelectPeopleActivity extends BaseActivity {

	Context mContext = null;
	/** 获取库Phon表字段 **/
	private static final String[] PHONES_PROJECTION = new String[] { Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,
			Phone.CONTACT_ID };
	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;
	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;
	/** 头像ID **/
	private static final int PHONES_PHOTO_ID_INDEX = 2;
	/** 联系人的ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;
	/** 联系人名称 **/
	private ArrayList<String> mContactsName = new ArrayList<String>();
	/** 联系人头像 **/
	private ArrayList<String> mContactsNumber = new ArrayList<String>();
	/** 联系人头像 **/
	private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();
	private ListView mListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_people);
		init();
	}

	/**
	 * 初始化页面
	 */
	public void init() {
		setNavigationTitle("选择联系人");
		mContext = this;
		mListView = (ListView) findViewById(R.id.select_people_listview);
		getPhoneContacts();
		MyListAdapter adapter = new MyListAdapter(this);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("peoplename", mContactsName.get(position));
				intent.putExtra("peopletel", mContactsNumber.get(position));
				setResult(2, intent);
				finish();
			}
		});
	}

	/**
	 * 得到手机通讯录联系人信息
	 * 
	 */
	private void getPhoneContacts() {
		ContentResolver resolver = mContext.getContentResolver();
		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 得到联系人名称
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
				// 得到联系人ID
				Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
				// 得到联系人头像ID
				Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
				// 得到联系人头像Bitamp
				Bitmap contactPhoto = null;
				// photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
				if (photoid > 0) {
					Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
					InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
					contactPhoto = BitmapFactory.decodeStream(input);
				} else {
					contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.icon_bg);
				}
				mContactsName.add(contactName);
				mContactsNumber.add(phoneNumber);
				mContactsPhonto.add(contactPhoto);
			}
			phoneCursor.close();
		}
	}

	/**
	 * 适配器
	 * 
	 * @author ouyanghao
	 * 
	 */
	class MyListAdapter extends BaseAdapter {

		public MyListAdapter(Context context) {
			mContext = context;
		}

		public int getCount() {
			// 设置绘制数量
			return mContactsName.size();
		}

		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView title = null;
			TextView text = null;
			if (convertView == null || position < mContactsNumber.size()) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_select_people, null);
				title = (TextView) convertView.findViewById(R.id.textname);
				text = (TextView) convertView.findViewById(R.id.texttel);
			}
			// 绘制联系人名称
			title.setText(mContactsName.get(position));
			// 绘制联系人号码
			text.setText(mContactsNumber.get(position));
			return convertView;
		}
	}
}
