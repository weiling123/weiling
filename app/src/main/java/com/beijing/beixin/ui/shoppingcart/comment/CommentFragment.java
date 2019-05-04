package com.beijing.beixin.ui.shoppingcart.comment;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.CommentBean;
import com.beijing.beixin.pojo.CommentBeanItem;
import com.beijing.beixin.pojo.ShopCommentReply;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.shoppingcart.BookDetailActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.PullListTask;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase;
import com.beijing.beixin.widget.CircleImageView;
import com.lidroid.xutils.http.RequestParams;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beijing.beixin.R;
import com.beijing.beixin.constants.SystemConfig;
import com.beijing.beixin.pojo.CommentBean;
import com.beijing.beixin.pojo.CommentBeanItem;
import com.beijing.beixin.pojo.ShopCommentReply;
import com.beijing.beixin.ui.base.BaseFragment;
import com.beijing.beixin.ui.shoppingcart.GoodCommentsActivity;
import com.beijing.beixin.utils.BitmapUtil;
import com.beijing.beixin.utils.PullListTask;
import com.beijing.beixin.utils.pulltorefresh.PullToRefreshBase;
import com.beijing.beixin.widget.CircleImageView;
import com.lidroid.xutils.http.RequestParams;

public class CommentFragment extends BaseFragment {

	private String mType;
	private MyPullListTask mMyPullListTask;
	private BitmapUtil mBitmapUtil = new BitmapUtil();

	public static CommentFragment instance(String type) {
		CommentFragment commentFragment = new CommentFragment();
		Bundle bundle = new Bundle();
		bundle.putString("type", type);
		commentFragment.setArguments(bundle);
		return commentFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mType = getArguments().getString("type");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.pulllist_fragment, container, false);
		CommentBeanItem[] arraList = new CommentBeanItem[0];
		View loadingView = rootView.findViewById(R.id.progressBar);
		View loadFaileView = rootView.findViewById(R.id.load_faile);
		mMyPullListTask = new MyPullListTask(rootView);
		rootView.findViewById(R.id.load_faile_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mMyPullListTask.startNet();
			}
		});
		mMyPullListTask.setArraList(arraList, R.layout.item_frag_comment_list, loadingView, loadFaileView);
		return rootView;
	}

	class MyPullListTask extends PullListTask<CommentBeanItem> implements OnItemClickListener {

		public MyPullListTask(View view) {
			super(getActivity(), view, PullToRefreshBase.Mode.BOTH, true, false);
		}

		@Override
		public View getPullListView(CommentBeanItem t, int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null || convertView.getTag() == null) {
				viewHolder = new ViewHolder();
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_frag_comment_list, null, false);
				viewHolder.mUserIconIView = (CircleImageView) convertView.findViewById(R.id.user_icon);
				viewHolder.mUserAccountTView = (TextView) convertView.findViewById(R.id.user_account);
				viewHolder.mUserLevel = (TextView) convertView.findViewById(R.id.comment_level);
				viewHolder.mUserTimeTView = (TextView) convertView.findViewById(R.id.user_time);
				viewHolder.mRatingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
				viewHolder.mUserCommentTView = (TextView) convertView.findViewById(R.id.user_comment);
				viewHolder.mUserBuyTimeTView = (TextView) convertView.findViewById(R.id.user_buy_time);
				viewHolder.mBottom = convertView.findViewById(R.id.bottom);
				viewHolder.mlistview = (ListView) convertView.findViewById(R.id.listview);
				viewHolder.mListItemAdapte = new ListItemAdapte();
				viewHolder.mlistview.setAdapter(viewHolder.mListItemAdapte);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			CommentBeanItem item = mArrayList.get(position);
			String url = item.getUserIcon();
			if (!TextUtils.isEmpty(url)) {
				mBitmapUtil.displayImage(getActivity(), viewHolder.mUserIconIView, url);
			}
			if (item.getLoginId() != null) {
				if (item.getLoginId().length() >= 11) {
					viewHolder.mUserAccountTView.setText(item.getLoginId().substring(0, 3) + "******"
							+ item.getLoginId().substring(9, item.getLoginId().length()));
				} else {
					viewHolder.mUserAccountTView.setText(item.getLoginId());
				}
			}
			viewHolder.mUserLevel.setText(item.getUserLevelNm());
			viewHolder.mUserTimeTView.setText(item.getShowCommentTime());
			viewHolder.mRatingBar.setRating(item.getGradeLevel());
			viewHolder.mUserCommentTView.setText(item.getContent());
			viewHolder.mUserBuyTimeTView.setText("购买日期：" + item.getShowLastBuyTime());
			ShopCommentReply[] shopCommentReplyList = item.getShopCommentReplyList();
			if (shopCommentReplyList == null || shopCommentReplyList.length == 0) {
				viewHolder.mlistview.setVisibility(View.GONE);
				viewHolder.mBottom.setVisibility(View.GONE);
			} else {
				viewHolder.mlistview.setVisibility(View.VISIBLE);
				viewHolder.mBottom.setVisibility(View.VISIBLE);
				viewHolder.mListItemAdapte.setPosition(shopCommentReplyList);
			}
			return convertView;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		}

		public String getPageUrl() {
			return SystemConfig.PRODUCT_COMMENT;
		}

		public RequestParams getRequestParams() {
			RequestParams requestParams = super.getRequestParams();
			requestParams.addBodyParameter("productId", ((BookDetailActivity) getActivity()).mProductId);
			if (!TextUtils.isEmpty(mType)) {
				requestParams.addBodyParameter("commentStatistics", mType);
			}
			return requestParams;
		}

		public CommentBeanItem[] getList(String response) {
			if (TextUtils.isEmpty(response)) {
				return null;
			}
			CommentBean commentBean = JSON.parseObject(response, CommentBean.class);
			if (commentBean == null) {
				return null;
			}
			return commentBean.getResult();
		}
	}

	private static class ViewHolder {

		CircleImageView mUserIconIView;
		TextView mUserAccountTView;
		TextView mUserLevel;
		TextView mUserTimeTView;
		RatingBar mRatingBar;
		TextView mUserCommentTView;
		TextView mUserBuyTimeTView;
		ListView mlistview;
		View mBottom;
		ListItemAdapte mListItemAdapte;
	}

	private class ListItemAdapte extends ArrayAdapter<ShopCommentReply> {

		private ShopCommentReply[] mShopCommentReplys;

		public ListItemAdapte() {
			super(getActivity(), R.layout.item_comment_list_item);
		}

		public void setPosition(ShopCommentReply[] shopCommentReplys) {
			mShopCommentReplys = shopCommentReplys;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			if (mShopCommentReplys == null || mShopCommentReplys.length == 0) {
				return 0;
			}
			return mShopCommentReplys.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolderItem viewHolderItem;
			if (convertView == null || convertView.getTag() == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_comment_list_item, null);
				viewHolderItem = new ViewHolderItem();
				viewHolderItem.shop_comment = (TextView) convertView.findViewById(R.id.shop_comment);
				viewHolderItem.shop_comment_time = (TextView) convertView.findViewById(R.id.shop_comment_time);
				convertView.setTag(viewHolderItem);
			} else {
				viewHolderItem = (ViewHolderItem) convertView.getTag();
			}
			String comment = mShopCommentReplys[position].getReplyCont();
			viewHolderItem.shop_comment.setText(getSpannableStringBuilder(comment));
			viewHolderItem.shop_comment_time.setText(mShopCommentReplys[position].getShowReplyTime());
			return convertView;
		}
	}

	private SpannableStringBuilder getSpannableStringBuilder(String content) {
		if (TextUtils.isEmpty(content)) {
			content = "商家回复：";
		} else {
			content = "商家回复：" + content;
		}
		SpannableStringBuilder builder = new SpannableStringBuilder(content);
		builder.setSpan(NAME_SPAN, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		if (TextUtils.isEmpty(content)) {
			builder.setSpan(CONTENT_SPAN, 5, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return builder;
	}

	public static final ForegroundColorSpan NAME_SPAN = new ForegroundColorSpan(Color.parseColor("#F05253"));
	public static final ForegroundColorSpan CONTENT_SPAN = new ForegroundColorSpan(Color.parseColor("#312C12"));

	private static class ViewHolderItem {

		TextView shop_comment;
		TextView shop_comment_time;
	}
}
