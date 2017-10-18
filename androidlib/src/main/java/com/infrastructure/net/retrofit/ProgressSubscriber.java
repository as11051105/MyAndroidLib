package com.infrastructure.net.retrofit;

import android.content.Context;
import android.text.TextUtils;

import com.infrastructure.util.DialogUtils;
import com.infrastructure.util.NetworkUtils;
import com.infrastructure.util.logger.LFLogger;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DefaultObserver;


/**
 * 1、用于在Http请求开始时，自动显示一个ProgressDialog 2、在Http请求结束时，关闭ProgressDialog 调用者自己对请求数据进行处理
 */
public class ProgressSubscriber<T> extends DefaultObserver<T> {

    //    回调接口
    private RetrofitRespListener retrofitRespListener;
    //    弱引用反正内存泄露
    private WeakReference<Context> mActivity;
    //    是否能取消请求
    private boolean isShowDialog;

    //  private String msg = "正在加载数据，请稍候...";
    private String msg = "";

    public ProgressSubscriber(Context context, RetrofitRespListener retrofitRespListener) {
        this.retrofitRespListener = retrofitRespListener;
        this.mActivity = new WeakReference<>(context);
        this.isShowDialog = false;
    }

    public ProgressSubscriber(Context context, RetrofitRespListener retrofitRespListener, boolean cancel) {
        this.retrofitRespListener = retrofitRespListener;
        this.mActivity = new WeakReference<>(context);
        this.isShowDialog = cancel;
    }

    public ProgressSubscriber(Context context, RetrofitRespListener retrofitRespListener, boolean cancel, String msg) {
        this.retrofitRespListener = retrofitRespListener;
        this.mActivity = new WeakReference<>(context);
        this.isShowDialog = cancel;
        this.msg = msg;
    }

    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        Context context = mActivity.get();
        if (isShowDialog) {
            if (TextUtils.isEmpty(msg)) {
                DialogUtils.showProgressDialog(context, 0, "正在加载数据，请稍候...");
            } else {
                DialogUtils.showProgressDialog(context, 0, msg);
            }
        }
    }

    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        Context context = mActivity.get();
        if (isShowDialog) {
            DialogUtils.removeDialog(context);
        }
       // onCancelProgress();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理 隐藏ProgressDialog
     */
    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        Context context = mActivity.get();
        if (context == null) {
            return;
        }
        LFLogger.i("onError()--->" + e.toString());
        if (NetworkUtils.isAvailable(context) == false) {
            if (retrofitRespListener != null) {
                LFLogger.i("onError()--->当前网络不可用，请检查网络");
                retrofitRespListener.onError("当前网络不可用，请检查网络");
            }
            return;
        }
        if (e instanceof SocketTimeoutException) {
            if (retrofitRespListener != null) {
                LFLogger.i("onError()--->连接超时，请检查您的网络状态");
                retrofitRespListener.onError("连接超时，请检查您的网络状态");
            }
        } else if (e instanceof ConnectException) {
            if (retrofitRespListener != null) {
                LFLogger.i("onError()--->网络中断，请检查您的网络状态");
                retrofitRespListener.onError("网络中断，请检查您的网络状态");
            }
        } else {
            if (retrofitRespListener != null) {
                LFLogger.i("onError()--->接口访问错误");
                retrofitRespListener.onError("接口访问错误");
            }
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        dismissProgressDialog();
        if (retrofitRespListener != null) {
            retrofitRespListener.onNext(t + "");
        }
    }

//    /**
//     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
//     */
//    public void onCancelProgress() {
//        dismissProgressDialog();
//        if (!this..isUnsubscribed()) {
//            c();
//        }
//    }
}