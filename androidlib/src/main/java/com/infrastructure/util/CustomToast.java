package com.infrastructure.util;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

public class CustomToast {

    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public static void showToast(Context mContext, String text, int duration) {
        if (!TextUtils.isEmpty(text)) {
            mHandler.removeCallbacks(r);
            if (mToast != null) {
                mToast.setText(text);
            } else {
                mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
                // mToast.setGravity(Gravity.BOTTOM, 0, 0);
            }
            mHandler.postDelayed(r, duration);
            mToast.show();
        }
    }

    @SuppressWarnings("static-access")
    public static void showToast(Context mContext, String text) {
        if (!TextUtils.isEmpty(text)) {
            mHandler.removeCallbacks(r);
            if (mToast != null) {
                mToast.setText(text);
            } else {
                mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
                // mToast.setGravity(Gravity.BOTTOM, 0, 0);
            }
            mHandler.postDelayed(r, mToast.LENGTH_SHORT);
            mToast.show();
        }
    }

    public static void showToast(Context mContext, int resId, int duration) {
        showToast(mContext, mContext.getResources().getString(resId), duration);
    }

}
