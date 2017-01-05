package cn.lashou.listener;

import android.support.v4.view.ViewPager;

import cn.lashou.widget.ViewPagerIndicator;

public class PageListener implements ViewPager.OnPageChangeListener{
        private ViewPagerIndicator mIndicator;

        public PageListener(ViewPagerIndicator indicator) {
                this.mIndicator = indicator;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mIndicator.setOffX(position, positionOffset);
        }
        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }