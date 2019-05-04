package com.beijing.beixin.pojo;

import java.io.Serializable;

/**
 * 收货地址列表
 * 
 * @author ouyanghao
 *
 */
@SuppressWarnings("serial")
public class AddressBean implements Serializable {
	/**
	 * 地名
	 */
	private String zoneNm;
	/**
	 * 具体地址，省市县街道
	 */
	private String addressPath;
	/**
	 * 收货人姓名
	 */
	private String name;
	/**
	 * 用户ID
	 */
	private Integer sysUserId;
	/**
	 * 收货地址ID
	 */
	private Integer receiveAddrId;
	/**
	 * 地名ID
	 */
	private Integer zoneId;
	/**
	 * 详细地点（用户填写），如某某公司
	 */
	private String addr;
	/**
	 * 邮政编码
	 */
	private String zipcode;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 固话
	 */
	private String tel;
	/**
	 * 是否默认地址，Y是，N不是
	 */
	private String isDefault;

	public String getZoneNm() {
		return zoneNm;
	}

	public void setZoneNm(String zoneNm) {
		this.zoneNm = zoneNm;
	}

	public String getAddressPath() {
		return addressPath;
	}

	public void setAddressPath(String addressPath) {
		this.addressPath = addressPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}

	public Integer getReceiveAddrId() {
		return receiveAddrId;
	}

	public void setReceiveAddrId(Integer receiveAddrId) {
		this.receiveAddrId = receiveAddrId;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
}
