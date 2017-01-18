package cn.lashou.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import cn.lashou.R;
import cn.lashou.constants.Constants;
import cn.lashou.core.BaseActivity;
import cn.lashou.ui.MainActivity;
import cn.lashou.util.AppStatusTracker;

/**
 * Created by luow on 2016/8/10.
 */
public class SplashActivity extends BaseActivity {
    private Handler mHandler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppStatusTracker.getInstance().setAppStatus(Constants.STATUS_ONLINE);

        super.onCreate(savedInstanceState);

        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 1000);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
