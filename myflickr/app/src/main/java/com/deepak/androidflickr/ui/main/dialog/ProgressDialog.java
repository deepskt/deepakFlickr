package com.deepak.androidflickr.ui.main.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.deepak.androidflickr.Config;
import com.deepak.androidflickr.util.Tracer;

/*
* @author deepsindeep
* */
public class ProgressDialog {
    private final String TAG = Config.logger + ProgressDialog.class.getSimpleName();


    private MaterialDialog mDialog;
    private Context mContext;

    public ProgressDialog(Context context) {
        mContext = context;
        mDialog = new MaterialDialog.Builder(context).widgetColor(Color.RED).theme(Theme.DARK).
                dividerColor(Color.BLACK)
                .contentColor(Color.BLACK).cancelable(false)
                .progress(true, 0).build();
    }

    public void show(String message) {
        Tracer.debug(TAG," show "+" ");
        mDialog.setTitle("Title");
        mDialog.setMessage(message);
        mDialog.getProgressBar().setVisibility(View.VISIBLE);
        show();
    }

    public void changeToError(String message) {
        mDialog.getProgressBar().setVisibility(View.GONE);
        mDialog.setContent(message);
        handler.postDelayed(runnable, 3000);
    }

    public void changeMessage(String message) {
        mDialog.setContent(message);
    }


    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            dismiss();
        }
    };

    public void dismiss() {
        if (!((Activity) mContext).isFinishing() && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void show() {
        Tracer.debug(TAG," show "+" ");
        if (!((Activity) mContext).isFinishing() && !mDialog.isShowing()) {
            Tracer.debug(TAG," show  called"+" ");

            mDialog.show();
        }
    }

}
