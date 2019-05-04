package com.beijing.beixin.utils.sqlite;

import com.beijing.beixin.constants.SystemConfig;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * db助手类
 * 
 * @author ouyanghao
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		super(context, "foot_info.db", null, SystemConfig.DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		String footinfoSQL = "CREATE TABLE footinfo(footinfo_id varchar(10) primary key,"
				+ "footinfo_ids varchar(20),footinfo_name varchar(20),footinfo_image varchar(20),footinfo_price varchar(20),footinfo_shopname varchar(20),footinfo_oldprice,loginname varchar(20))";
		String productsearchSQL = "CREATE TABLE productsearch(product_id varchar(10) primary key,"
				+ "product_name varchar(20))";
		String shopCart = "CREATE TABLE shopcart(shopInfId TEXT,number INTEGER,checkState INTEGER,skuId TEXT)";
		arg0.execSQL(footinfoSQL);
		arg0.execSQL(productsearchSQL);
		arg0.execSQL(shopCart);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS " + "footinfo");
		db.execSQL("DROP TABLE IF EXISTS " + "productsearch");
		db.execSQL("DROP TABLE IF EXISTS " + "shopcart");
		onCreate(db);
	}
}
