package cn.lashou.widget;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import cn.lashou.R;

/**
 * 循环滚动切换图片(支持带标题,不带标题传null即可),自带适配器 指定uri的图片 手动滑动时，暂停滚动，增强用户友好性
 */
public class RollViewPager extends ViewPager {

    private Context context;
    private int currentItem = 0;
    private ArrayList<String> uriList;// 图片地址
    private ArrayList<View> dots;// 点的位置不固定，所以需要让调用者传入
    private TextView title;// 用于显示每个图片的标题
    private String[] titles;
    private OnPagerClickCallback onPagerClickCallback;
    private ViewPagerTask viewPagerTask;
    private boolean hasSetAdapter = false;
    private static final int SWITCH_DURATION = 4000;
    private long start = 0;
    private float lastX;
    private float currentX;
    private boolean isAutoLooper = true;
    private ViewPagerIndicator mBannerIndicator;

    public RollViewPager(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isAutoLooper = false;
                getParent().requestDisallowInterceptTouchEvent(true);
                start = System.currentTimeMillis();
                mHandler.removeCallbacksAndMessages(null);
                currentX = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                lastX = event.getX();
                mHandler.removeCallbacks(viewPagerTask);
                break;

            case MotionEvent.ACTION_CANCEL:
                startRoll();
                break;

            case MotionEvent.ACTION_UP:
                lastX = event.getX();
                long duration = System.currentTimeMillis() - start;
                if (Math.abs(lastX - currentX) <= 8f && duration <= 400) {
                    if (onPagerClickCallback != null)
                        onPagerClickCallback.onPagerClick(currentItem);
                    startRoll();
                    return true;
                }
                startRoll();
                break;
        }
        return super.onTouchEvent(event);
    }

    public class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            currentItem = (currentItem + 1) % uriList.size();
            mHandler.sendEmptyMessage(0);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    setCurrentItem(currentItem);
                    startRoll();
                    break;

                case 1:
                    setCurrentItem(uriList.size() - 1);
                    break;

                case 2:
                    setCurrentItem(0);
                    break;

                default:
                    break;
            }
        }
    };

    public void setDot(ViewPagerIndicator mBannerIndicator) {
        this.mBannerIndicator = mBannerIndicator;
    }

    public void setPagerCallback(OnPagerClickCallback onPagerClickCallback) {
        this.onPagerClickCallback = onPagerClickCallback;
    }

    /**
     * 设置网络图片的url集合，也可以是本地图片的uri
     *
     * @param uriList
     */
    public void setUriList(ArrayList<String> uriList) {
        this.uriList = uriList;
    }

    /**
     * 标题相关
     *
     * @param title  用于显示标题的TextView
     * @param titles 标题数组
     */
    public void setTitle(TextView title, String[] titles) {
        this.title = title;
        this.titles = titles;
        if (title != null && titles != null && titles.length > 0)
            title.setText(titles[0]);// 默认显示第一张的标题
    }

    /**
     * 开始滚动
     */
    public void startRoll() {
        isAutoLooper = true;
        if (!hasSetAdapter) {
            hasSetAdapter = true;
            this.setOnPageChangeListener(new MyOnPageChangeListener());
            this.setAdapter(new ViewPagerAdapter());
        }
        if (viewPagerTask == null) {
            viewPagerTask = new ViewPagerTask();
        }
        mHandler.postDelayed(viewPagerTask, SWITCH_DURATION);
    }

    class MyOnPageChangeListener implements OnPageChangeListener {


        @Override
        public void onPageSelected(int position) {
            currentItem = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float offset, int offsetPixels) {
            mBannerIndicator.setOffX(position, offset);
            if (!isAutoLooper) {
                // 写以下代码作用是 单图片滑到最后时，在滑就又从第一个开始。 bug：不是最后一个也会触发
                // if (position == 0) {
                // if (offset == 0 && (lastX - currentX > 8f)) {
                // mHandler.sendEmptyMessage(1);
                // }
                // } else if (position == uriList.size() - 1) {
                // if (offset == 0 && (lastX - currentX < -8f)) {
                // mHandler.sendEmptyMessage(2);
                // }
                // }
            }
        }
    }

    /**
     * 自带设配器,需要回调类来处理page的点击事件
     */
    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return uriList.size();
        }

        @Override
        public Object instantiateItem(View container, final int position) {
            View view = View.inflate(context, R.layout.pager_image_item, null);
            ((ViewPager) container).addView(view);
            SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.iv_item);
            if (uriList.get(position).equals("") || uriList.get(position) == "") {
                //	imageView.setBackgroundResource(R.drawable.home_beanmorenoff);
            } else {
                imageView.setImageURI(Uri.parse(uriList.get(position)));
            }
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(View view, int position, Object object) {
            // 将ImageView从ViewPager移除
            ((ViewPager) view).removeView((View) object);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }

    /**
     * 停止
     */
    public void stopRollTask() {
        if (viewPagerTask != null) {
            viewPagerTask = null;
            mHandler.removeCallbacksAndMessages(null);
            currentItem = 0;
        }
    }

    /**
     * 处理page点击的回调接口
     */
    public interface OnPagerClickCallback {
        void onPagerClick(int position);
    }
}
