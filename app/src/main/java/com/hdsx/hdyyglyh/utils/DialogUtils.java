package com.hdsx.hdyyglyh.utils;

import android.app.Activity;
import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hdsx.hdyyglyh.R;

public class DialogUtils {

    public static MaterialDialog createProgress(Activity activity) {
        return new MaterialDialog.Builder(activity)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .build();
    }

    public static MaterialDialog createProgress(Activity activity, int msg) {
        return new MaterialDialog.Builder(activity)
                .content(msg)
                .cancelable(false)
                .progress(true, 0)
                .build();
    }

    public static void showTip(Context context, int msg) {
        new MaterialDialog.Builder(context)
                .content(msg)
                .build()
                .show();
    }

    public static MaterialDialog buildTip(Context context) {
        return new MaterialDialog.Builder(context)
                .build();
    }

    public static void showTip(Context context, String msg) {
        new MaterialDialog.Builder(context)
                .content(msg)
                .build()
                .show();
    }

    public static void show(MaterialDialog dialog) {
        if (dialog != null && !dialog.isShowing())
            dialog.show();
    }

    public static void dismiss(MaterialDialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public static boolean isShowing(MaterialDialog dialog) {
        return dialog != null && dialog.isShowing();
    }

}
