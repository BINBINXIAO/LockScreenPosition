package com.hdsx.hdyyglyh;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.hdsx.background.locationservice.LocationChangBroadcastReceiver;
import com.hdsx.hdyyglyh.greendb.base.MySQLiteOpenHelper;
import com.hdsx.hdyyglyh.greendb.gen.DaoMaster;
import com.hdsx.hdyyglyh.greendb.gen.DaoSession;

public class App extends Application {

    public static App baseApplication;
    private static LocationChangBroadcastReceiver locationChangBroadcastReceiver;

    /*
     * 数据库
     */
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private static DaoSession daoSession;
    private DaoMaster daoMaster;
    private String db_name = "yhdb.db";
    private SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        locationChangBroadcastReceiver = new LocationChangBroadcastReceiver();

        loginDB();
    }

    private void loginDB() {
        mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext(),db_name,null);
        db = mySQLiteOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext() {

        return baseApplication.getBaseContext();
    }

    public static LocationChangBroadcastReceiver getlocationChangeBoardcase() {
        if (null == locationChangBroadcastReceiver) {
            locationChangBroadcastReceiver = new LocationChangBroadcastReceiver();
        }

        return locationChangBroadcastReceiver;
    }

    public static DaoSession getDao() {
        return daoSession;
    }
}
