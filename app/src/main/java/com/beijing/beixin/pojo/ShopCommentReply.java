package com.beijing.beixin.pojo;

import com.beijing.beixin.utils.TimeUtil;

public class ShopCommentReply {

	private long replyTime;

	private String replyCont;

	public String getShowReplyTime() {
		return TimeUtil.getTxtTime(replyTime);
	}

	public long getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(long replyTime) {
		this.replyTime = replyTime;
	}

	public String getReplyCont() {
		return replyCont;
	}

	public void setReplyCont(String replyCont) {
		this.replyCont = replyCont;
	}

}
