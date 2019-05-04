package com.beijing.beixin.ui.activity.yindaoye;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.beijing.beixin.R;
import com.beijing.beixin.application.MyApplication;
import com.beijing.beixin.ui.MainActivity;

public class GuideFragment extends Fragment {

	public int Flag;
	RelativeLayout mRelativeLayout;
	Button mButton;

	public static GuideFragment getGuideFragment(int flag) {
		GuideFragment mFragment = new GuideFragment();
		mFragment.Flag = flag;
		return mFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.guidefragment, null);
		mRelativeLayout = (RelativeLayout) view.findViewById(R.id.image);
		mButton = (Button) view.findViewById(R.id.next_button);
		if (Flag == 1) {
			mRelativeLayout.setBackgroundResource(R.drawable.index_00);
			mButton.setVisibility(View.GONE);
		}
		if (Flag == 2) {
			mRelativeLayout.setBackgroundResource(R.drawable.index_01);
			mButton.setVisibility(View.GONE);
		}
		if (Flag == 3) {
			mRelativeLayout.setBackgroundResource(R.drawable.index_02);
			mButton.setVisibility(View.VISIBLE);
		}
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				((MyApplication) getActivity().getApplication()).setIsopen("true");
				startActivity(new Intent(getActivity(), MainActivity.class));
				getActivity().finish();
			}
		});
		return view;
	}

	@Override
	public void onStop() {
		super.onStop();
		// mRelativeLayout.setBackgroundResource(null);
		mRelativeLayout = null;
		mButton = null;
	}
}
