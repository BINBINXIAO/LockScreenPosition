package com.hdsx.background.locationservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.amap.api.location.AMapLocation;
import com.hdsx.hdyyglyh.App;
import com.hdsx.hdyyglyh.greendb.bean.TrackBean;
import com.hdsx.hdyyglyh.page.yhxc.XcMapActivity;
import com.hdsx.hdyyglyh.utils.CoordinateTransformUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LocationChangBroadcastReceiver extends BroadcastReceiver {

    public static final String RECEIVER_ACTION = "location_in_background";
    public static final String RECEIVER_DATA = "location_data";
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(RECEIVER_ACTION)) {
            AMapLocation location = (AMapLocation) intent.getParcelableExtra(RECEIVER_DATA);
            if (null != location) {
                String string =  Utils.formatUTC(System.currentTimeMillis(), //保存文件到本地
                        "yyyy-MM-dd HH:mm:ss")  +
                        ", "+location.getLatitude() +
                        ", " + location.getLongitude();


//                Utils.saveFile(string, "broadcastlocation.txt", true);
                Utils.saveFile2(context,string, "broadcastlocation.txt", true);

                processCustomMessageA(context, location);

                TrackBean trackBean = new TrackBean();
                double[] loc = CoordinateTransformUtil.gcj02towgs84(location.getLongitude(), location.getLatitude());
                trackBean.setLontitude(loc[0]);
                trackBean.setLatitude(loc[1]);
                App.getDao().getTrackBeanDao().insertOrReplace(trackBean);
            }
        }
    }

    //send msg to MainActivity
    private void processCustomMessageA(Context context, AMapLocation aMapLocation) {
        if (XcMapActivity.isForeground) {
            Intent msgIntent = new Intent(XcMapActivity.MESSAGE_RECEIVED_ACTION);
            if (aMapLocation != null) {
                msgIntent.putExtra("data", aMapLocation);
            }
            context.sendBroadcast(msgIntent);
        }
    }

}
