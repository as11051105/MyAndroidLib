package com.infrastructure.net.retrofit;

import android.content.Context;

import com.infrastructure.net.retrofit.progress.ProgressHelper;
import com.infrastructure.util.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitService {

    private volatile static RetrofitService INSTANCE;

    //获取单例
    public static RetrofitService getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitService();
                }
            }
        }
        return INSTANCE;
    }

    public <T> T createRetrofitService(final Context context, final Class<T> service, File cacheFile, String baseUrl, boolean isDebug) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();//
        if (isDebug) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            // 设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }
        // File cacheFile = LFApplication.lfApplication.getFileManager().getExternalCacheDir();
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isConnectivity(context)) {
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtils.isConnectivity(context)) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()//
                            .header("Cache-Control", "public, max-age=" + maxAge)//
                            .removeHeader("ielts")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为1天
                    int maxStale = 60 * 60 * 24 * 1;
                    response.newBuilder()//
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)//
                            .removeHeader("ielts")//
                            .build();
                }
                return response;
            }
        };
        builder.cache(cache).addInterceptor(cacheInterceptor);

        // 设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        // 错误重连
        builder.retryOnConnectionFailure(true);

        Retrofit retrofit = new Retrofit.Builder()//
                .client(ProgressHelper.addProgress(builder).build())//
                .addConverterFactory(JsonConverterFactory.create())//
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//
                .baseUrl(baseUrl + "/")//
                .build();
        return retrofit.create(service);
    }

}
