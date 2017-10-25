package com.infrastructure.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class ImageLoaderUtils {

    /**
     * 加载原图片
     *
     * @param context
     * @param url
     * @param id
     * @param target
     */
    public static void loadImg(Context context, String url, int id, ImageView target) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .centerCrop()//图片显示类型
                        .placeholder(id)//加载中图片
                        .error(id)//加载错误的图片
                        .priority(Priority.HIGH)//设置请求优先级
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//全部缓存
                )
                .transition(new DrawableTransitionOptions().crossFade(1000))//渐显效果
                .into(target);
    }


    /**
     * 加载bitmap图片
     *
     * @param context
     * @param bitmap
     * @param id
     * @param target
     */
    public static void loadImg(Context context, Bitmap bitmap, int id, ImageView target) {
        Glide.with(context)
                .asBitmap()
                .load(bitmap)
                .apply(new RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .centerCrop()//图片显示类型
                        .placeholder(id)//加载中图片
                        .error(id)//加载错误的图片
                        .priority(Priority.HIGH)//设置请求优先级
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//全部缓存
                )
                .transition(new BitmapTransitionOptions().crossFade(1000))//渐显效果
                .into(target);
    }

    /**
     * 加载byte图片
     *
     * @param context
     * @param bytes
     * @param id
     * @param target
     */
    public static void loadImg(Context context, byte[] bytes, int id, ImageView target) {
        Glide.with(context)
                .load(bytes)
                .apply(new RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .fitCenter()//图片显示类型
                        .placeholder(id)//加载中图片
                        .error(id)//加载错误的图片
                        .priority(Priority.HIGH)//设置请求优先级
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//全部缓存
                )
                .transition(new DrawableTransitionOptions().crossFade(1000))//渐显效果
                .into(target);
    }

    /**
     * 加载git图片
     *
     * @param context
     * @param url
     * @param id
     * @param target
     */
    public static void loadGitImg(Context context, String url, int id, ImageView target) {
        Glide.with(context)
                .asGif()
                .load(url)
                .apply(new RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .centerCrop()//图片显示类型
                        .placeholder(id)//加载中图片
                        .error(id)//加载错误的图片
                        .priority(Priority.HIGH)//设置请求优先级
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//全部缓存
                )
                .transition(new DrawableTransitionOptions().crossFade(1000))//渐显效果
                .into(target);
    }

    /**
     * 加载圆形头像
     *
     * @param context 如果是activity glide会与其生命周期关联,在onStop()中取消加载图片,如果
     *                想要始终加载图片则需要传入Application实例
     * @param url
     * @param id
     * @param target
     */
    public static void loadRoundImg(Context context, String url, int id, ImageView target) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .centerCrop()//图片显示类型
                        .placeholder(id)//加载中图片
                        .error(id)//加载错误的图片
                        .priority(Priority.HIGH)//设置请求优先级
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//全部缓存
                        .circleCrop()//设置成圆形头像<这个是V4.0新增的>
                )
                .transition(new DrawableTransitionOptions().crossFade(1000))//渐显效果
                .into(target);
    }

    /**
     * 加载圆角图片
     *
     * @param context 如果是activity glide会与其生命周期关联,在onStop()中取消加载图片,如果
     *                想要始终加载图片则需要传入Application实例
     * @param url
     * @param id
     * @param target
     */
    public static void loadRoundedCornersImg(Context context, String url, int id, ImageView target) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .centerCrop()//图片显示类型
                        .placeholder(id)//加载中图片
                        .error(id)//加载错误的图片
                        .priority(Priority.HIGH)//设置请求优先级
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//全部缓存
                        .transform(new RoundedCorners(10))//设置成圆角头像<这个是V4.0新增的>
                )
                .transition(new DrawableTransitionOptions().crossFade(1000))//渐显效果
                .into(target);
    }


}
