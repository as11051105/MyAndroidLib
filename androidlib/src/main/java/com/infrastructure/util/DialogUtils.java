package com.infrastructure.util;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.infrastructure.widgets.dialog.MyDialogFragment;
import com.infrastructure.widgets.dialog.ProgressDialogFragment;


/**
 * 类名: DialogUtil.java
 * <p>
 * 功能:
 *
 * @author Mr.Z
 * @version 1.0
 * @dateTime 2015-8-25 上午9:56:01
 */
@SuppressLint("NewApi")
public class DialogUtils {

    /**
     * dialog tag
     */
    private static String mDialogTag = "dialog";

    /**
     * 功能:显示一个自定义的对话框(有背景层).
     *
     * @param gravity 位置
     * @author Mr.Z
     * @dateTime 2015-4-23 下午3:27:39
     */

    public static void showDialog(View view, int gravity) {
        FragmentActivity activity = (FragmentActivity) view.getContext();
        // Create and show the dialog.
        MyDialogFragment newFragment = MyDialogFragment.newInstance(android.app.DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog, gravity, 1.00);
        newFragment.setContentView(view);
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        // 指定一个系统转场动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.add(newFragment, mDialogTag);
        ft.commitAllowingStateLoss();
        // newFragment.show(ft, mDialogTag);
        // return newFragment;
    }

    /**
     * 功能:显示一个自定义的对话框(有背景层).
     *
     * @param gravity 位置
     * @author Mr.Z
     * @dateTime 2015-4-23 下午3:27:39
     */

    public static void showDialog(View view, int gravity, double widthSize) {
        FragmentActivity activity = (FragmentActivity) view.getContext();
        // Create and show the dialog.
        MyDialogFragment newFragment = MyDialogFragment.newInstance(android.app.DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog, gravity, widthSize);
        newFragment.setContentView(view);
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        // 指定一个系统转场动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.add(newFragment, mDialogTag);
        ft.commitAllowingStateLoss();
        // newFragment.show(ft, mDialogTag);
        // return newFragment;
    }


    /**
     * 描述：显示进度框.
     *
     * @param context               the context
     * @param indeterminateDrawable 用默认请写0
     * @param message               the message
     */
    public static void showProgressDialog(Context context, int indeterminateDrawable, String message) {
        FragmentActivity activity = (FragmentActivity) context;
        ProgressDialogFragment newFragment = ProgressDialogFragment.newInstance(indeterminateDrawable, message);
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        // 指定一个系统转场动画
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.add(newFragment, mDialogTag);
        ft.commitAllowingStateLoss();
        // newFragment.show(ft, mDialogTag);
        // return newFragment;
    }

    /**
     * 描述：移除Fragment.
     *
     * @param context the context
     */
    @SuppressLint("NewApi")
    public static void removeDialog(Context context) {
        try {
            FragmentActivity activity = (FragmentActivity) context;
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            // 指定一个系统转场动画
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            Fragment prev = activity.getFragmentManager().findFragmentByTag(mDialogTag);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            // 可能有Activity已经被销毁的异常
            e.printStackTrace();
        }
    }
}
