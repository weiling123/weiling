package com.beijing.beixin.pojo;

/**
 * Created by IntelliJ IDEA. User: myj Date: 12-12-19 Time: 下午8:03
 */
public class ShopLevelProxy {

	private Integer levelId;
	private String levelNm;
	private BaseImageProxy levelIcon;
	private Integer levelGrade;

	public ShopLevelProxy(Integer levelId, String levelNm, BaseImageProxy levelIcon, Integer levelGrade) {
		this.levelId = levelId;
		this.levelNm = levelNm;
		this.levelIcon = levelIcon;
		this.levelGrade = levelGrade;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public String getLevelNm() {
		return levelNm;
	}

	public void setLevelNm(String levelNm) {
		this.levelNm = levelNm;
	}

	public BaseImageProxy getLevelIcon() {
		return levelIcon;
	}

	public void setLevelIcon(BaseImageProxy levelIcon) {
		this.levelIcon = levelIcon;
	}

	public Integer getLevelGrade() {
		return levelGrade;
	}

	public void setLevelGrade(Integer levelGrade) {
		this.levelGrade = levelGrade;
	}
}
