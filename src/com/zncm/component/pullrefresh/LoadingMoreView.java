package com.zncm.component.pullrefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zncm.easysc.R;

public class LoadingMoreView {

	private ProgressBar progressBar; // 进度条
	private TextView tvWait;// 底部等待
	private RelativeLayout moreView; // 加载中

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public TextView getTvWait() {
		return tvWait;
	}

	public void setTvWait(TextView tvWait) {
		this.tvWait = tvWait;
	}
	
	public RelativeLayout getMoreView() {
		return moreView;
	}
	
	public void setMoreView(RelativeLayout moreView) {
		this.moreView = moreView;
	}

	public LoadingMoreView(Context ctx) {

		moreView = (RelativeLayout) LayoutInflater.from(ctx).inflate(R.layout.view_list_footview, null);

		if (moreView != null) {
			progressBar = (ProgressBar) moreView.findViewById(R.id.pg_wait);
			tvWait = (TextView) moreView.findViewById(R.id.tv_wait);
		}
	}
}
