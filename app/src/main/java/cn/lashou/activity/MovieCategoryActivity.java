package cn.lashou.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
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
import cn.lashou.adapter.HomeAdapter;
import cn.lashou.adapter.HorizontalAdapter;
import cn.lashou.constants.Constants;
import cn.lashou.core.BaseActivity;
import cn.lashou.entity.CategoryBean;
import cn.lashou.entity.HomeBean;
import cn.lashou.entity.MovieBean;
import cn.lashou.http.CallServer;
import cn.lashou.http.HttpListner;
import cn.lashou.util.ToastUtils;
import cn.lashou.widget.CheckListPopwindows;
import cn.lashou.widget.DoubleListPopWindows;
import cn.lashou.widget.SingleListPopwindows;
import cn.lashou.widget.SupportMultipleScreensUtil;
import cn.lashou.widget.listview.XListView;
import cn.lashou.widget.listview.XListView.IXListViewListener;
import cn.lashou.widget.listview.XListView.OnXScrollListener;

/**
 * Created by luow on 2016/12/26.
 */

public class MovieCategoryActivity extends BaseActivity implements HttpListner<String>, OnItemClickListener, IXListViewListener, OnXScrollListener {

    @BindView(R.id.movie_title)
    TextView mMovieTitle;
    @BindView(R.id.map)
    ImageView mMap;
    @BindView(R.id.ic_back)
    ImageView mIcBack;
    @BindView(R.id.moview_list)
    XListView mMoviewList;
    @BindView(R.id.tv_all_fenlei)
    TextView mTvAllFenlei;
    @BindView(R.id.img_fenlei)
    ImageView mImgFenlei;
    @BindView(R.id.all_fenlei)
    LinearLayout mAllFenlei;
    @BindView(R.id.tv_paixu)
    TextView mTvPaixu;
    @BindView(R.id.img_paixu)
    ImageView mImgPaixu;
    @BindView(R.id.paixu)
    LinearLayout mPaixu;
    @BindView(R.id.tv_select)
    TextView mTvSelect;
    @BindView(R.id.img_select)
    ImageView mImgSelect;
    @BindView(R.id.select)
    LinearLayout mSelect;
    @BindView(R.id.view_line)
    View mViewLine;
    @BindView(R.id.isHindCategory)
    LinearLayout mIsHindCategory;
    @BindView(R.id.mv_title)
    RelativeLayout mTitle;

    private MovieCategoryActivity currentActivity;
    private HomeAdapter mHomeAdapter;
    private HorizontalAdapter mHorizontalAdapter;
    //测试

    private List<HomeBean.ResultBean.GoodlistBean> mGoodlist;
    private List<MovieBean.ResultBean> mResultlist;
    private List<CategoryBean.DataBean> mDataList;
    private ArrayList<String> mPaixunList;
    private ArrayList<String> mCheckList;
    private View mHeaderView;
    private int adViewTopSpace;
    private int adViewHeight;
    private View itemHeaderAdView;
    private LinearLayout mMovieOne;
    private RecyclerView mRecyclerView;
    private LinearLayout mListFenlei;
    private LinearLayout mListPaixu;
    private LinearLayout mListSelect;
    private View mViewle;
    private TextView mTvFenlei;
    private ImageView mImgFeilei;
    private TextView mPaixuTv;
    private ImageView mPaixuImg;
    private TextView mSelectTv;
    private ImageView mSelectImg;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_category;
    }

    @Override
    protected void initView() {
        currentActivity = this;
        mGoodlist = new ArrayList<>();
        mResultlist = new ArrayList<>();
        mDataList = new ArrayList<>();
        mPaixunList = new ArrayList<>();
        mCheckList = new ArrayList<>();
        String[] list = getResources().getStringArray(R.array.paixun_list);
        String[] selectList = getResources().getStringArray(R.array.select_list);
        for (int i = 0; i < list.length; i++) {
            mPaixunList.add(list[i]);
        }
        for (int i = 0; i < selectList.length; i++) {
            mCheckList.add(selectList[i]);
        }

        initMovieHeader();

        mMoviewList.setOnScrollListener(this);
        mMoviewList.setPullLoadEnable(false);
        mMoviewList.setPullRefreshEnable(true);
        mMoviewList.setXListViewListener(this);
        mMoviewList.setOnItemClickListener(this);
        mMoviewList.addHeaderView(mHeaderView);
        mHomeAdapter = new HomeAdapter(this, mGoodlist);
        mMoviewList.setAdapter(mHomeAdapter);
    }

    private void initMovieHeader() {
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.layout_movie_header, null);
        SupportMultipleScreensUtil.scale(mHeaderView);

        mMovieOne = ButterKnife.findById(mHeaderView, R.id.movie_one);
        mListFenlei = ButterKnife.findById(mHeaderView, R.id.all_fenlei);
        mListPaixu = ButterKnife.findById(mHeaderView, R.id.paixu);
        mListSelect = ButterKnife.findById(mHeaderView, R.id.select);

        mTvFenlei = ButterKnife.findById(mHeaderView, R.id.tv_all_fenlei);
        mImgFeilei = ButterKnife.findById(mHeaderView, R.id.img_fenlei);
        mPaixuTv = ButterKnife.findById(mHeaderView, R.id.tv_paixu);
        mPaixuImg = ButterKnife.findById(mHeaderView, R.id.img_paixu);
        mSelectTv = ButterKnife.findById(mHeaderView, R.id.tv_select);
        mSelectImg = ButterKnife.findById(mHeaderView, R.id.img_select);

        mViewle = ButterKnife.findById(mHeaderView, R.id.le);

        setListener();

        mRecyclerView = ButterKnife.findById(mHeaderView, R.id.horizontal_RecycleView);

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mHorizontalAdapter = new HorizontalAdapter(this, mResultlist);
        mHorizontalAdapter.setRecycleViewOnClickListener(new HorizontalAdapter.OnRecycleViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(mHorizontalAdapter);

    }

    private void setListener() {
        mListFenlei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImgFeilei.setImageResource(R.drawable.iv_filter_checked);
                mTvFenlei.setTextColor(getResources().getColor(R.color.home_title_bar_color));

                mMoviewList.smoothScrollBy(SupportMultipleScreensUtil.getScaleValue(200), 1);

                DoubleListPopWindows doubleListPopWindows = new DoubleListPopWindows(currentActivity, mDataList);
                doubleListPopWindows.showAsDropDown(mViewle);
                doubleListPopWindows.setOnDoubleChangeListener(new DoubleListPopWindows.OnDoubleChangeListener() {
                    @Override
                    public void onDoubleChange(String leftValue, String rightValue) {

                        ToastUtils.showShort(MovieCategoryActivity.this, leftValue + "::" + rightValue);

                        if (rightValue.equals("")) {
                              mTvFenlei.setText(leftValue);
                        } else {
                               mTvFenlei.setText(rightValue);
                        }

                        mImgFeilei.setImageResource(R.drawable.iv_filter);
                        mTvFenlei.setTextColor(getResources().getColor(R.color.more_more_hui));

                    }
                });

                doubleListPopWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mMoviewList.smoothScrollBy(SupportMultipleScreensUtil.getScaleValue(-200), 1);
                        mImgFeilei.setImageResource(R.drawable.iv_filter);
                        mTvFenlei.setTextColor(getResources().getColor(R.color.more_more_hui));

                    }
                });
            }
        });

        mListPaixu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPaixuImg.setImageResource(R.drawable.iv_filter_checked);
                mPaixuTv.setTextColor(getResources().getColor(R.color.home_title_bar_color));

                mMoviewList.smoothScrollBy(SupportMultipleScreensUtil.getScaleValue(100), 1);

                SingleListPopwindows singleListPopwindows = new SingleListPopwindows(currentActivity, mPaixunList);
                singleListPopwindows.showAsDropDown(mViewle);
                singleListPopwindows.setOnSingleChangeListener(new SingleListPopwindows.OnSingleChangeListener() {
                    @Override
                    public void onSingleChange(String text) {
                        mMoviewList.smoothScrollBy(SupportMultipleScreensUtil.getScaleValue(-100), 1);
                        ToastUtils.showShort(currentActivity, text);
                        mPaixuTv.setText(text);
                    }
                });

                singleListPopwindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mMoviewList.smoothScrollBy(SupportMultipleScreensUtil.getScaleValue(-100), 1);
                        mPaixuImg.setImageResource(R.drawable.iv_filter);
                        mPaixuTv.setTextColor(getResources().getColor(R.color.more_more_hui));
                    }
                });

            }
        });

        mListSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoviewList.smoothScrollBy(SupportMultipleScreensUtil.getScaleValue(50), 1);

                mSelectImg.setImageResource(R.drawable.iv_filter_checked);
                mSelectTv.setTextColor(getResources().getColor(R.color.home_title_bar_color));

                CheckListPopwindows checkListPopwindows = new CheckListPopwindows(currentActivity, mCheckList);
                checkListPopwindows.showAsDropDown(mViewle);
                checkListPopwindows.setOnCheckChangeListener(new CheckListPopwindows.OnCheckChangeListener() {
                    @Override
                    public void onCheckChange(ArrayList<String> data) {

                        String text = "";
                        for (int i = 0; i < data.size(); i++) {
                            if (i != (data.size() - 1)) {
                                text += data.get(i) + ",";
                            } else {
                                text += data.get(i);
                            }
                        }
                        ToastUtils.showShort(currentActivity, text);
                    }
                });

                checkListPopwindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mMoviewList.smoothScrollBy(SupportMultipleScreensUtil.getScaleValue(-50), 1);
                        mSelectImg.setImageResource(R.drawable.iv_filter);
                        mSelectTv.setTextColor(getResources().getColor(R.color.more_more_hui));
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        requestHomeList();
    }

    private void requestHomeList() {
        Request<String> recommendRequest1 = NoHttp.createStringRequest(Constants.URL_MOVIEW_LIST, RequestMethod.GET);
        CallServer.getInstance().add(this, 0, recommendRequest1, this, true, true);

        Request<String> recommendRequest2 = NoHttp.createStringRequest(Constants.URL_HOME_LIST, RequestMethod.GET);
        CallServer.getInstance().add(this, 1, recommendRequest2, this, true, true);

        Request<String> recommendRequest = NoHttp.createStringRequest(Constants.URL_CATEGORY_DOUBLE_LIST, RequestMethod.GET);
        CallServer.getInstance().add(this, 2, recommendRequest, this, true, true);

    }

    @OnClick({R.id.map, R.id.ic_back, R.id.all_fenlei, R.id.paixu, R.id.select, R.id.isHindCategory})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.map:

                break;
            case R.id.ic_back:

                break;
            case R.id.all_fenlei:
                mImgFenlei.setImageResource(R.drawable.iv_filter_checked);
                mTvAllFenlei.setTextColor(getResources().getColor(R.color.home_title_bar_color));

                DoubleListPopWindows doubleListPopWindows = new DoubleListPopWindows(this, mDataList);
                doubleListPopWindows.showAsDropDown(mViewLine);
                doubleListPopWindows.setOnDoubleChangeListener(new DoubleListPopWindows.OnDoubleChangeListener() {
                    @Override
                    public void onDoubleChange(String leftValue, String rightValue) {
                        ToastUtils.showShort(MovieCategoryActivity.this, leftValue + "::" + rightValue);
                        if (rightValue.equals("")) {
                            mTvAllFenlei.setText(leftValue);
                        } else {
                            mTvAllFenlei.setText(rightValue);
                        }

                        mImgFenlei.setImageResource(R.drawable.iv_filter);
                        mTvAllFenlei.setTextColor(getResources().getColor(R.color.more_more_hui));
                    }
                });

                doubleListPopWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {//监听popwindow消失
                    @Override
                    public void onDismiss() {
                        mImgFenlei.setImageResource(R.drawable.iv_filter);
                        mTvAllFenlei.setTextColor(getResources().getColor(R.color.more_more_hui));
                    }
                });

                break;
            case R.id.paixu:
                SingleListPopwindows singleListPopwindows = new SingleListPopwindows(this, mPaixunList);
                singleListPopwindows.showAsDropDown(mViewLine);
                singleListPopwindows.setOnSingleChangeListener(new SingleListPopwindows.OnSingleChangeListener() {
                    @Override
                    public void onSingleChange(String text) {
                        ToastUtils.showShort(currentActivity, text);
                        mTvPaixu.setText(text);
                    }
                });

                singleListPopwindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mImgPaixu.setImageResource(R.drawable.iv_filter);
                        mTvPaixu.setTextColor(getResources().getColor(R.color.more_more_hui));
                    }
                });

                break;
            case R.id.select:
                mImgSelect.setImageResource(R.drawable.iv_filter_checked);
                mTvSelect.setTextColor(getResources().getColor(R.color.home_title_bar_color));
                CheckListPopwindows checkListPopwindows = new CheckListPopwindows(currentActivity, mCheckList);
                checkListPopwindows.showAsDropDown(mViewLine);
                checkListPopwindows.setOnCheckChangeListener(new CheckListPopwindows.OnCheckChangeListener() {
                    @Override
                    public void onCheckChange(ArrayList<String> data) {
                        String text = "";
                        for (int i = 0; i < data.size(); i++) {
                            if (i != (data.size() - 1)) {
                                text += data.get(i) + ",";
                            } else {
                                text += data.get(i);
                            }
                        }
                        ToastUtils.showShort(currentActivity, text);
                    }
                });

                checkListPopwindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mImgSelect.setImageResource(R.drawable.iv_filter);
                        mTvSelect.setTextColor(getResources().getColor(R.color.more_more_hui));
                    }
                });

                break;
            case R.id.isHindCategory:

                break;
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response, boolean isNet) {
        Gson gson = new Gson();

        switch (what) {
            case 0:
                mResultlist.clear();
                MovieBean mMovieBean = gson.fromJson(response.get().toString(), MovieBean.class);
                List<MovieBean.ResultBean> resultlist = mMovieBean.getResult();
                mResultlist.addAll(resultlist);
                mHorizontalAdapter.notifyDataSetChanged();
                break;
            case 1:
                mMoviewList.stopRefresh();
                mGoodlist.clear();
                HomeBean mHomeBean = gson.fromJson(response.get().toString(), HomeBean.class);
                List<HomeBean.ResultBean.GoodlistBean> goodlist = mHomeBean.getResult().getGoodlist();
                mGoodlist.addAll(goodlist);
                mHomeAdapter.notifyDataSetChanged();
                break;
            case 2:
                mDataList.clear();
                CategoryBean mCategoryBean = gson.fromJson(response.get().toString(), CategoryBean.class);
                List<CategoryBean.DataBean> mDataBeen = mCategoryBean.getData();
                mDataList.addAll(mDataBeen);
                break;

            default:
                break;
        }

    }

    @Override
    public void onFailed(int what, Response<String> response, boolean isNet) {

    }

    @Override
    public void onRefresh() {
        requestHomeList();
    }

    @Override
    public void onLoadMore() {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (itemHeaderAdView == null) {
            itemHeaderAdView = mMoviewList.getChildAt(1 - firstVisibleItem);
        }

        if (itemHeaderAdView != null) {
            adViewTopSpace = itemHeaderAdView.getTop();
            adViewHeight = mMovieOne.getHeight();
        }

        if (Math.abs(adViewTopSpace) >= adViewHeight) {
            mIsHindCategory.setVisibility(View.VISIBLE);
        } else {
            mIsHindCategory.setVisibility(View.GONE);
        }

        //这是防止滑动过快过猛，itemHeaderAdView.getTop()小于adViewHeight，走了上面判断的else方法
        if (firstVisibleItem > 3){
            mIsHindCategory.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onXScrolling(View view) {

    }
}
