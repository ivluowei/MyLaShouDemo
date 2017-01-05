package cn.lashou.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

import cn.lashou.R;

/**
 * Created by luow on 2016/8/14.
 */
public class CustTabWidget extends LinearLayout {


	private int[] mDrawableIds = new int[] { R.drawable.bg_home,
			R.drawable.bg_surrounding, R.drawable.bg_myself, R.drawable.bg_more };
	//CheckedTextView
	private List<CheckedTextView> mCheckedList = new ArrayList<CheckedTextView>();

	private List<View> mViewList = new ArrayList<View>();

	private List<ImageView> mIndicateImgs = new ArrayList<ImageView>();


	private CharSequence[] mLabels;

	public CustTabWidget(Context context) {
		super(context);
		initView(context);
	}

	public CustTabWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		mLabels = context.getResources().getTextArray(R.array.bottom_bar_labels);
		initView(context);
	}

	private void initView(final Context context) {
		this.setOrientation(LinearLayout.HORIZONTAL);
		//底部导航栏的高度
		this.setBackgroundResource(R.drawable.index_bottom_bar);

		LayoutInflater inflater = LayoutInflater.from(context);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		params.weight = 1.0f;
		params.gravity = Gravity.CENTER;

		for (int i = 0; i < mLabels.length; i++) {
			final int index = i;
			View tabView = inflater.inflate(R.layout.tab_item, null);
			CheckedTextView tabText = (CheckedTextView) tabView.findViewById(R.id.item_name);
			tabText.setText(mLabels[i]);
			//tabText.setPadding(0, 10, 25, 0);
			tabText.setCompoundDrawablesWithIntrinsicBounds(null, context
					.getResources().getDrawable(mDrawableIds[i]), null, null);
			//ImageView indicateImage = (ImageView) tabView.findViewById(R.id.indicate_img);
			addView(tabView, params);
			if (i == 0) {
				LinearLayout dividerView = new LinearLayout(context);
				LayoutParams dividerParams = new LayoutParams(2, 0);
				addView(dividerView, dividerParams);
			}


			tabText.setTag(i);

			mCheckedList.add(tabText);
			//mIndicateImgs.add(indicateImage);
			mViewList.add(tabView);


			if (i == 0) {
				tabText.setChecked(true);
				tabText.setTextColor(Color.rgb(255, 111, 54));
			} else {
				tabText.setChecked(false);
				tabText.setTextColor(Color.rgb(161, 161, 161));
			}

			tabView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setTabDisplay(context, index);
					if (null != mTabSelectedListener) {
						mTabSelectedListener.onTabSelecete(index);
					}
				}
			});
		}
	}

	public void setTabDisplay(Context context, int index){
		for (int i = 0; i < mCheckedList.size(); i++) {
			CheckedTextView tabText = mCheckedList.get(i);
			if ((Integer) (tabText.getTag()) == index) {
				tabText.setChecked(true);
				tabText.setTextColor(Color.rgb(255, 111, 54));
			} else {
				tabText.setChecked(false);
				tabText.setTextColor(Color.rgb(161, 161, 161));
			}
		}
	}

	/**
	 * 消息提示
	 *
	 * @param context
	 * @param position
	 * @param visible
	 */
	public void setIndicateDisplay(Context context, int position,
			boolean visible) {
		int size = mIndicateImgs.size();
		if (size <= position) {
			return;
		}
		ImageView indicateImg = mIndicateImgs.get(position);
		indicateImg.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
	}


	public interface onTabSelectedListener{
		void onTabSelecete(int index);
	}

	private onTabSelectedListener mTabSelectedListener;
	public void setOnTabSelectedListener(onTabSelectedListener listener){
		this.mTabSelectedListener = listener;
	}
}
