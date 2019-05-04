package com.beijing.beixin.services;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.beijing.beixin.db.DataBaseConstDefine;
import com.beijing.beixin.db.DataBaseUtil;
import com.beijing.beixin.db.SQLiteDataBaseHelper;
import com.beijing.beixin.pojo.ErrorLogs;

/**
 * 24,日志信息表service
 * 
 * @author wenjian
 * 
 */
public class ErrorLogServices implements DataBaseConstDefine {

	private static ErrorLogServices service;
	private static Context mContext;

	private ErrorLogServices() {
	}

	public static ErrorLogServices getInstance(Context context) {
		mContext = context;
		if (null == service) {
			service = new ErrorLogServices();
		}
		return service;
	}

	/**
	 * 根据ID查询日志信息
	 * 
	 * @return
	 */
	public ArrayList<ErrorLogs> query(String werks) {
		ArrayList<ErrorLogs> list = new ArrayList<ErrorLogs>();
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		Cursor cursor = helper.query(TABLE_ERRORLOGS, ERRORLOGS_COLUMNS, ERRORLOGS_EID + "=? ", new String[] { werks });
		if (null != cursor) {
			int size = cursor.getCount();
			if (0 != size) {
				cursor.moveToFirst();
				for (int i = 0; i < size; i++) {
					ErrorLogs logs = new ErrorLogs();
					logs.seteId(cursor.getInt(cursor.getColumnIndex(ERRORLOGS_EID)));// id
					logs.setUserId(cursor.getString(cursor.getColumnIndex(ERRORLOGS_USERID)));// 调用者账号
					logs.setStartTime(cursor.getString(cursor.getColumnIndex(ERRORLOGS_STARTTIME)));// 发送系统
					logs.setEndTime(cursor.getString(cursor.getColumnIndex(ERRORLOGS_ENDTIME)));// 接收
					logs.setSendSys(cursor.getString(cursor.getColumnIndex(ERRORLOGS_SENDSYS)));// 接口名称
					logs.setReceiveSys(cursor.getString(cursor.getColumnIndex(ERRORLOGS_RECEIVESYS)));// 开始时间
					logs.setApiName(cursor.getString(cursor.getColumnIndex(ERRORLOGS_APINAME)));// 结束时间
					logs.setpData(cursor.getString(cursor.getColumnIndex(ERRORLOGS_PDATA)));// 传输数据
					logs.setResult(cursor.getString(cursor.getColumnIndex(ERRORLOGS_RESULT)));// 处理结果
					logs.setErrorInfo(cursor.getString(cursor.getColumnIndex(ERRORLOGS_ERRORINFO)));// 异常信息
					logs.setAddress(cursor.getString(cursor.getColumnIndex(ERRORLOGS_ADDRESS)));// 提交状态
					logs.setDeviceBrand(cursor.getString(cursor.getColumnIndex(ERRORLOGS_DEVICEBRAND)));// 提交状态
					logs.setDeviceModel(cursor.getString(cursor.getColumnIndex(ERRORLOGS_DEVICEMODEL)));// 提交状态
					logs.setDeviceInfo(cursor.getString(cursor.getColumnIndex(ERRORLOGS_DEVICEINFO)));// 提交状态
					logs.setRemark(cursor.getString(cursor.getColumnIndex(ERRORLOGS_REMARK)));// 提交状态
					logs.setpData(cursor.getString(cursor.getColumnIndex(ERRORLOGS_PDATASTATE)));// 提交状态
					logs.setCreateTime(cursor.getString(cursor.getColumnIndex(ERRORLOGS_CREATETIME)));// 提交状态
					cursor.moveToNext();
					list.add(logs);
				}
			}
			cursor.close();
		}
		return list;
	}

	/**
	 * 插入
	 * 
	 * @param Logs
	 * @return
	 */
	public boolean insert(ErrorLogs logs) {
		// 处理结果标志
		boolean dealFalg = false;
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		try {
			helper.insert(TABLE_ERRORLOGS, ERRORLOGS_SHORT,
					new Object[] { logs.getUserId(), logs.getStartTime(), logs.getEndTime(), logs.getSendSys(),
							logs.getReceiveSys(), logs.getApiName(), logs.getpData(), logs.getResult(),
							logs.getErrorInfo(), logs.getAddress(), logs.getDeviceBrand(), logs.getDeviceModel(),
							logs.getDeviceInfo(), logs.getRemark(), logs.getCreateTime(), logs.getStatus() });
			dealFalg = true;
			// 设置事物处理成功标志
			helper.transactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			dealFalg = false;
		} finally {
			// 提交事物/回滚事物
			helper.endTransaction();
		}
		return dealFalg;
	}

	/**
	 * 更新
	 * 
	 * @param Logs
	 * @return
	 */
	public boolean update(ErrorLogs logs) {
		// 处理结果标志
		boolean dealFalg = false;
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		try {
			helper.update(TABLE_ERRORLOGS, ERRORLOGS_COLUMNS,
					new Object[] { logs.geteId(), logs.getUserId(), logs.getStartTime(), logs.getEndTime(),
							logs.getSendSys(), logs.getReceiveSys(), logs.getApiName(), logs.getpData(),
							logs.getResult(), logs.getErrorInfo(), logs.getAddress(), logs.getDeviceBrand(),
							logs.getDeviceModel(), logs.getDeviceInfo(), logs.getRemark(), logs.getCreateTime(),
							logs.getStatus() },
					ERRORLOGS_EID + "=? ", new String[] { logs.geteId() + "" });
			dealFalg = true;
			// 设置事物处理成功标志
			helper.transactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			dealFalg = false;
		} finally {
			// 提交事物/回滚事物
			helper.endTransaction();
		}
		return dealFalg;
	}

	/**
	 * 通用根据条件删除 helper
	 * 
	 * @param list
	 */
	public boolean delete(String whereClause, String[] whereArgs) {
		boolean htag = false;
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		try {
			helper.delete(TABLE_ERRORLOGS, whereClause, whereArgs);
			// 设置事务处理成功标志
			helper.transactionSuccessful();
			htag = true;
		} catch (Exception e) {
			e.printStackTrace();
			htag = false;
		} finally {
			// 提交事务/回滚事务
			helper.endTransaction();
		}
		return htag;
	}
}
