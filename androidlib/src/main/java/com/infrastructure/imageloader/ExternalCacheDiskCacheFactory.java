package com.infrastructure.imageloader;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;

import java.io.File;

/**
 * 类名：
 * <p>
 * 功能：
 *
 * @author Mr.Z
 * @dataTime 2016/8/5 14:29
 */

public class ExternalCacheDiskCacheFactory extends DiskLruCacheFactory {
    public ExternalCacheDiskCacheFactory(Context context) {
        this(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
    }

    public ExternalCacheDiskCacheFactory(Context context, int diskCacheSize) {
        this(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR, diskCacheSize);
    }

    public ExternalCacheDiskCacheFactory(final Context context, final String diskCacheName, int diskCacheSize) {
        super(new CacheDirectoryGetter() {
            @Override
            public File getCacheDirectory() {
                File cacheDirectory;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 如果SD卡可以用的话把图片缓存到SD卡上
                    cacheDirectory = context.getExternalCacheDir();
                } else {
                    // 把图片缓存到应用data/data/包/...下
                    cacheDirectory = context.getCacheDir();
                }
                if (cacheDirectory == null) {
                    return null;
                }
                if (diskCacheName != null) {
                    return new File(cacheDirectory, diskCacheName);
                }
                return cacheDirectory;
            }
        }, diskCacheSize);
    }
}
