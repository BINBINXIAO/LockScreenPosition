package com.hdsx.background.locationservice;

import android.content.Intent;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;


public class LocationService extends NotificationService {
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private IWifiAutoCloseDelegate mWifiAutoCloseDelegate = new WifiAutoCloseDelegate();
    private boolean mIsWifiCloseable = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        applyNotiKeepMech(); //开启利用notification提高进程优先级的机制
        if (mWifiAutoCloseDelegate.isUseful(getApplicationContext())) {
            mIsWifiCloseable = true;
            mWifiAutoCloseDelegate.initOnServiceStarted(getApplicationContext());
        }

        startLocation();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        unApplyNotiKeepMech();
        stopLocation();
        super.onDestroy();
    }

    void startLocation() {
        stopLocation();

        if (null == mLocationClient) {
            mLocationClient = new AMapLocationClient(this.getApplicationContext());
        }

        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
        // 使用连续
        mLocationOption.setOnceLocation(false);
        mLocationOption.setLocationCacheEnable(false);
        // 每5秒定位一次
        mLocationOption.setInterval(2 * 1000);
        // 地址信息
        mLocationOption.setNeedAddress(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(locationListener);
        mLocationClient.startLocation();
    }

    void stopLocation() {
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
        }
    }

    AMapLocationListener locationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //发送结果的通知
            sendLocationBroadcast(aMapLocation);

            if (!mIsWifiCloseable) {
                return;
            }

            if (aMapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
                mWifiAutoCloseDelegate.onLocateSuccess(getApplicationContext(), PowerManagerUtil.getInstance().isScreenOn(getApplicationContext()), NetUtil.getInstance().isMobileAva(getApplicationContext()));
            } else {
                mWifiAutoCloseDelegate.onLocateFail(getApplicationContext() , aMapLocation.getErrorCode() , PowerManagerUtil.getInstance().isScreenOn(getApplicationContext()), NetUtil.getInstance().isWifiCon(getApplicationContext()));
            }
        }

        private void sendLocationBroadcast(AMapLocation aMapLocation) {
            if (null != aMapLocation) {
                Intent mIntent = new Intent(LocationChangBroadcastReceiver.RECEIVER_ACTION);
                mIntent.putExtra(LocationChangBroadcastReceiver.RECEIVER_DATA, aMapLocation);
                sendBroadcast(mIntent);
//                String string =  Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss")  + ", "+aMapLocation.getLatitude() + ", " + aMapLocation.getLongitude();
//                Utils.saveFile(string, "backlocation.txt", true);
            }
        }
    };
}
