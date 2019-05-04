package com.beijing.beixin.pojo;

public class CommentBean {

	private long share_current_request_time;

	private String ctx;

	private int totalCount;

	private CommentBeanItem[] result;

	public long getShare_current_request_time() {
		return share_current_request_time;
	}

	public void setShare_current_request_time(long share_current_request_time) {
		this.share_current_request_time = share_current_request_time;
	}

	public String getCtx() {
		return ctx;
	}

	public void setCtx(String ctx) {
		this.ctx = ctx;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public CommentBeanItem[] getResult() {
		return result;
	}

	public void setResult(CommentBeanItem[] result) {
		this.result = result;
	}

}
