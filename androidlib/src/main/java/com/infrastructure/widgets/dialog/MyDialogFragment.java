package com.infrastructure.widgets.dialog;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 类名: DialogFragment.java
 * <p>
 * 功能:弹出框
 *
 * @author Mr.Z
 * @version 1.0
 * @dateTime 2015-2-9 上午10:56:42
 */
@SuppressLint("NewApi")
public class MyDialogFragment extends DialogFragment {

    /**
     * The m theme.
     */
    private int mTheme;

    /**
     * The m style.
     */
    private int mStyle;

    /**
     * The m content view.
     */
    private View mContentView;


    /**
     * The m gravity.
     */
    private int mGravity;
    /**
     * DialogFragment 的宽度，不填或1.00 为充满
     */
    private double widthSize = 1.00;

    /**
     * New instance.
     *
     * @param style the style
     * @param theme the theme
     * @return the ab sample dialog fragment
     */
    public static MyDialogFragment newInstance(int style, int theme, double widthSize) {
        return newInstance(style, theme, Gravity.CENTER, widthSize);
    }

    /**
     * New instance.
     *
     * @param style   the style
     * @param theme   the theme
     * @param gravity the gravity
     * @return the ab sample dialog fragment
     */
    public static MyDialogFragment newInstance(int style, int theme, int gravity, double widthSize) {
        MyDialogFragment f = new MyDialogFragment();

        // Supply style input as an argument.
        Bundle args = new Bundle();
        args.putInt("style", style);
        args.putInt("theme", theme);
        args.putInt("gravity", gravity);
        args.putDouble("widthSize", widthSize);
        f.setArguments(args);
        return f;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.DialogFragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStyle = getArguments().getInt("style");
        mTheme = getArguments().getInt("theme");
        mGravity = getArguments().getInt("gravity");
        widthSize = getArguments().getDouble("widthSize");
        setStyle(mStyle, mTheme);
    }


    /*
     * (non-Javadoc)
     *
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mContentView;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.DialogFragment#onActivityCreated(android.os.Bundle)
     */
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        View view = getView(); //onCreateView创建出来的view
//        if (view != null) {
//            if (view.getParent() != null) {
//                throw new IllegalStateException("DialogFragment can not be attached to a container view");
//            }
//            getDialog().setContentView(view); //将view交给dialog显示
//        }
//        getDialog().setCanceledOnTouchOutside(true);
//        Window window = getDialog().getWindow();
//        WindowManager.LayoutParams attributes = window.getAttributes();
//        attributes.gravity = mGravity;
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        // window.setBackgroundDrawable(new
//        // ColorDrawable(Color.parseColor("#00000000")));
//        window.setBackgroundDrawable(null);
//    }

    /**
     * Gets the content view.
     *
     * @return the content view
     */
    public View getContentView() {
        return mContentView;
    }

    /**
     * Sets the content view.
     *
     * @param mContentView the new content view
     */
    public void setContentView(View mContentView) {
        this.mContentView = mContentView;
    }


    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimator(transit, enter, nextAnim);
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        // getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
        getDialog().getWindow().setLayout((int) (dm.widthPixels * widthSize), ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().setCanceledOnTouchOutside(true);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = mGravity;
        //   window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // window.setBackgroundDrawable(new
        // ColorDrawable(Color.parseColor("#00000000")));
        window.setBackgroundDrawable(null);

    }
}
