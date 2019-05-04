package com.beijing.beixin.pojo;

import java.io.Serializable;

/**
 * 2.1.21.地址AJAX联动查询
 * 
 * @author ouyanghao
 *
 */
@SuppressWarnings("serial")
public class ZoneBean implements Serializable {
	/**
	 * 地名
	 */
	private String name;
	/**
	 * 地名ID
	 */
	private Integer id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
