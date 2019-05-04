package com.beijing.beixin.pojo;

import com.beijing.beixin.utils.TimeUtil;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class CommentBeanItem implements Parcelable {
	private String id;

	private String content;

	private String userName;

	private String commentTime;

	private int gradeLevel;

	private String userLevelNm;

	private String replyTime;

	private String commentReply;

	private String showCommentTime;

	private ShopCommentReply[] shopCommentReplyList;

	private long lastBuyTime;

	private String userIcon;

	private String loginId;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public CommentBeanItem(Parcel in) {

	}

	public CommentBeanItem() {

	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getShowLastBuyTime() {
		return TimeUtil.getDateFormatDate(lastBuyTime);
	}

	public long getLastBuyTime() {
		return lastBuyTime;
	}

	public void setLastBuyTime(long lastBuyTime) {
		this.lastBuyTime = lastBuyTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getShowCommentTime() {
		if (!TextUtils.isEmpty(commentTime)) {
			showCommentTime = commentTime.trim();
			int index = showCommentTime.indexOf(" ");
			if (index != -1) {
				showCommentTime = showCommentTime.substring(0, index);
			}

		}
		return showCommentTime;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public int getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(int gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public String getCommentReply() {
		return commentReply;
	}

	public void setCommentReply(String commentReply) {
		this.commentReply = commentReply;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	// 用来创建自定义的Parcelable的对象
	public static final Parcelable.Creator<CommentBeanItem> CREATOR = new Parcelable.Creator<CommentBeanItem>() {
		public CommentBeanItem createFromParcel(Parcel in) {
			return new CommentBeanItem(in);
		}

		public CommentBeanItem[] newArray(int size) {
			return new CommentBeanItem[size];
		}
	};

	public ShopCommentReply[] getShopCommentReplyList() {
		return shopCommentReplyList;
	}

	public void setShopCommentReplyList(ShopCommentReply[] shopCommentReplyList) {
		this.shopCommentReplyList = shopCommentReplyList;
	}

	public void setShowCommentTime(String showCommentTime) {
		this.showCommentTime = showCommentTime;
	}

	public String getUserLevelNm() {
		return userLevelNm;
	}

	public void setUserLevelNm(String userLevelNm) {
		this.userLevelNm = userLevelNm;
	}

}
