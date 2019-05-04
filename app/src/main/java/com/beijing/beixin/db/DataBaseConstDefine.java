package com.beijing.beixin.db;

/**
 * 以下存放db的字段 建表语句 /查询字段 将字段定义成static便于 批量修改字段
 * 
 * @author wenjian
 * 
 */
public interface DataBaseConstDefine {

	/**
	 * 版本
	 */
	public static final int VERSION = 1;
	/**
	 * *****************************24,日志信息表****************
	 */
	/**
	 * 24,操作日志表
	 */
	public static final String TABLE_OPERATIONLOGS = "OPERATIONLOGS";
	/**
	 * id
	 */
	public static final String OPERATIONLOGS_OID = "OID";
	/**
	 * 用户id
	 */
	public static final String OPERATIONLOGS_USERID = "USER_ID";
	/**
	 * 开始时间
	 */
	public static final String OPERATIONLOGS_STARTTIME = "STARTTIME";
	/**
	 * 结束时间
	 */
	public static final String OPERATIONLOGS_ENDTIME = "ENDTIME";
	/**
	 * 发送系统
	 */
	public static final String OPERATIONLOGS_SENDSYS = "SENDSYS";
	/**
	 * 接收系统
	 */
	public static final String OPERATIONLOGS_RECEIVESYS = "RECEIVESYS";
	/**
	 * 接口名称
	 */
	public static final String OPERATIONLOGS_APINAME = "APINAME";
	/**
	 * 传输数据
	 */
	public static final String OPERATIONLOGS_PDATA = "PDATA";
	/**
	 * 处理结果 成功S 出错E
	 */
	public static final String OPERATIONLOGS_RESULT = "RESULT";
	/**
	 * 异常信息
	 */
	public static final String OPERATIONLOGS_ERRORINFO = "ERRORINFO";
	/**
	 * 客户端ip
	 */
	public static final String OPERATIONLOGS_ADDRESS = "ADDRESS";
	/**
	 * 手机品牌
	 */
	public static final String OPERATIONLOGS_DEVICEBRAND = "DEVICEBRAND";
	/**
	 * 手机型号
	 */
	public static final String OPERATIONLOGS_DEVICEMODEL = "DEVICEMODEL";
	/**
	 * 手机设备详细信息
	 */
	public static final String OPERATIONLOGS_DEVICEINFO = "DEVICEINFO";
	/**
	 * 备注
	 */
	public static final String OPERATIONLOGS_REMARK = "REMARK";
	/**
	 * 提交状态 Y/N
	 */
	public static final String OPERATIONLOGS_PDATASTATE = "PDATASTATE";
	/**
	 * 创建时间
	 */
	public static final String OPERATIONLOGS_CREATETIME = "CREATETIME";
	/**
	 * 功能名称
	 */
	public static final String OPERATIONLOGS_FUNCTION = "FUNCTION";
	/**
	 * APP版本号
	 */
	public static final String OPERATIONLOGS_APPVERSION = "APPVERSION";
	/**
	 * 模块
	 */
	public static final String OPERATIONLOGS_MODULE = "MODULE";
	/**
	 * 操作日志信息表的字段集合
	 */
	public static final String[] OPERATIONLOGS_COLUMNS = { OPERATIONLOGS_OID, OPERATIONLOGS_USERID,
			OPERATIONLOGS_STARTTIME, OPERATIONLOGS_ENDTIME, OPERATIONLOGS_SENDSYS, OPERATIONLOGS_RECEIVESYS,
			OPERATIONLOGS_APINAME, OPERATIONLOGS_PDATA, OPERATIONLOGS_RESULT, OPERATIONLOGS_ERRORINFO,
			OPERATIONLOGS_ADDRESS, OPERATIONLOGS_DEVICEBRAND, OPERATIONLOGS_DEVICEMODEL, OPERATIONLOGS_DEVICEINFO,
			OPERATIONLOGS_REMARK, OPERATIONLOGS_PDATASTATE, OPERATIONLOGS_CREATETIME, OPERATIONLOGS_FUNCTION,
			OPERATIONLOGS_APPVERSION, OPERATIONLOGS_MODULE };
	/**
	 * 操作日志信息表的字段集合(不含ID)
	 */
	public static final String[] OPERATIONLOGS_SHORT = { OPERATIONLOGS_USERID, OPERATIONLOGS_STARTTIME,
			OPERATIONLOGS_ENDTIME, OPERATIONLOGS_SENDSYS, OPERATIONLOGS_RECEIVESYS, OPERATIONLOGS_APINAME,
			OPERATIONLOGS_PDATA, OPERATIONLOGS_RESULT, OPERATIONLOGS_ERRORINFO, OPERATIONLOGS_ADDRESS,
			OPERATIONLOGS_DEVICEBRAND, OPERATIONLOGS_DEVICEMODEL, OPERATIONLOGS_DEVICEINFO, OPERATIONLOGS_REMARK,
			OPERATIONLOGS_PDATASTATE, OPERATIONLOGS_CREATETIME, OPERATIONLOGS_FUNCTION, OPERATIONLOGS_APPVERSION,
			OPERATIONLOGS_MODULE };
	/**
	 * 操作日志信息表建表语句
	 */
	public static final String OPERATIONLOGS_CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_OPERATIONLOGS + " ("
			+ OPERATIONLOGS_OID + "  INTEGER PRIMARY KEY AUTOINCREMENT," + OPERATIONLOGS_USERID + " TEXT,"
			+ OPERATIONLOGS_STARTTIME + " TEXT," + OPERATIONLOGS_ENDTIME + " TEXT," + OPERATIONLOGS_SENDSYS + " TEXT,"
			+ OPERATIONLOGS_RECEIVESYS + " TEXT," + OPERATIONLOGS_APINAME + " TEXT," + OPERATIONLOGS_PDATA + " TEXT,"
			+ OPERATIONLOGS_RESULT + " TEXT," + OPERATIONLOGS_ERRORINFO + " TEXT," + OPERATIONLOGS_ADDRESS + " TEXT,"
			+ OPERATIONLOGS_DEVICEBRAND + " TEXT," + OPERATIONLOGS_DEVICEMODEL + " TEXT," + OPERATIONLOGS_DEVICEINFO
			+ " TEXT," + OPERATIONLOGS_REMARK + " TEXT," + " TEXT," + OPERATIONLOGS_FUNCTION + " TEXT," + " TEXT,"
			+ OPERATIONLOGS_APPVERSION + " TEXT," + " TEXT," + OPERATIONLOGS_MODULE + " TEXT,"
			+ OPERATIONLOGS_PDATASTATE + " TEXT," + OPERATIONLOGS_CREATETIME + "  TEXT) ";
	/**
	 * *****************************错误日志信息表****************
	 */
	/**
	 * 24,错误日志表
	 */
	public static final String TABLE_ERRORLOGS = "ERRORLOGS";
	/**
	 * id
	 */
	public static final String ERRORLOGS_EID = "EID";
	/**
	 * 用户id
	 */
	public static final String ERRORLOGS_USERID = "USERID";
	/**
	 * 开始时间
	 */
	public static final String ERRORLOGS_STARTTIME = "STARTTIME";
	/**
	 * 结束时间
	 */
	public static final String ERRORLOGS_ENDTIME = "ENDTIME";
	/**
	 * 发送系统
	 */
	public static final String ERRORLOGS_SENDSYS = "SENDSYS";
	/**
	 * 接收系统
	 */
	public static final String ERRORLOGS_RECEIVESYS = "RECEIVESYS";
	/**
	 * 接口名称
	 */
	public static final String ERRORLOGS_APINAME = "APINAME";
	/**
	 * 传输数据
	 */
	public static final String ERRORLOGS_PDATA = "PDATA";
	/**
	 * 处理结果 成功S 出错E
	 */
	public static final String ERRORLOGS_RESULT = "RESULT";
	/**
	 * 异常信息
	 */
	public static final String ERRORLOGS_ERRORINFO = "ERRORINFO";
	/**
	 * 客户端ip
	 */
	public static final String ERRORLOGS_ADDRESS = "ADDRESS";
	/**
	 * 手机品牌
	 */
	public static final String ERRORLOGS_DEVICEBRAND = "DEVICEBRAND";
	/**
	 * 手机型号
	 */
	public static final String ERRORLOGS_DEVICEMODEL = "DEVICEMODEL";
	/**
	 * 手机设备详细信息
	 */
	public static final String ERRORLOGS_DEVICEINFO = "DEVICEINFO";
	/**
	 * 备注
	 */
	public static final String ERRORLOGS_REMARK = "REMARK";
	/**
	 * 提交状态 Y/N
	 */
	public static final String ERRORLOGS_PDATASTATE = "PDATASTATE";
	/**
	 * 创建时间
	 */
	public static final String ERRORLOGS_CREATETIME = "CREATETIME";
	/**
	 * 操作日志信息表的字段集合
	 */
	public static final String[] ERRORLOGS_COLUMNS = { ERRORLOGS_EID, ERRORLOGS_USERID, ERRORLOGS_STARTTIME,
			ERRORLOGS_ENDTIME, ERRORLOGS_SENDSYS, ERRORLOGS_RECEIVESYS, ERRORLOGS_APINAME, ERRORLOGS_PDATA,
			ERRORLOGS_RESULT, ERRORLOGS_ERRORINFO, ERRORLOGS_ADDRESS, ERRORLOGS_DEVICEBRAND, ERRORLOGS_DEVICEMODEL,
			ERRORLOGS_DEVICEINFO, ERRORLOGS_REMARK, ERRORLOGS_PDATASTATE, ERRORLOGS_CREATETIME };
	/**
	 * 操作日志信息表的字段集合(不含ID)
	 */
	public static final String[] ERRORLOGS_SHORT = { ERRORLOGS_USERID, ERRORLOGS_STARTTIME, ERRORLOGS_ENDTIME,
			ERRORLOGS_SENDSYS, ERRORLOGS_RECEIVESYS, ERRORLOGS_APINAME, ERRORLOGS_PDATA, ERRORLOGS_RESULT,
			ERRORLOGS_ERRORINFO, ERRORLOGS_ADDRESS, ERRORLOGS_DEVICEBRAND, ERRORLOGS_DEVICEMODEL, ERRORLOGS_DEVICEINFO,
			ERRORLOGS_REMARK, ERRORLOGS_PDATASTATE, ERRORLOGS_CREATETIME };
	/**
	 * 操作日志信息表建表语句
	 */
	public static final String ERRORLOGS_CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_OPERATIONLOGS + " ("
			+ ERRORLOGS_EID + "  INTEGER PRIMARY KEY AUTOINCREMENT," + ERRORLOGS_USERID + " TEXT," + ERRORLOGS_STARTTIME
			+ " TEXT," + ERRORLOGS_ENDTIME + " TEXT," + ERRORLOGS_SENDSYS + " TEXT," + ERRORLOGS_RECEIVESYS + " TEXT,"
			+ ERRORLOGS_APINAME + " TEXT," + ERRORLOGS_PDATA + " TEXT," + ERRORLOGS_RESULT + " TEXT,"
			+ ERRORLOGS_ERRORINFO + " TEXT," + ERRORLOGS_ADDRESS + " TEXT," + ERRORLOGS_DEVICEBRAND + " TEXT,"
			+ ERRORLOGS_DEVICEMODEL + " TEXT," + ERRORLOGS_DEVICEINFO + " TEXT," + ERRORLOGS_REMARK + " TEXT,"
			+ ERRORLOGS_PDATASTATE + " TEXT," + ERRORLOGS_CREATETIME + "  TEXT) ";
}
