package com.beijing.beixin.utils.sqlite;

/**
 * 足迹浏览
 * 
 * @author ouyanghao
 *
 */
public class FootInfo {

	private String footinfoid;
	private String loginname;
	private String footinfoname;
	private String footinfoimage;
	private String footinfoprice;
	private String footinfoshopname;
	private String footinfooldprice;

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getFootinfoshopname() {
		return footinfoshopname;
	}

	public void setFootinfoshopname(String footinfoshopname) {
		this.footinfoshopname = footinfoshopname;
	}

	public String getFootinfooldprice() {
		return footinfooldprice;
	}

	public void setFootinfooldprice(String footinfooldprice) {
		this.footinfooldprice = footinfooldprice;
	}

	public String getFootinfoid() {
		return footinfoid;
	}

	public void setFootinfoid(String footinfoid) {
		this.footinfoid = footinfoid;
	}

	public String getFootinfoname() {
		return footinfoname;
	}

	public void setFootinfoname(String footinfoname) {
		this.footinfoname = footinfoname;
	}

	public String getFootinfoimage() {
		return footinfoimage;
	}

	public void setFootinfoimage(String footinfoimage) {
		this.footinfoimage = footinfoimage;
	}

	public String getFootinfoprice() {
		return footinfoprice;
	}

	public void setFootinfoprice(String footinfoprice) {
		this.footinfoprice = footinfoprice;
	}
}
