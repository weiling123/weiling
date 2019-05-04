package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.Date;

public class ErrorLogs implements Serializable {

	private static final long serialVersionUID = 1L;
	private int eId;// 主键
	private String userId;// 用户id
	private String startTime;// 开始时间 yyyy-MM-dd HH:mm:ss
	private String endTime;// 结束时间 yyyy-MM-dd HH:mm:ss
	private String sendSys;// 发送系统
	private String receiveSys;// 接收系统
	private String apiName;// 接口名称
	private String pData;// 传输数据
	private String result;// 处理结果"成功-S 出错-E"
	private String errorInfo;// 异常信息
	private String address;// 客户端ip
	private String deviceBrand;// 手机品牌
	private String deviceModel;// 手机型号
	private String deviceInfo;// 手机设备详细信息
	private String appVersion;// APP版本号
	private String remark;// 备注
	private String createTime;// 创建时间
	private String status;// 上传状态,提交状态 Y/N

	public int geteId() {
		return eId;
	}

	public void seteId(int eId) {
		this.eId = eId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSendSys() {
		return sendSys;
	}

	public void setSendSys(String sendSys) {
		this.sendSys = sendSys;
	}

	public String getReceiveSys() {
		return receiveSys;
	}

	public void setReceiveSys(String receiveSys) {
		this.receiveSys = receiveSys;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getpData() {
		return pData;
	}

	public void setpData(String pData) {
		this.pData = pData;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDeviceBrand() {
		return deviceBrand;
	}

	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
}
