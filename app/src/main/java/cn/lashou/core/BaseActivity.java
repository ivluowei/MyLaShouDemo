package cn.lashou.core;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import cn.lashou.constants.Constants;
import cn.lashou.util.ToastUtils;
import cn.lashou.widget.SupportMultipleScreensUtil;

/**
 * Created by lw on 2016/6/20.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    private static long lastTimeStamp = 0l;
    public static final int MODE_BACK = 0;
    public static final int MODE_DRAWER = 1;
    public static final int MODE_NONE = 2;
    public static final int MODE_HOME = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (0) {
            case Constants.STATUS_FORCE_KILLED:
                protectApp();
                break;
            case Constants.STATUS_LOGOUT:
            case Constants.STATUS_OFFLINE:
            case Constants.STATUS_ONLINE:
                if (getLayoutId() != 0) {
                    setContentView(getLayoutId());
                    View rootView = findViewById(android.R.id.content);
                    SupportMultipleScreensUtil.scale(rootView);
                    ButterKnife.bind(this);
                }
                initView();
                initData();
                break;
        }
    }

    protected abstract void initView();
    protected abstract void initData();

    protected int getLayoutId() {
        return 0;
    }

    protected void protectApp() {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("appStatus", ConstantsValue.STATUS_RESTART_APP);
//        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 退出程序.
     *
     * @param context
     */
    public static void exitApplication(Activity context) {
        long currentTimeStamp = System.currentTimeMillis();
        if (currentTimeStamp - lastTimeStamp > 1350L) {
            ToastUtils.showToast(context, "再按一次退出");
        } else {
            context.finish();
        }
        lastTimeStamp = currentTimeStamp;
    }

}
