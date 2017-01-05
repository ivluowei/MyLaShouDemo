package cn.lashou;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

import cn.lashou.widget.SupportMultipleScreensUtil;
import cn.sharesdk.framework.ShareSDK;
import timber.log.Timber;

/**
 * Created by luow on 2016/8/14.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.setDebug(true); // 开启NoHttp调试模式。
        Logger.setTag("specter"); // 设置NoHttp打印Log的TAG
        NoHttp.initialize(this, new NoHttp.Config()
                .setConnectTimeout(30 * 1000) // 全局连接超时时间，单位毫秒。
                .setReadTimeout(30 * 1000) // 全局服务器响应超时时间，单位毫秒。
                .setNetworkExecutor(new OkHttpNetworkExecutor()) // 使用Okhttp做网络层
        );

        ShareSDK.initSDK(this);

        Fresco.initialize(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            //  Timber.plant(new );
        }
        SupportMultipleScreensUtil.init(this);
    }
}
