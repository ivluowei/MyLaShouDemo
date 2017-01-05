/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package cn.lashou.widget.listview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.lashou.R;

public class XListViewHeader extends LinearLayout {
	private LinearLayout mContainer;
	private ImageView mArrowImageView;
	private ImageView mProgressBar;
	private TextView mHintTextView;
	private int mState = STATE_NORMAL;

	private AnimationDrawable mAnim;

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;

	public XListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.xlistview_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);

		mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
		mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
		mProgressBar = (ImageView) findViewById(R.id.xlistview_header_progressbar);
		mAnim = (AnimationDrawable) mProgressBar.getDrawable();
	}

	public void setState(int state) {
		if (state !=STATE_REFRESHING && state == mState) {
			mAnim.stop();
			return;
		}
		if (state == STATE_REFRESHING) { // 显示进度
			mArrowImageView.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.VISIBLE);
		} else { // 显示箭头图片
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
		}
		switch (state) {
		case STATE_NORMAL:
			mAnim.stop();
			mHintTextView.setText(R.string.xlistview_header_hint_normal);
			break;
		case STATE_READY:
			if (mState != STATE_READY) {
				mAnim.stop();
				mHintTextView.setText(R.string.xlistview_header_hint_ready);
			}
			break;
		case STATE_REFRESHING:
			mHintTextView.setText(R.string.xlistview_header_hint_loading);
			break;
		default:
			break;
		}
		startAnim();
		mState = state;
	}
	
	private void startAnim() {
		if (mProgressBar.isShown() && !mAnim.isRunning() ) {
			mAnim.start();
		}
	}

	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return mContainer.getHeight();
	}

}
