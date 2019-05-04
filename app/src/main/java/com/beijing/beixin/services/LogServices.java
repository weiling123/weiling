package com.beijing.beixin.services;

import java.util.ArrayList;

import com.beijing.beixin.db.DataBaseConstDefine;
import com.beijing.beixin.db.DataBaseUtil;
import com.beijing.beixin.db.SQLiteDataBaseHelper;
import com.beijing.beixin.pojo.OperationLogs;

import android.content.Context;
import android.database.Cursor;

/**
 * 24,操作日志信息表service
 * 
 * @author wenjian
 * 
 */
public class LogServices implements DataBaseConstDefine {

	private static LogServices service;
	private static Context mContext;

	private LogServices() {
	}

	public static LogServices getInstance(Context context) {
		mContext = context;
		if (null == service) {
			service = new LogServices();
		}
		return service;
	}

	/**
	 * 根据ID查询日志信息
	 * 
	 * @return
	 */
	public ArrayList<OperationLogs> query(String werks) {
		ArrayList<OperationLogs> list = new ArrayList<OperationLogs>();
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		Cursor cursor = helper.query(TABLE_OPERATIONLOGS, OPERATIONLOGS_COLUMNS, OPERATIONLOGS_OID + "=? ",
				new String[] { werks });
		if (null != cursor) {
			int size = cursor.getCount();
			if (0 != size) {
				cursor.moveToFirst();
				for (int i = 0; i < size; i++) {
					OperationLogs logs = new OperationLogs();
					logs.setoId(cursor.getInt(cursor.getColumnIndex(OPERATIONLOGS_OID)));// id
					logs.setUserId(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_USERID)));// 用户id
					logs.setStartTime(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_STARTTIME)));// 开始时间
					logs.setEndTime(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_ENDTIME)));// 结束时间
					logs.setSendSys(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_SENDSYS)));// 发送系统
					logs.setReceiveSys(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_RECEIVESYS)));// 接收系统
					logs.setApiName(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_APINAME)));// 接口名称
					logs.setpData(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_PDATA)));// 传输数据
					logs.setResult(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_RESULT)));// 处理结果
					logs.setErrorInfo(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_ERRORINFO)));// 异常信息
					logs.setAddress(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_ADDRESS)));// 客户端ip
					logs.setDeviceBrand(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_DEVICEBRAND)));// 手机品牌
					logs.setDeviceModel(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_DEVICEMODEL)));// 手机型号
					logs.setDeviceInfo(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_DEVICEINFO)));// 手机设备详细信息
					logs.setRemark(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_REMARK)));// 备注
					logs.setpDataState(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_PDATASTATE)));// 提交状态
					logs.setCreateTime(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_CREATETIME)));// 创建时间
					logs.setCreateTime(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_APPVERSION)));// app版本号
					logs.setCreateTime(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_FUNCTION)));// 功能名称
					logs.setCreateTime(cursor.getString(cursor.getColumnIndex(OPERATIONLOGS_MODULE)));// 模块
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
	public boolean insert(OperationLogs logs) {
		// 处理结果标志
		boolean dealFalg = false;
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		try {
			helper.insert(TABLE_OPERATIONLOGS, OPERATIONLOGS_SHORT,
					new Object[] { logs.getUserId(), logs.getStartTime(), logs.getEndTime(), logs.getSendSys(),
							logs.getReceiveSys(), logs.getApiName(), logs.getpData(), logs.getResult(),
							logs.getErrorInfo(), logs.getAddress(), logs.getDeviceBrand(), logs.getDeviceModel(),
							logs.getDeviceInfo(), logs.getRemark(), logs.getpDataState(), logs.getCreateTime(),
							logs.getFunction(), logs.getAppVersion(), logs.getModule() });
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
	public boolean update(OperationLogs logs) {
		// 处理结果标志
		boolean dealFalg = false;
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		try {
			helper.update(TABLE_OPERATIONLOGS, OPERATIONLOGS_COLUMNS,
					new Object[] { logs.getoId(), logs.getUserId(), logs.getStartTime(), logs.getEndTime(),
							logs.getSendSys(), logs.getReceiveSys(), logs.getApiName(), logs.getpData(),
							logs.getResult(), logs.getErrorInfo(), logs.getAddress(), logs.getDeviceBrand(),
							logs.getDeviceModel(), logs.getDeviceInfo(), logs.getRemark(), logs.getpDataState(),
							logs.getUserId(), logs.getCreateTime(), logs.getFunction(), logs.getAppVersion(),
							logs.getModule() },
					OPERATIONLOGS_OID + "=? ", new String[] { logs.getoId() + "" });
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
			helper.delete(TABLE_OPERATIONLOGS, whereClause, whereArgs);
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
