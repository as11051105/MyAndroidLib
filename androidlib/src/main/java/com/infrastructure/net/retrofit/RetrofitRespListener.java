package com.infrastructure.net.retrofit;

/**
 * 成功回调处理
 * Created by WZG on 2016/7/16.
 */
public interface RetrofitRespListener<T> {
    void onNext(String data);

    void onError(String msg);
}
