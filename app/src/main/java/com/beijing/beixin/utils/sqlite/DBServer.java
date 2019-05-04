package com.beijing.beixin.utils.sqlite;

import java.util.ArrayList;
import java.util.List;

import com.beijing.beixin.pojo.DataShop;
import com.beijing.beixin.utils.LogUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * sqlite操作
 * 
 * @author ouyanghao
 * 
 */
@SuppressWarnings("unused")
public class DBServer {

	private DBHelper dbhelper;

	public DBServer(Context context) {
		this.dbhelper = new DBHelper(context);
	}

	/**
	 * 添加浏览足迹
	 * 
	 * @param entity
	 */
	public void addFootInfo(FootInfo entity) {
		SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
		Object[] arrayOfObject = new Object[7];
		arrayOfObject[0] = entity.getFootinfoid();
		arrayOfObject[1] = entity.getFootinfoname();
		arrayOfObject[2] = entity.getFootinfoimage();
		arrayOfObject[3] = entity.getFootinfoprice();
		arrayOfObject[4] = entity.getFootinfoshopname();
		arrayOfObject[5] = entity.getFootinfooldprice();
		arrayOfObject[6] = entity.getLoginname();
		localSQLiteDatabase.execSQL(
				"insert into footinfo(footinfo_ids,footinfo_name,footinfo_image,footinfo_price,footinfo_shopname,footinfo_oldprice,loginname) values(?,?,?,?,?,?,?)",
				arrayOfObject);
		localSQLiteDatabase.close();
	}

	/**
	 * 添加搜索记录
	 * 
	 * @param entity
	 */
	public void addProduct(ProductSearch entity) {
		SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
		Object[] arrayOfObject = new Object[1];
		arrayOfObject[0] = entity.getProductname();
		localSQLiteDatabase.execSQL("insert into productsearch(product_name) values(?)", arrayOfObject);
		localSQLiteDatabase.close();
	}

	/**
	 * 清除数据搜索记录
	 * 
	 * @param entity
	 */
	public void delProduct() {
		SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
		localSQLiteDatabase.execSQL("delete from productsearch");
		localSQLiteDatabase.close();
	}

	/**
	 * 清除数据浏览足迹
	 * 
	 * @param entity
	 */
	public void delFootInfo() {
		SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
		localSQLiteDatabase.execSQL("delete from footinfo");
		localSQLiteDatabase.close();
	}

	/**
	 * 清空本地购物车
	 */
	public void emptyDataShopCart() {
		SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
		localSQLiteDatabase.execSQL("delete from shopcart");
		localSQLiteDatabase.close();
	}

	public DataShop[] getDataShopCart() {
		SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
		Cursor localCursor = localSQLiteDatabase.rawQuery("select * from shopcart", null);
		ArrayList<DataShop> arrayList = new ArrayList<DataShop>();
		while (localCursor.moveToNext()) {
			String shopInfId = localCursor.getString(localCursor.getColumnIndex("shopInfId"));
			int number = localCursor.getInt(localCursor.getColumnIndex("number"));
			int checkState = localCursor.getInt(localCursor.getColumnIndex("checkState"));
			String skuId = localCursor.getString(localCursor.getColumnIndex("skuId"));
			DataShop dataShop = new DataShop(shopInfId, skuId, number, checkState);
			arrayList.add(dataShop);
		}

		if (arrayList.size() == 0) {
			return null;
		}

		DataShop[] dataShops = new DataShop[arrayList.size()];
		return arrayList.toArray(dataShops);
	}

	/***
	 * 更新本地购物车
	 */
	public void updateDataShopCart(DataShop[] dataShops) {
		SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
		localSQLiteDatabase.execSQL("delete from shopcart");
		if (dataShops != null && dataShops.length > 0) {
			localSQLiteDatabase.beginTransaction();
			for (DataShop item : dataShops) {
				Object[] arrayOfObject = new Object[4];
				arrayOfObject[0] = item.getShopInfId();
				arrayOfObject[1] = item.getNumber();
				arrayOfObject[2] = item.getCheckState();
				LogUtil.e("item.getCheckState()", item.getCheckState() + "");
				arrayOfObject[3] = item.getSkuId();
				localSQLiteDatabase.execSQL("insert into shopcart(shopInfId,number,checkState,skuId) values(?,?,?,?)",
						arrayOfObject);
			}
			localSQLiteDatabase.setTransactionSuccessful();
			localSQLiteDatabase.endTransaction();
		}
		localSQLiteDatabase.close();
	}

	/**
	 * 获取浏览足迹
	 * 
	 */
	public List<FootInfo> findAllFootInfo(String loginname) {
		List<FootInfo> localArrayList = new ArrayList<FootInfo>();
		SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
		// 删除重复数据
		localSQLiteDatabase.execSQL(
				"delete from footinfo where rowid not in(select max(rowid) from footinfo group by footinfo_ids)");
		Cursor localCursor = localSQLiteDatabase.rawQuery("select distinct * from footinfo where loginname=" + loginname
				+ " order by footinfo_id desc limit 20 offset 0", null);
		while (localCursor.moveToNext()) {
			FootInfo temp = new FootInfo();
			temp.setFootinfoid(localCursor.getString(localCursor.getColumnIndex("footinfo_ids")));
			temp.setLoginname(localCursor.getString(localCursor.getColumnIndex("loginname")));
			temp.setFootinfoname(localCursor.getString(localCursor.getColumnIndex("footinfo_name")));
			temp.setFootinfoimage(localCursor.getString(localCursor.getColumnIndex("footinfo_image")));
			temp.setFootinfoprice(localCursor.getString(localCursor.getColumnIndex("footinfo_price")));
			temp.setFootinfoshopname(localCursor.getString(localCursor.getColumnIndex("footinfo_shopname")));
			temp.setFootinfooldprice(localCursor.getString(localCursor.getColumnIndex("footinfo_oldprice")));
			localArrayList.add(temp);
		}
		localSQLiteDatabase.close();
		return localArrayList;
	}

	/**
	 * 获取搜索记录
	 */
	public List<ProductSearch> findAllProduct() {
		List<ProductSearch> localArrayList = new ArrayList<ProductSearch>();
		SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
		Cursor localCursor = localSQLiteDatabase
				.rawQuery("select * from productsearch order by product_id desc limit 20 offset 0", null);
		while (localCursor.moveToNext()) {
			ProductSearch temp = new ProductSearch();
			temp.setProductname(localCursor.getString(localCursor.getColumnIndex("product_name")));
			localArrayList.add(temp);
		}
		// 删除重复数据
		localSQLiteDatabase.execSQL(
				"delete from productsearch where rowid not in(select max(rowid) from productsearch group by product_name)");
		localSQLiteDatabase.close();
		return localArrayList;
	}
}
