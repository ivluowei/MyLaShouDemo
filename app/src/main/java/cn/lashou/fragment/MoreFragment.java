package cn.lashou.fragment;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lashou.R;
import cn.lashou.core.BaseFragment;
import cn.lashou.util.ToastUtils;

/**
 * Created by luow on 2016/8/14.
 */

public class MoreFragment extends BaseFragment {

    @BindView(R.id.wifi_cb)
    CheckBox mWifiCb;
    @BindView(R.id.more_wifi)
    RelativeLayout mMoreWifi;
    @BindView(R.id.remind_cb)
    CheckBox mRemindCb;
    @BindView(R.id.more_mind)
    RelativeLayout mMoreMind;
    @BindView(R.id.more_clean)
    RelativeLayout mMoreClean;
    @BindView(R.id.more_good)
    RelativeLayout mMoreGood;
    @BindView(R.id.more_advice)
    RelativeLayout mMoreAdvice;
    @BindView(R.id.kefu)
    TextView mKefu;
    @BindView(R.id.more_kefu)
    RelativeLayout mMoreKefu;
    @BindView(R.id.more_check)
    RelativeLayout mMoreCheck;
    @BindView(R.id.more_about)
    RelativeLayout mMoreAbout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    @OnClick({R.id.more_wifi, R.id.more_mind, R.id.more_clean, R.id.more_good, R.id.more_advice, R.id.more_kefu, R.id.more_check, R.id.more_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more_wifi:
                ToastUtils.showShort(getActivity(),"wifi");
                break;
            case R.id.more_mind:
                ToastUtils.showShort(getActivity(),"more_mind");
                break;
            case R.id.more_clean:
                ToastUtils.showShort(getActivity(),"more_clean");
                break;
            case R.id.more_good:
                ToastUtils.showShort(getActivity(),"more_good");
                break;
            case R.id.more_advice:
                ToastUtils.showShort(getActivity(),"more_advice");
                break;
            case R.id.more_kefu:
                ToastUtils.showShort(getActivity(),"more_kefu");
                break;
            case R.id.more_check:
                ToastUtils.showShort(getActivity(),"more_check");
                break;
            case R.id.more_about:
                ToastUtils.showShort(getActivity(),"more_about");
                break;
        }
    }
}
