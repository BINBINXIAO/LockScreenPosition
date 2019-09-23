package com.hdsx.background.locationservice;

import android.content.Context;

public interface IWifiAutoCloseDelegate {

    boolean isUseful(Context context);

    void initOnServiceStarted(Context context);

    /**
     * 定位成功时，如果移动网络无法访问，而且屏幕是点亮状态，则对状态进行保存
     */
    void onLocateSuccess(Context context, boolean isScreenOn, boolean isMobileable);

    /**
     * 对定位失败情况的处理
     */
    void onLocateFail(Context context, int errorCode, boolean isScreenOn, boolean isWifiable);

}
