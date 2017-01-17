package cn.lashou.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import cn.lashou.R;
import cn.lashou.activity.SplashActivity;
import cn.lashou.constants.Constants;
import cn.lashou.fragment.HomeFragment;
import cn.lashou.fragment.MeFrgment;
import cn.lashou.fragment.MoreFragment;
import cn.lashou.fragment.SurroundingFragment;
import cn.lashou.util.DownLoadManager;
import cn.lashou.util.ToastUtils;
import cn.lashou.widget.CustTabWidget;

/**
 * Created by luow on 2016/8/10.
 */
public class MainActivity extends AppCompatActivity implements CustTabWidget.onTabSelectedListener {
    private static long lastTimeStamp = 0l;
    private CustTabWidget mCustTabWidget;
    private HomeFragment mHomeFragment;
    private SurroundingFragment mSurroundingFragment;
    private MeFrgment mMeFrgment;
    private MoreFragment mMoreFragment;
    private int mIndex = Constants.HOME_FRAGMENT_INDEX;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DownLoadManager.getFileFromServer(Constants.URL_AND_FIX);
                } catch (Exception e) {

                }
            }
        }).start();


        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mContext = MainActivity.this;
        initData();
    }

    private void initData() {
        mCustTabWidget = (CustTabWidget) findViewById(R.id.tab_widget);
        mCustTabWidget.setOnTabSelectedListener(this);
        mCustTabWidget.setTabDisplay(this, mIndex);
        mHomeFragment = new HomeFragment();
        mSurroundingFragment = new SurroundingFragment();
        mMeFrgment = new MeFrgment();
        mMoreFragment = new MoreFragment();
        initTab(mIndex);

    }

    @Override
    public void onTabSelecete(int index) {
        initTab(index);
    }

    public void setTab(int index) {
        mCustTabWidget.setTabDisplay(this, index);
        initTab(index);
    }

    private void initTab(int index) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        hideFragments(beginTransaction);
        switch (index) {
            case Constants.HOME_FRAGMENT_INDEX:

                if (!mHomeFragment.isAdded()) {
                    beginTransaction.add(R.id.content_frame, mHomeFragment);
                }
                beginTransaction.show(mHomeFragment);

                break;
            case Constants.SURRONNDING_FRAGMENT_INDEX:

                if (!mSurroundingFragment.isAdded()) {
                    beginTransaction.add(R.id.content_frame, mSurroundingFragment);
                }
                beginTransaction.show(mSurroundingFragment);

                break;
            case Constants.ME_FRAGMENT_INDEX:

                if (!mMeFrgment.isAdded()) {
                    beginTransaction.add(R.id.content_frame, mMeFrgment);
                }
                beginTransaction.show(mMeFrgment);

                break;
            case Constants.MORE_FRAGMENT_INDEX:

                if (!mMoreFragment.isAdded()) {
                    beginTransaction.add(R.id.content_frame, mMoreFragment);
                }

                beginTransaction.show(mMoreFragment);

                break;
            default:
                break;
        }
        beginTransaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mSurroundingFragment != null) {
            transaction.hide(mSurroundingFragment);
        }
        if (mMeFrgment != null) {
            transaction.hide(mMeFrgment);
        }
        if (mMoreFragment != null) {
            transaction.hide(mMoreFragment);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //    super.onSaveInstanceState(outState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        switch (intent.getIntExtra("appStatus", 0)) {
            case Constants.STATUS_RESTART_APP:
                protectApp();
                break;
            case Constants.STATUS_LOGOUT:
                protectApp();
                break;
        }
    }

    protected void protectApp() {
        startActivity(new Intent(MainActivity.this, SplashActivity.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (KeyEvent.KEYCODE_BACK == keyCode) {
            exitApplication();
        }
        return false;
    }

    /**
     * 退出程序.
     */
    public void exitApplication() {
        long currentTimeStamp = System.currentTimeMillis();
        if (currentTimeStamp - lastTimeStamp > 1350L) {
            ToastUtils.showToast(mContext, "再按一次退出");
        } else {
            finish();
        }
        lastTimeStamp = currentTimeStamp;
    }

}
