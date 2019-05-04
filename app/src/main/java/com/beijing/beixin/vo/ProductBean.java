package com.beijing.beixin.vo;

import java.util.List;

/**
 * 测试实体
 * 
 * @author ouyanghao
 *
 */
public class ProductBean {
	private int pid;
	private String bookname;
	private String price;
	private String bookimage;
	private int state;
	private String shop;
	private List appProductBusinessRule;

	public List getAppProductBusinessRule() {
		return appProductBusinessRule;
	}

	public void setAppProductBusinessRule(List appProductBusinessRule) {
		this.appProductBusinessRule = appProductBusinessRule;
	}

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String string) {
		this.price = string;
	}

	public String getBookimage() {
		return bookimage;
	}

	public void setBookimage(String bookimage) {
		this.bookimage = bookimage;
	}
}
