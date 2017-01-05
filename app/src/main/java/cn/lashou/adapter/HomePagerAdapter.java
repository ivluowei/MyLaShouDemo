package cn.lashou.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by luow on 2016/8/18.
 */

public class HomePagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<View> mViewBanner;

    public HomePagerAdapter(Context mContext, ArrayList<View> mViewBanner) {
        this.mContext = mContext;
        this.mViewBanner = mViewBanner;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewBanner.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewBanner.get(position));
    }

    @Override
    public int getCount() {
        return mViewBanner.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
