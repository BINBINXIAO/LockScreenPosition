package com.hdsx.hdyyglyh.utils;

import android.os.Handler;
import android.widget.Toast;

import com.hdsx.hdyyglyh.App;


/**
 * Created by Administrator on 2018/6/29.
 */

public class ToastUtil {
    private static Toast toast;
    private static Handler mHandler = new Handler();

    public static void showToast(String info) {
        mHandler.removeCallbacks(r);
        if (null != toast) {
            toast.setText(info);
        } else {
            toast = Toast.makeText(App.getContext(), info, Toast.LENGTH_SHORT);
        }
        mHandler.postDelayed(r, 5000);
        toast.show();
    }

    private static Runnable r = new Runnable() {
        @Override
        public void run() {
            toast.cancel();
        }
    };
}
