package com.beijing.beixin.pojo;

import java.io.Serializable;
import java.util.List;

public class AllProvinceList implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 地名
	 */
	private String name;
	/**
	 * 地名ID
	 */
	private Integer id;
	private List<AllCityList> list;

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

	public List<AllCityList> getList() {
		return list;
	}

	public void setList(List<AllCityList> list) {
		this.list = list;
	}

}
