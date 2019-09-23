package com.hdsx.background.locationservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;


public class NotificationService extends Service {

    /**
     * startForeground的 noti_id
     */
    private static int NOTI_ID = 123321;

    private Utils.CloseServiceReceiver mCloseReceiver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mCloseReceiver = new Utils.CloseServiceReceiver(this);
        registerReceiver(mCloseReceiver, Utils.getCloseServiceFilter());
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        if (mCloseReceiver != null) {
            unregisterReceiver(mCloseReceiver);
            mCloseReceiver = null;
        }

        super.onDestroy();
    }


    private final String mHelperServiceName = "com.hdsx.background.locationservice.LocationHelperService";

    /**
     * 触发利用notification增加进程优先级
     */
    protected void applyNotiKeepMech() {
        startForeground(NOTI_ID, Utils.buildNotification(getBaseContext()));
        startBindHelperService();
    }

    public void unApplyNotiKeepMech() {
        stopForeground(true);
    }

    public Binder mBinder;

    public class LocationServiceBinder extends ILocationServiceAIDL.Stub {

        @Override
        public void onFinishBind(){
        }
    }

    private ILocationHelperServiceAIDL mHelperAIDL;

    private void startBindHelperService() {
        connection = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                //doing nothing
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ILocationHelperServiceAIDL l = ILocationHelperServiceAIDL.Stub.asInterface(service);
                mHelperAIDL = l;
                try {
                    l.onFinishBind(NOTI_ID);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
        Intent intent = new Intent();
        intent.setAction(mHelperServiceName);
        intent.setPackage("com.hdsx.hdyyglyh");
        Intent explicitIntent = Utils.getExplicitIntent(getApplicationContext(), intent);
        bindService(explicitIntent, connection, Service.BIND_AUTO_CREATE);
    }

    private ServiceConnection connection;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (mBinder == null) {
            mBinder = new LocationServiceBinder();
        }
        return mBinder;
    }

}
