/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package cn.lashou.widget.listview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.lashou.R;

public class XListViewFooter extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;
	public final static int STATE_HINT = 3;

	private Context mContext;

	private View mContentView;
	private ImageView mFooterIcon;
	private AnimationDrawable mAnim;
	private TextView mHintView;
	
	public XListViewFooter(Context context) {
		super(context);
		initView(context);
	}
	
	public XListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		mContext = context;
		LinearLayout moreView = (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.xlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mFooterIcon = (ImageView) moreView.findViewById(R.id.xlistview_footer_icon);
		mAnim = (AnimationDrawable) mFooterIcon.getDrawable();
		mHintView = (TextView)moreView.findViewById(R.id.xlistview_footer_hint_textview);
	}
	
	public void setState(int state) {
		if(state == STATE_NORMAL) {
			mAnim.stop();
			mHintView.setVisibility(View.GONE);
			mFooterIcon.setVisibility(View.GONE);
		} else if (state == STATE_READY) {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_ready);
			mFooterIcon.setVisibility(View.VISIBLE);
		} else if (state == STATE_LOADING) {
			mFooterIcon.setVisibility(View.VISIBLE);
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_loading);
		} else if(state == STATE_HINT){
			mFooterIcon.setVisibility(View.GONE);
			mHintView.setVisibility(View.VISIBLE);
		}
		startAnim();
	}
	
	private void startAnim() {
		if (mFooterIcon.isShown() && !mAnim.isRunning() ) {
			mAnim.start();
		} 
	}
	
	public void setHintText(String text) {
		mHintView.setText(text);
	}
	
	public void setHintText(int resId) {
		mHintView.setText(resId);
	}
	
	public void setBottomMargin(int height) {
		if (height < 0) return ;
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}
	
	public int getBottomMargin() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		return lp.bottomMargin;
	}
	
	/**
	 * normal status
	 */
	public void normal() {
		mAnim.stop();
		mHintView.setVisibility(View.GONE);
		mFooterIcon.setVisibility(View.GONE);
	}
	
	/**
	 * loading status 
	 */
	public void loading() {
		mHintView.setVisibility(View.VISIBLE);
		mFooterIcon.setVisibility(View.VISIBLE);
		startAnim();
	}
	
	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}
	
	/**
	 * show footer
	 */
	public void show() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}
	
	
}
