package com.beijing.beixin.pojo;

import java.util.HashMap;

/**
 * User: lxd Date: 11-12-23 Time: 下午5:34 公共图片SDK
 */
public class BaseImageProxy extends HashMap<String, String> {

	private static final long serialVersionUID = 1L;
	private String fileId;

	public BaseImageProxy(String fileId) {
		this.fileId = fileId;
	}

	public String toString() {
		return get("");
	}
}
