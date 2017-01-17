package cn.lashou;

import android.app.Application;
import android.os.Environment;

import com.alipay.euler.andfix.patch.PatchManager;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yolanda.nohttp.NoHttp;

import java.io.IOException;

import cn.lashou.util.AppStatusTracker;
import cn.lashou.widget.SupportMultipleScreensUtil;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by luow on 2016/8/14.
 */

public class MyApplication extends Application {

    private static final String APATCH_PATH = "/out.apatch";

    private PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化
        mPatchManager = new PatchManager(this);
        mPatchManager.init("1.0"); // 版本号
        // 加载 apatch
        mPatchManager.loadPatch();

        // add patch at runtime
        try {
            // .apatch file path
            String patchFileString = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() +"/MyLaShou" + APATCH_PATH;
            mPatchManager.addPatch(patchFileString);
        } catch (IOException e) {

        }

        AppStatusTracker.init();

        NoHttp.initialize(this, new NoHttp.Config()
                .setConnectTimeout(30 * 1000) // 全局连接超时时间，单位毫秒。
                .setReadTimeout(30 * 1000) // 全局服务器响应超时时间，单位毫秒。
                .setNetworkExecutor(new OkHttpNetworkExecutor()) // 使用Okhttp做网络层
        );

        ShareSDK.initSDK(this);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getApplicationContext());

        Fresco.initialize(this);

        SupportMultipleScreensUtil.init(this);

    }

//    public void addPatch(String path) throws IOException {
//        File src = new File(path);
//     //   File dest = new File(mPatchDir, src.getName());
//        if (!src.exists()) {
//            throw new FileNotFoundException(path);
//        }
//        if (dest.exists()) {
//            Log.d(TAG, "patch [" + src.getName() + "] has be loaded.");
//            boolean deleteResult = dest.delete();
//            if (deleteResult)
//                Log.e(TAG, "patch [" + dest.getPath() + "] has be delete.");
//            else {
//                Log.e(TAG, "patch [" + dest.getPath() + "] delete error");
//                return;
//            }
//        }
//        FileUtil.copyFile(src, dest);// copy to patch's directory
//        Patch patch = addPatch(dest);
//        if (patch != null) {
//            mPatchManager.addPatch();
//        }
//    }

}
