package com.infrastructure.widgets.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;


/**
 * 类名: ProgressDialogFragment.java
 * <p>
 * 功能:弹出的进度框
 *
 * @author Mr.Z
 * @version 1.0
 * @dateTime 2015-2-9 上午10:56:17
 */
@SuppressLint("NewApi")
public class ProgressDialogFragment extends DialogFragment {

    static View mContentView;
    int mIndeterminateDrawable;
    String mMessage;

    /**
     * Create a new instance of AbProgressDialogFragment.
     */
    public static ProgressDialogFragment newInstance(int indeterminateDrawable, String message) {
        ProgressDialogFragment f = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putInt("indeterminateDrawable", indeterminateDrawable);
        args.putString("message", message);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndeterminateDrawable = getArguments().getInt("indeterminateDrawable");
        mMessage = getArguments().getString("message");
        ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        // ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.progressbar_style_blue);
        if (mIndeterminateDrawable > 0) {
            mProgressDialog.setIndeterminateDrawable(getActivity().getResources().getDrawable(mIndeterminateDrawable));
        }

        if (mMessage != null) {
            mProgressDialog.setMessage(mMessage);
        }
        return mProgressDialog;
    }

}
