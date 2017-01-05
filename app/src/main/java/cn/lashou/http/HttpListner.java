package cn.lashou.http;

import com.yolanda.nohttp.rest.Response;

/**
 * Created by luow on 16/8/24.
 */
public interface HttpListner<T> {
    void onSucceed(int what, Response<T> response, boolean isNet);
    void onFailed(int what, Response<T> response, boolean isNet);

}
