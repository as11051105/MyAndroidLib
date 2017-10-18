package com.infrastructure.util;

import android.util.Log;
import android.view.View;

/**
 * 类名: CommonUtils.java
 * <p>
 * 功能:
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

//    public static boolean isViewFastDoubelClicked(View currentView) {
//        if (lastView != null && lastView == currentView && System.currentTimeMillis() - lastClickTime < 1000) {
//            Log.d("DoubleClicked", "View doubleClicked");
//            lastClickTime = System.currentTimeMillis();
//            return true;
//        }
//        Log.d("DoubleClicked", "View clicked");
//        lastView = currentView;
//        lastClickTime = System.currentTimeMillis();
//        return false;
//    }



    public static boolean isViewFastDoubelClicked(View currentView) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

//    public static Object getAndroidManifestMetaData(String key) {
//        Object value = null;
//        PackageManager packageManager = LFApplication.lfApplication.getApplicationContext().getPackageManager();
//        ApplicationInfo applicationInfo;
//        try {
//            applicationInfo = packageManager.getApplicationInfo(LFApplication.lfApplication.getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
//            if (applicationInfo != null && applicationInfo.metaData != null) {
//                value = applicationInfo.metaData.get(key);
//            }
//        } catch (NameNotFoundException e) {
//            throw new RuntimeException("Could not read the name in the manifest file.", e);
//        }
//        if (value == null) {
//            throw new RuntimeException("The name '" + key + "' is not defined in the manifest file's meta data.");
//        }
//        return value.toString();
//
//    }
//
//    public static String getMacAddress(Context context) {
//        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        return wifiManager.getConnectionInfo().getMacAddress();
//    }


}
