package com.beijing.beixin.vo;

/**
 * 图片资源实体类
 * 
 * @author ouyanghao
 *
 */
public class CarouselData {
	/** ID */
	private int Id;
	/** 图片标题 */
	private String Title;
	/** 图片地址 */
	private String Image;
	/** 图片位置 */
	private int PositionId;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public int getPositionId() {
		return PositionId;
	}

	public void setPositionId(int positionId) {
		PositionId = positionId;
	}
}
