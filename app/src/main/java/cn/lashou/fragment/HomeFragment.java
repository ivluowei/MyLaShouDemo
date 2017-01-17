package cn.lashou.fragment;


import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lashou.R;
import cn.lashou.activity.GoodDetailsActivity;
import cn.lashou.activity.MovieCategoryActivity;
import cn.lashou.adapter.HomeAdapter;
import cn.lashou.adapter.HomeGridViewAdapter;
import cn.lashou.adapter.HomePagerAdapter;
import cn.lashou.adapter.HorizontalAdapter;
import cn.lashou.adapter.HorizontalAdapter.OnRecycleViewClickListener;
import cn.lashou.constants.Constants;
import cn.lashou.core.BaseFragment;
import cn.lashou.entity.BannerInfo;
import cn.lashou.entity.HomeBean;
import cn.lashou.entity.HomeImgBean;
import cn.lashou.entity.MovieBean;
import cn.lashou.http.CallServer;
import cn.lashou.http.HttpListner;
import cn.lashou.listener.PageListener;
import cn.lashou.ui.MainActivity;
import cn.lashou.util.ToastUtils;
import cn.lashou.widget.RollViewPager;
import cn.lashou.widget.SupportMultipleScreensUtil;
import cn.lashou.widget.ViewPagerIndicator;
import cn.lashou.widget.listview.XListView;
import cn.lashou.widget.listview.XListView.IXListViewListener;
import cn.lashou.widget.listview.XListView.OnXScrollListener;

import static butterknife.ButterKnife.findById;

;

/**
 * Created by luow on 2016/8/14.
 */
public class HomeFragment extends BaseFragment implements HttpListner, OnRecycleViewClickListener, OnItemClickListener, OnXScrollListener, IXListViewListener {
    private static final String TAG ="HomeFragment";
    @BindView(R.id.listView)
    XListView mXListView;
    @BindView(R.id.top_btn)
    Button mTopBtn;
    @BindView(R.id.city_name)
    TextView mCityName;
    @BindView(R.id.location_lay)
    LinearLayout mLocationLay;
    @BindView(R.id.scan_img)
    ImageView mScanImg;
    @BindView(R.id.msg_iv)
    ImageView mMsgIv;
    @BindView(R.id.news_num)
    TextView mNewsNum;
    @BindView(R.id.rl_msg_num)
    RelativeLayout mRlMsgNum;
    @BindView(R.id.ll_right_layout)
    LinearLayout mLlRightLayout;
    @BindView(R.id.inputLL)
    LinearLayout mInputLL;
    @BindView(R.id.title_bar)
    RelativeLayout mTitleBar;

    private HomeAdapter mHomeAdapter;
    private View mHeaderView;
    private RollViewPager mViewPager;
    private ViewPager mCateViewPager;
    private HomePagerAdapter mCateListAdapter;
    private HorizontalAdapter mHorizontalAdapter;

    private ArrayList<View> mViewCateList;
    private ArrayList<HomeImgBean> mHomeImgBeanOne;
    private ArrayList<HomeImgBean> mHomeImgBeanTwo;
    private List<HomeBean.ResultBean.GoodlistBean> mGoodlist;
    private List<MovieBean.ResultBean> mResultlist;
    private ViewPagerIndicator mIndicator;
    private ViewPagerIndicator mBannerIndicator;
    private BannerInfo mBannerInfo;
    private SimpleDraweeView simpleDraweeView1;
    private SimpleDraweeView simpleDraweeView2;
    private SimpleDraweeView simpleDraweeView3;
    private SimpleDraweeView simpleDraweeView4;
    private SimpleDraweeView simpleDraweeView5;
    private SimpleDraweeView simpleDraweeView6;
    private SimpleDraweeView simpleDraweeView7;
    private ArrayList<String> uriList;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv_rel_one;
    private TextView tv_rel_two;
    private TextView tv_rel_three;
    private TextView tv_rel_four;
    private TextView tv_lin_one;
    private TextView tv_lin_two;
    private TextView tv_lin_three;
    private TextView tv_lin_four;
    private TextView tv_lin_five;
    private TextView tv_lin_six;
    private RecyclerView mRecyclerView;
    private int pos;
    private int adViewTopSpace;
    private int adViewHeight;
    private View itemHeaderAdView;
    private View mFooterView;
    private boolean isVisible;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {

        uriList = new ArrayList<>();
        mViewCateList = new ArrayList<>();
        mHomeImgBeanOne = new ArrayList<>();
        mHomeImgBeanTwo = new ArrayList<>();
        mGoodlist = new ArrayList<>();
        mResultlist = new ArrayList<>();
        //获取资源文件
        String[] iconName = getResources().getStringArray(R.array.home_bar_lables);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.home_bar_icon);
        for (int i = 0; i < iconName.length; i++) {
            if (i < 8) {
                mHomeImgBeanOne.add(new HomeImgBean(iconName[i], typedArray.getResourceId(i, 0)));
            } else {
                mHomeImgBeanTwo.add(new HomeImgBean(iconName[i], typedArray.getResourceId(i, 0)));
            }
        }

        initHeaderView();

        initCateView();

        initHorizontalView();

        //添加(头布局)headerView
        mTitleBar.getBackground().setAlpha(0);
        SupportMultipleScreensUtil.scale(mHeaderView);
        SupportMultipleScreensUtil.scale(mFooterView);
        mXListView.setOnScrollListener(this);
        mXListView.setPullLoadEnable(false);
        mXListView.setPullRefreshEnable(true);
        mXListView.setXListViewListener(this);
        mXListView.setOnItemClickListener(this);
        mXListView.addHeaderView(mHeaderView);
        mXListView.addFooterView(mFooterView);
        mHomeAdapter = new HomeAdapter(getActivity(), mGoodlist);
        mXListView.setAdapter(mHomeAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position - 2;
        if (pos >= 0) {//判断pos>0，是禁用XListView的header监听
            if (pos == mGoodlist.size()) {
                MainActivity activity = (MainActivity) getActivity();
                activity.setTab(Constants.SURRONNDING_FRAGMENT_INDEX);
                ToastUtils.showShort(getActivity(), "查看全部");
            } else {
                Intent it = new Intent(getActivity(), GoodDetailsActivity.class);
                it.putExtra("title",mGoodlist.get(pos).getTitle());
                it.putExtra("imageUrl",mGoodlist.get(pos).getImages().get(0).getImage().toString());
                startActivity(it);
            }
        }
    }

    @Override
    protected void initData() {

        Request<String> recommendRequest = NoHttp.createStringRequest(Constants.URL_IMAGE, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(), 0, recommendRequest, this, true, true);

        Request<String> recommendRequest1 = NoHttp.createStringRequest(Constants.URL_MOVIEW_LIST, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(), 1, recommendRequest1, this, true, true);

        Request<String> recommendRequest2 = NoHttp.createStringRequest(Constants.URL_HOME_LIST, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(), 2, recommendRequest2, this, true, true);

    }

    //初始化头布局控件
    private void initHeaderView() {
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_header, null);
        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_footer, null);
        mViewPager = findById(mHeaderView, R.id.pager_banner);
        mBannerIndicator = findById(mHeaderView, R.id.banner_indicator);
        mCateViewPager = findById(mHeaderView, R.id.cateList_viewPager);
        mIndicator = findById(mHeaderView, R.id.indicator);
        simpleDraweeView1 = findById(mHeaderView, R.id.image_one);
        simpleDraweeView2 = findById(mHeaderView, R.id.image_two);
        simpleDraweeView3 = findById(mHeaderView, R.id.image_three);
        simpleDraweeView4 = findById(mHeaderView, R.id.image_four);
        simpleDraweeView5 = findById(mHeaderView, R.id.image_five);
        simpleDraweeView6 = findById(mHeaderView, R.id.image_six);
        simpleDraweeView7 = findById(mHeaderView, R.id.image_seven);
        tv1 = findById(mHeaderView, R.id.tv_one);
        tv2 = findById(mHeaderView, R.id.tv_two);
        tv3 = findById(mHeaderView, R.id.tv_three);
        tv4 = findById(mHeaderView, R.id.tv_four);
        tv_rel_one = findById(mHeaderView, R.id.tv_rel_one);
        tv_rel_two = findById(mHeaderView, R.id.tv_rel_two);
        tv_rel_three = findById(mHeaderView, R.id.tv_rel_three);
        tv_rel_four = findById(mHeaderView, R.id.tv_rel_four);
        tv_lin_one = findById(mHeaderView, R.id.tv_lin_one);
        tv_lin_two = findById(mHeaderView, R.id.tv_lin_two);
        tv_lin_three = findById(mHeaderView, R.id.tv_lin_three);
        tv_lin_four = findById(mHeaderView, R.id.tv_lin_four);
        tv_lin_five = findById(mHeaderView, R.id.tv_lin_five);
        tv_lin_six = findById(mHeaderView, R.id.tv_lin_six);
    }

    //初始化商品分类
    private void initCateView() {
        //商品分类的gridview
        View mPageOne = LayoutInflater.from(getActivity()).inflate(R.layout.home_gridview, null);
        GridView mGridView1 = findById(mPageOne, R.id.gridView);
        mGridView1.setAdapter(new HomeGridViewAdapter(getActivity(), mHomeImgBeanOne));
        mGridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), MovieCategoryActivity.class));
                ToastUtils.showShort(getActivity(), mHomeImgBeanOne.get(position).getIconName());
            }
        });

        View mPageTwo = LayoutInflater.from(getActivity()).inflate(R.layout.home_gridview, null);
        GridView mGridView2 = findById(mPageTwo, R.id.gridView);
        mGridView2.setAdapter(new HomeGridViewAdapter(getActivity(), mHomeImgBeanTwo));
        mGridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity activity = (MainActivity) getActivity();
                activity.setTab(1);
                ToastUtils.showShort(getActivity(), mHomeImgBeanTwo.get(position).getIconName());
            }
        });

        //商品分类的adapter
        mViewCateList.add(mPageOne);
        mViewCateList.add(mPageTwo);
        mCateListAdapter = new HomePagerAdapter(getActivity(), mViewCateList);
        mCateViewPager.setOnPageChangeListener(new PageListener(mIndicator));
        mCateViewPager.setAdapter(mCateListAdapter);
    }

    //初始化电影
    private void initHorizontalView() {
        mRecyclerView = ButterKnife.findById(mHeaderView, R.id.horizontal_RecycleView);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mHorizontalAdapter = new HorizontalAdapter(getActivity(), mResultlist);
        mHorizontalAdapter.setRecycleViewOnClickListener(this);
        mRecyclerView.setAdapter(mHorizontalAdapter);
    }

    //RecycleView监听回调
    @Override
    public void onClick(View view, int position) {
        ToastUtils.showShort(getActivity(), "点了" + position);
    }

    @Override
    public void onRefresh() {
        Request<String> recommendRequest = NoHttp.createStringRequest(Constants.URL_IMAGE, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(), 0, recommendRequest, this, true, true);

        Request<String> recommendRequest1 = NoHttp.createStringRequest(Constants.URL_MOVIEW_LIST, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(), 1, recommendRequest1, this, true, true);

        Request<String> recommendRequest2 = NoHttp.createStringRequest(Constants.URL_HOME_LIST, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(), 2, recommendRequest2, this, true, true);
    }

    @Override
    public void onLoadMore() {

    }

    private void setData() {
        simpleDraweeView1.setImageURI(Uri.parse(mBannerInfo.getOther().get(0).getImageUrl()));
        simpleDraweeView2.setImageURI(Uri.parse(mBannerInfo.getOther().get(1).getImageUrl()));
        simpleDraweeView3.setImageURI(Uri.parse(mBannerInfo.getOther().get(2).getImageUrl()));
        simpleDraweeView4.setImageURI(Uri.parse(mBannerInfo.getOther().get(3).getImageUrl()));
        simpleDraweeView5.setImageURI(Uri.parse(mBannerInfo.getOther().get(4).getImageUrl()));
        simpleDraweeView6.setImageURI(Uri.parse(mBannerInfo.getOther().get(5).getImageUrl()));
        simpleDraweeView7.setImageURI(Uri.parse(mBannerInfo.getOther().get(6).getImageUrl()));
        tv1.setText(mBannerInfo.getOther().get(0).getBigTitle());
        tv2.setText(mBannerInfo.getOther().get(0).getSmallTitle());
        tv3.setText(mBannerInfo.getOther().get(1).getBigTitle());
        tv4.setText(mBannerInfo.getOther().get(1).getSmallTitle());
        tv_rel_one.setText(mBannerInfo.getOther().get(2).getBigTitle());
        tv_rel_two.setText(mBannerInfo.getOther().get(2).getSmallTitle());
        tv_rel_three.setText(mBannerInfo.getOther().get(3).getBigTitle());
        tv_rel_four.setText(mBannerInfo.getOther().get(3).getSmallTitle());
        tv_lin_one.setText(mBannerInfo.getOther().get(4).getBigTitle());
        tv_lin_two.setText(mBannerInfo.getOther().get(4).getSmallTitle());
        tv_lin_three.setText(mBannerInfo.getOther().get(5).getBigTitle());
        tv_lin_four.setText(mBannerInfo.getOther().get(5).getSmallTitle());
        tv_lin_five.setText(mBannerInfo.getOther().get(6).getBigTitle());
        tv_lin_six.setText(mBannerInfo.getOther().get(6).getSmallTitle());
        mBannerIndicator.setNumbers(mBannerInfo.getBanner().size());//设置indictor数量
        mViewPager.setUriList(uriList);
        mViewPager.setDot(mBannerIndicator);
        mViewPager.stopRollTask();
        mViewPager.startRoll();
        mViewPager.setPagerCallback(new RollViewPager.OnPagerClickCallback() {
            @Override
            public void onPagerClick(int position) {
                ToastUtils.showShort(getActivity(), "" + position);
            }
        });
    }

    @Override
    @OnClick({R.id.top_btn, R.id.location_lay, R.id.rl_msg_num, R.id.inputLL, R.id.scan_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_btn:
                mXListView.setSelection(Constants.SURRONNDING_FRAGMENT_INDEX);
                break;
            case R.id.location_lay:
                ToastUtils.showShort(getActivity(), "北京");
                break;
            case R.id.rl_msg_num:
                ToastUtils.showShort(getActivity(), "邮件");
                break;
            case R.id.inputLL:
                ToastUtils.showShort(getActivity(), "搜索");
                break;
            case R.id.scan_img:
                ToastUtils.showShort(getActivity(), "二维码");
                break;
        }
    }

    @Override
    public void onSucceed(int what, Response response, boolean isNet) {
        Gson gson = new Gson();
        switch (what) {
            case 0:
                uriList.clear();
                Logger.e(response.get());
                mBannerInfo = gson.fromJson(response.get().toString(), BannerInfo.class);
                //初始化广告条的数据
                for (int i = 0; i < mBannerInfo.getBanner().size(); i++) {
                    uriList.add(mBannerInfo.getBanner().get(i).getImageUrl());
                }
                setData();
                break;

            case 1:
                mResultlist.clear();
                MovieBean mMovieBean = gson.fromJson(response.get().toString(), MovieBean.class);
                List<MovieBean.ResultBean> resultlist = mMovieBean.getResult();
                mResultlist.addAll(resultlist);
                mHorizontalAdapter.notifyDataSetChanged();
                break;

            case 2:
                mXListView.stopRefresh();
                mGoodlist.clear();
                HomeBean mHomeBean = gson.fromJson(response.get().toString(), HomeBean.class);
                List<HomeBean.ResultBean.GoodlistBean> goodlist = mHomeBean.getResult().getGoodlist();
                mGoodlist.addAll(goodlist);
                mHomeAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailed(int what, Response response, boolean isNet) {

    }

    @Override
    public void onXScrolling(View view) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        float fraction;

        if (itemHeaderAdView == null) {
            itemHeaderAdView = mXListView.getChildAt(1 - firstVisibleItem);
        }
        if (itemHeaderAdView != null) {
            adViewTopSpace = itemHeaderAdView.getTop() + mTitleBar.getHeight();
            adViewHeight = mViewPager.getHeight();
        }
        float space = Math.abs(adViewTopSpace) * 1f;
        fraction = space / (adViewHeight - mTitleBar.getHeight());
        if (adViewTopSpace > mTitleBar.getHeight()) {
            mTitleBar.getBackground().setAlpha(0);
            return;
        }

        if (fraction >= 1f) {
            mTitleBar.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
        } else {
            mTitleBar.getBackground().setAlpha(0);
        }

        if (mXListView.getLastVisiblePosition() >= 25 && isVisible) {
            isVisible = false;
            mTopBtn.setVisibility(View.VISIBLE);
            animate(mTopBtn, R.anim.floating_action_button_show);
        }else if (mXListView.getLastVisiblePosition() < 25 && !isVisible){
            isVisible = true;
            mTopBtn.setVisibility(View.GONE);
            animate(mTopBtn, R.anim.floating_action_button_hide);
        }

    }

    private void animate(View view, int anim) {
        if (anim != 0) {
            Animation a = AnimationUtils.loadAnimation(view.getContext(), anim);
            view.startAnimation(a);
        }
    }

//    private class MyAsyn extends AsyncTask {
//        @Override
//        protected Object doInBackground(Object[] params) {
//            try {
//                //这里可以进行网络数据的操作
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//            mXListView.stopRefresh();
//        }
//    }
}