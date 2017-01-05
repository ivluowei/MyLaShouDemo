package cn.lashou.http;

import android.content.Context;

import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import cn.lashou.util.NetUtils;

/**
 * Created by luow on 16/8/24.
 */
public class HttpResponseListner<T> implements OnResponseListener<T> {

    private HttpListner<T> mListner;

    //  private WaitDialog mWaitDialog;

    private boolean isLoading;

    private Request<T> mRequest;

    private Context mContext;

    public HttpResponseListner(Context context, Request<T> request, HttpListner<T> listner, boolean canCancle, boolean isLoading) {
        this.mRequest = request;
        this.mRequest = request;
        this.mContext = context;
        if (context != null) {
//            mWaitDialog = new WaitDialog(context);
//            mWaitDialog.setCancelable(canCancle);
//            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialogInterface) {
//                    mWaitDialog.cancel();
            //       }
            //    });
        }
        mListner = listner;
    }


    @Override
    public void onStart(int what) {

    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        if (mListner != null) {
            if (NetUtils.isConnected(mContext)) {
                mListner.onSucceed(what, response, true);
            }else{
                mListner.onSucceed(what, response, false);
            }
        }
    }

    @Override
    public void onFailed(int what, Response<T> response) {
        if (mListner != null) {
            if (NetUtils.isConnected(mContext)) {
                mListner.onFailed(what, response, true);
            } else {
                mListner.onFailed(what, response, false);
            }
        }
    }

    @Override
    public void onFinish(int what) {

    }
}
