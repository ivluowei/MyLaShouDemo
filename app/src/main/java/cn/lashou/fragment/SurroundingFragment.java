package cn.lashou.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lashou.R;
import cn.lashou.adapter.HomeAdapter;
import cn.lashou.constants.Constants;
import cn.lashou.core.BaseFragment;
import cn.lashou.entity.CategoryBean;
import cn.lashou.entity.HomeBean;
import cn.lashou.http.CallServer;
import cn.lashou.http.HttpListner;
import cn.lashou.util.ToastUtils;
import cn.lashou.widget.CheckListPopwindows;
import cn.lashou.widget.DoubleListPopWindows;
import cn.lashou.widget.LoadingStateView;
import cn.lashou.widget.SingleListPopwindows;
import cn.lashou.widget.listview.XListView;
import cn.lashou.widget.listview.XListView.IXListViewListener;

/**
 * Created by luow on 2016/8/14.
 */

public class SurroundingFragment extends BaseFragment implements HttpListner, IXListViewListener, OnItemClickListener {

    @BindView(R.id.map)
    ImageView mMap;
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
    View mView;
    @BindView(R.id.surroundlistview)
    XListView mXListView;
    @BindView(R.id.loading)
    LoadingStateView mLoadingStateView;

    private HomeAdapter mHomeAdapter;
    private List<CategoryBean.DataBean> mDataList;
    private List<HomeBean.ResultBean.GoodlistBean> mGoodlist;
    private ArrayList<String> mPaixunList;
    private ArrayList<String> mCheckList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_surrounding;
    }

    @Override
    protected void initView(View view) {
        mPaixunList = new ArrayList<>();
        mDataList = new ArrayList<>();
        mGoodlist = new ArrayList<>();
        mCheckList = new ArrayList<>();
        String[] list = getResources().getStringArray(R.array.paixun_list);
        String[] selectList = getResources().getStringArray(R.array.select_list);
        for (int i = 0; i < list.length; i++) {
            mPaixunList.add(list[i]);
        }
        for (int i = 0; i < selectList.length; i++) {
            mCheckList.add(selectList[i]);
        }
        mXListView.setPullLoadEnable(true);
        mXListView.setPullRefreshEnable(true);
        mXListView.setXListViewListener(this);
        mXListView.setOnItemClickListener(this);
        mHomeAdapter = new HomeAdapter(getActivity(), mGoodlist);
        mXListView.setAdapter(mHomeAdapter);
    }

    @Override
    protected void initData() {

        requestHomeList();

    }

    /**
     * 请求列表数据
     **/
    private void requestHomeList() {
        Request<String> recommendRequest = NoHttp.createStringRequest(Constants.URL_CATEGORY_DOUBLE_LIST, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(), 0, recommendRequest, this, true, true);

        mLoadingStateView.showLoadingView();
        Request<String> recommendRequest2 = NoHttp.createStringRequest(Constants.URL_HOME_LIST, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(), 1, recommendRequest2, this, true, true);
    }

    private Handler mHandler=new Handler();

    /**
     * 请求列表数据
     **/
    private void requestHomeList1() {
        mLoadingStateView.showLoadingView();

        //模拟延迟加载
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Request<String> recommendRequest = NoHttp.createStringRequest(Constants.URL_CATEGORY_DOUBLE_LIST, RequestMethod.GET);
                CallServer.getInstance().add(getActivity(), 0, recommendRequest, SurroundingFragment.this, true, true);


                Request<String> recommendRequest2 = NoHttp.createStringRequest(Constants.URL_HOME_LIST, RequestMethod.GET);
                CallServer.getInstance().add(getActivity(), 1, recommendRequest2, SurroundingFragment.this, true, true);
            }
        },3000);

    }


    @OnClick({R.id.all_fenlei, R.id.paixu, R.id.select,R.id.map})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.map:
                ToastUtils.showShort(getActivity(), "地图");
                break;

            case R.id.all_fenlei:

                mImgFenlei.setImageResource(R.drawable.iv_filter_checked);
                mTvAllFenlei.setTextColor(getResources().getColor(R.color.home_title_bar_color));

                DoubleListPopWindows doubleListPopWindows = new DoubleListPopWindows(getActivity(), mDataList);
                doubleListPopWindows.showAsDropDown(mView);
                doubleListPopWindows.setOnDoubleChangeListener(new DoubleListPopWindows.OnDoubleChangeListener() {
                    @Override
                    public void onDoubleChange(String leftValue, String rightValue) {
                        ToastUtils.showShort(getActivity(), leftValue + "::" + rightValue);
                        if (rightValue.equals("")) {
                            mTvAllFenlei.setText(leftValue);
                        } else {
                            mTvAllFenlei.setText(rightValue);
                        }
                        mImgFenlei.setImageResource(R.drawable.iv_filter);
                        mTvAllFenlei.setTextColor(getResources().getColor(R.color.more_more_hui));


                        requestHomeList1();
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
                mImgPaixu.setImageResource(R.drawable.iv_filter_checked);
                mTvPaixu.setTextColor(getResources().getColor(R.color.home_title_bar_color));
                SingleListPopwindows singleListPopwindows = new SingleListPopwindows(getActivity(), mPaixunList);
                singleListPopwindows.showAsDropDown(mView);
                singleListPopwindows.setOnSingleChangeListener(new SingleListPopwindows.OnSingleChangeListener() {
                    @Override
                    public void onSingleChange(String text) {
                        ToastUtils.showShort(getActivity(), text);
                        mTvPaixu.setText(text);
                        requestHomeList1();
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
                CheckListPopwindows checkListPopwindows = new CheckListPopwindows(getActivity(), mCheckList);
                checkListPopwindows.showAsDropDown(mView);
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

                        requestHomeList1();

                        ToastUtils.showShort(getActivity(), text);
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
        }
    }

    @Override
    public void onSucceed(int what, Response response, boolean isNet) {
        if (!isNet) {
            mXListView.stopRefresh();
            mLoadingStateView.showNoNetView();
            mLoadingStateView.setFullScreenListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestHomeList();
                }
            });

        } else {

            Gson gson = new Gson();
            switch (what) {
                case 0:
                    mDataList.clear();
                    CategoryBean mCategoryBean = gson.fromJson(response.get().toString(), CategoryBean.class);
                    List<CategoryBean.DataBean> mDataBeen = mCategoryBean.getData();
                    mDataList.addAll(mDataBeen);
                    break;
                case 1:

                    mLoadingStateView.hide();
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

    }


    @Override
    public void onFailed(int what, Response response, boolean isNet) {
        if (!isNet) {
            mLoadingStateView.showNoDataView();
        }
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
}
