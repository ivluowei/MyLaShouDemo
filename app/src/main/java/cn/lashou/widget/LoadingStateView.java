package cn.lashou.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lashou.R;

/**
 * loadingDialog
 */
public class LoadingStateView extends FrameLayout {

    private LayoutInflater mInflater;
    //loading中
    private View mStateLayout;
    private View mLoadingLayout;
    private ImageView mLoadingView;

    //no net
    private LinearLayout mStateNoNetLayout;
    private TextView mNoNetTv;

    //no data
    private LinearLayout mStateNoDataLayout;
    private TextView mNoDataTv;

    private AnimationDrawable mAnim;

    private boolean isLoading = false;
    private boolean isShowNoDataView;

    public LoadingStateView(Context context) {
        super(context);
        init(context);
    }

    public LoadingStateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public LoadingStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        mStateLayout = mInflater.inflate(R.layout.load_state_layout, this);
        SupportMultipleScreensUtil.scale(mStateLayout);
        mLoadingLayout = mStateLayout.findViewById(R.id.state_loading_layout);
        mLoadingView = (ImageView) mStateLayout.findViewById(R.id.state_loading);
        mAnim = (AnimationDrawable) mLoadingView.getDrawable();

        mStateNoNetLayout = (LinearLayout) mStateLayout.findViewById(R.id.state_no_net);
        mNoNetTv = (TextView) mStateLayout.findViewById(R.id.load_no_net_text);

        mStateNoDataLayout = (LinearLayout) mStateLayout.findViewById(R.id.state_no_data_layout);
        mNoDataTv = (TextView) mStateLayout.findViewById(R.id.no_data_tv);

    }

    public void setFullScreenListener(OnClickListener l) {
        mStateLayout.setOnClickListener(l);
    }

    //loading中
    public void showLoadingView() {
        if (this.getVisibility() == View.GONE) {
            this.setVisibility(View.VISIBLE);
        }

        mLoadingLayout.setVisibility(View.VISIBLE);

        if (!mAnim.isRunning()) {
            mAnim.start();
        }
        isLoading = true;
        isShowNoDataView = false;

        mStateNoNetLayout.setVisibility(View.GONE);
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isShowNoDataView() {
        return isShowNoDataView;
    }

    //no net
    public void showNoNetView() {
        if (this.getVisibility() == View.GONE) {
            this.setVisibility(View.VISIBLE);
        }
        isLoading = false;
        isShowNoDataView = false;
        mStateNoNetLayout.setVisibility(View.VISIBLE);
        mNoNetTv.setText("点击重新加载试试");

        mLoadingLayout.setVisibility(View.GONE);
        mStateNoDataLayout.setVisibility(View.GONE);
    }

    public void showNoDataView() {
        if (this.getVisibility() == View.GONE) {
            this.setVisibility(View.VISIBLE);
        }
        isLoading = false;
        isShowNoDataView = false;
        mStateNoDataLayout.setVisibility(View.VISIBLE);
        mNoDataTv.setText("暂时还没数据啊");

        mLoadingLayout.setVisibility(View.GONE);
        mStateNoNetLayout.setVisibility(View.GONE);
    }

    public void showNoDataView(String text) {
        if (this.getVisibility() == View.GONE) {
            this.setVisibility(View.VISIBLE);
        }
        isLoading = false;
        isShowNoDataView = false;
        mStateNoDataLayout.setVisibility(View.VISIBLE);
        mNoNetTv.setText(text);

        mLoadingLayout.setVisibility(View.GONE);
        mStateNoNetLayout.setVisibility(View.GONE);
    }

    public void hide() {
        if (mAnim.isRunning()) {
            mAnim.stop();
        }
        this.setVisibility(View.GONE);
    }

}
