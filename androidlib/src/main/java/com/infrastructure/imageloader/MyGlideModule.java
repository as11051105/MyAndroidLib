package com.infrastructure.imageloader;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * 类名：
 * <p>
 * 功能：
 *
 * @author Mr.Z
 * @dataTime 2016/8/5 14:30
 */
@GlideModule
public class MyGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 配置图片将缓存到SD卡
//        ExternalCacheDiskCacheFactory externalCacheDiskCacheFactory = new ExternalCacheDiskCacheFactory(context);
//        builder.setDiskCache(externalCacheDiskCacheFactory);
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
//        //设置memory和Bitmap池的大小
//        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
//        builder.setMemoryCache(new LruResourceCache((int) (1.2 * calculator.getMemoryCacheSize())));
//        builder.setBitmapPool(new LruBitmapPool((int) (1.2 * calculator.getBitmapPoolSize())));
//        //内存缓存相关,默认是20m
        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));


        //设置磁盘缓存及其路径
        //
        int MAX_CACHE_SIZE = 100 * 1024 * 1024;
        String CACHE_FILE_NAME = "imgCache";
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, CACHE_FILE_NAME, MAX_CACHE_SIZE));
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String downloadDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                    CACHE_FILE_NAME;
            //路径---->sdcard/imgCache
            builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, MAX_CACHE_SIZE));
        } else {
            //路径---->/sdcard/Android/data/<application package>/cache/imgCache
            builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, CACHE_FILE_NAME, MAX_CACHE_SIZE));
        }
    }


//    @Override
//    public void registerComponents(Context context, Registry registry) {
//        //配置glide网络加载框架
//        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
//    }

    @Override
    public boolean isManifestParsingEnabled() {
        //不使用清单配置的方式,减少初始化时间
        return false;
    }
}
