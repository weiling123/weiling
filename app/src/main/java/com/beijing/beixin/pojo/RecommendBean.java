package com.beijing.beixin.pojo;

import java.io.Serializable;

/**
 * 2.1.21.地址AJAX联动查询
 * 
 * @author ouyanghao
 *
 */
@SuppressWarnings("serial")
public class RecommendBean implements Serializable {
	/**
	 * 地名
	 */
	private String name;
	/**
	 * 图片路径
	 */
	private String image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
