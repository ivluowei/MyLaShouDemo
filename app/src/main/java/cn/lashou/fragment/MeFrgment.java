package cn.lashou.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lashou.R;
import cn.lashou.activity.LoginActivity;
import cn.lashou.core.BaseFragment;
import cn.lashou.entity.UserBean;

/**
 * Created by luow on 2016/8/14.
 */

public class MeFrgment extends BaseFragment {


    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.unlogin)
    LinearLayout mUnlogin;
    @BindView(R.id.user_icon)
    SimpleDraweeView mUserIcon;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.login_in)
    RelativeLayout mLoginIn;
    @BindView(R.id.lashouquan)
    LinearLayout mLashouquan;
    @BindView(R.id.collection)
    LinearLayout mCollection;
    @BindView(R.id.liulan)
    LinearLayout mLiulan;
    @BindView(R.id.wait_pay)
    LinearLayout mWaitPay;
    @BindView(R.id.pay)
    LinearLayout mPay;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.wait_pinjia)
    LinearLayout mWaitPinjia;
    @BindView(R.id.imageView2)
    ImageView mImageView2;
    @BindView(R.id.imageView3)
    ImageView mImageView3;
    @BindView(R.id.textView2)
    TextView mTextView2;
    @BindView(R.id.tuijian)
    RelativeLayout mTuijian;
    @BindView(R.id.imageView5)
    ImageView mImageView5;
    @BindView(R.id.imageView4)
    ImageView mImageView4;
    @BindView(R.id.textView3)
    TextView mTextView3;
    @BindView(R.id.pingjia)
    RelativeLayout mPingjia;
    @BindView(R.id.imageView6)
    ImageView mImageView6;
    @BindView(R.id.imageView7)
    ImageView mImageView7;
    @BindView(R.id.textView4)
    TextView mTextView4;
    @BindView(R.id.choujiang)
    RelativeLayout mChoujiang;
    @BindView(R.id.imageView77)
    ImageView mImageView77;
    @BindView(R.id.imageView8)
    ImageView mImageView8;
    @BindView(R.id.textView5)
    TextView mTextView5;
    @BindView(R.id.textView7)
    TextView mTextView7;
    @BindView(R.id.enter_home)
    RelativeLayout mEnterHome;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView(View view) {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleUserInfo(UserBean bean) {
            mUnlogin.setVisibility(View.GONE);
            mLoginIn.setVisibility(View.VISIBLE);
            mUserIcon.setImageURI(Uri.parse(bean.getHeaderIcon()));
            mUserName.setText(bean.getName());
    }

    @Override
    @OnClick({R.id.login, R.id.lashouquan, R.id.collection, R.id.liulan, R.id.wait_pay, R.id.pay, R.id.wait_pinjia, R.id.tuijian, R.id.pingjia, R.id.choujiang, R.id.enter_home})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.lashouquan:
                break;
            case R.id.collection:
                break;
            case R.id.liulan:
                break;
            case R.id.wait_pay:
                break;
            case R.id.pay:
                break;
            case R.id.wait_pinjia:
                break;
            case R.id.tuijian:
                break;
            case R.id.pingjia:
                break;
            case R.id.choujiang:
                break;
            case R.id.enter_home:
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
