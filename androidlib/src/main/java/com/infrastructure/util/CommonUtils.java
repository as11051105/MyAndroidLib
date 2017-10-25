package com.infrastructure.util;

import android.util.Log;
import android.view.View;

/**
 * 类名: CommonUtils.java
 * <p>
 * 功能: 防止双击
 *
 * @author Mr.Z
 * @version 1.0
 * @dateTime 2015-2-9 上午11:18:32
 */
public class CommonUtils {

    private static View lastView;
    private static long lastClickTime;

    /**
     * 同一个View是否被快速点击了多遍, times 时间间隔 在这个间隔内被视为重复点击
     */
    public static boolean isViewFastDoubelClicked(View currentView, long times) {
        if (lastView != null && lastView == currentView && System.currentTimeMillis() - lastClickTime < times) {
            Log.d("DoubleClicked", "View doubleClicked");
            lastClickTime = System.currentTimeMillis();
            return true;
        }
        Log.d("DoubleClicked", "View clicked");
        lastView = currentView;
        lastClickTime = System.currentTimeMillis();
        return false;

    }



    public static boolean isViewFastDoubelClicked(View currentView) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }



}
