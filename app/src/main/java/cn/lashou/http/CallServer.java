package cn.lashou.http;

import android.content.Context;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by luow on 16/8/24.
 */
public class CallServer {

    private final RequestQueue mQueue;

    private CallServer(){
        mQueue = NoHttp.newRequestQueue();
    }

    private static CallServer callServer;


    public synchronized static CallServer getInstance(){
        if (callServer == null){
            callServer = new CallServer();
        }
        return callServer;
    }

    /***
     * 添加一个请求到队列中的
     */
    public <T> void add(Context context, int what, Request<T> request
            , HttpListner<T> httpListner, boolean canCancle, boolean isLoading){
        mQueue.add(what,request,new HttpResponseListner<T>(context,request,httpListner,canCancle,isLoading));
    }




}
