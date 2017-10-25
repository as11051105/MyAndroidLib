package com.infrastructure.widgets.handler;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/4/7.
 */

public class WeakRefHandler extends Handler {
    WeakReference<Context> mWeakContext;

    public WeakRefHandler(Context context) {
        mWeakContext = new WeakReference<Context>(context);
    }

    @Override
    public void handleMessage(Message msg) {
        if ((mWeakContext.get() instanceof Activity) && ((Activity) mWeakContext.get()).isFinishing())
            return;
        if (mWeakContext == null) {
            return;
        }
        super.handleMessage(msg);
    }
}
