package com.hdsx.hdyyglyh.page.yhxc;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.hdsx.background.locationservice.LocationChangBroadcastReceiver;
import com.hdsx.background.locationservice.LocationService;
import com.hdsx.background.locationservice.LocationStatusManager;
import com.hdsx.hdyyglyh.App;
import com.hdsx.hdyyglyh.R;
import com.hdsx.hdyyglyh.greendb.bean.PathBean;
import com.hdsx.hdyyglyh.utils.DialogUtils;
import com.hdsx.hdyyglyh.utils.ToastUtil;
import com.hdsx.hdyyglyh.utils.Utils;
import com.hdsx.hdyyglyh.view.EndSjDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.amap.api.location.AMapLocation.GPS_ACCURACY_GOOD;
import static com.amap.api.location.AMapLocation.LOCATION_SUCCESS;
import static com.amap.api.maps.CoordinateConverter.CoordType.GPS;
import static com.amap.api.maps.model.BitmapDescriptorFactory.fromResource;
import static com.autonavi.amap.mapcore.Inner_3dMap_location.GPS_ACCURACY_BAD;

public class XcMapActivity extends AppCompatActivity implements AMap.OnMapLoadedListener,
        AMapLocationListener {

    @BindView(R.id.iv_lk)
    ImageView ivLk;
    @BindView(R.id.open_lk)
    CardView openLk;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.btn_sbsj)
    Button btnSbsj;
    @BindView(R.id.btn_js)
    Button btnJs;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.parent)
    RelativeLayout parent;
    @BindView(R.id.view_bxgj)
    LinearLayout viewBxgj;
    @BindView(R.id.ll_start)
    LinearLayout llStart;
    @BindView(R.id.ll_sb)
    LinearLayout llSb;
    @BindView(R.id.ll_ys)
    LinearLayout llYs;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_gps)
    TextView tvGps;

    private AMapLocationClient mlocationClient;
    public static final int SPAN = 1000 * 5;
    private TextView title;
    private Toolbar toolbar;
    private MapView mapView;
    private AMap aMap;
    private LatLng locationLatLng;//定位成功的经纬度
    private BitmapDescriptor locationIcon;//定位显示的图标
    private EndSjDialog endSjDialog;
    private boolean isOpenlk = false;//开启路况图层、
    private boolean locationSuccess = false;//定位是否成功
    private MaterialDialog mTipDialog;//错误提示dialog
    private static Handler handler = new Handler();//用来发送延迟消息
    private boolean showLocationTip = false;//定时还未定位成功显示框判断
    public static boolean isForeground = false;
    private boolean firstLocation = true;
    private Marker locationMarker;
    private CoordinateConverter coordinateConverter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xc_map);
        ButterKnife.bind(this);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        initMap();
        initLocation();

        broadInit();
    }

    private void broadInit() {

        LocationChangBroadcastReceiver loc = App.getlocationChangeBoardcase();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationChangBroadcastReceiver.RECEIVER_ACTION);
        registerReceiver(loc, intentFilter);

        registerMessageReceiver();

    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.getUiSettings().setZoomControlsEnabled(false);
            aMap.setOnMapLoadedListener(this);
        }
        locationIcon = fromResource(R.drawable.map_marker);
        coordinateConverter = new CoordinateConverter(getApplicationContext()).from(GPS);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapLoaded() {

    }

    private void initLocation() {
        mlocationClient = new AMapLocationClient(getApplicationContext());
        mlocationClient.setLocationListener(this);
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location != null && location.getErrorCode() == LOCATION_SUCCESS) {
            locationSuccess = true;

            locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());

            Log.e("定位結果", "onLocationChanged: " + locationLatLng);
            if (firstLocation) {
                firstLocation = false;

                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 11));
                locationMarker = addMarker(locationLatLng, locationIcon, 9);

            } else {
                if (locationMarker != null) {
                    locationMarker.setPosition(locationLatLng);
                } else {
                    locationMarker = addMarker(locationLatLng, locationIcon, 9);
                }
            }
        } else {
            locationSuccess = false;
        }
    }

    @OnClick({R.id.btn_sbsj, R.id.btn_js, R.id.iv_title, R.id.open_lk, R.id.iv_location, R.id.ll_start, R.id.iv_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_start://开始继续
                if (Utils.getInternet()) {//判断当然是否有网络
                    //启动巡查后切换到巡查上报界面，隐藏的一些属性显示
                    title.setText("巡查上报");
                    llYs.setVisibility(View.VISIBLE);
                    ivSetting.setVisibility(View.VISIBLE);
                    llSb.setVisibility(View.VISIBLE);
                    llStart.setVisibility(View.GONE);
                    restoreTime = 0;
                    startTimer();
                    App.getDao().getTrackBeanDao().deleteAll();

                    Intent intent = new Intent(XcMapActivity.this, LocationService.class);
                    startService(intent);

                } else {
                    ToastUtil.showToast("定位环境不佳，请检查网络或到空旷户外重新定位");
                }

                LocationStatusManager.getInstance().resetToInit(getApplicationContext());
                break;
            case R.id.iv_setting:

                break;
            case R.id.btn_sbsj://上报事件
                Intent intent = new Intent(this, SbsjActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_js://结束巡查
                endSjDialog = new EndSjDialog(this);
                endSjDialog.setSaveTrackDataListener(new EndSjDialog.SaveTrackDataListener() {
                    @Override
                    public void onSaveTrackData() {
                        endSjDialog.dismiss();
                        ToastUtil.showToast("记录巡查用时，巡查结束");
                        closeTimer();

                        title.setText("启动巡查");
                        llYs.setVisibility(View.GONE);
                        ivSetting.setVisibility(View.GONE);
                        llSb.setVisibility(View.GONE);
                        llStart.setVisibility(View.VISIBLE);

                        stopLocationService();


                        //跳转到线路保存界面
                        Intent intent1 = new Intent(XcMapActivity.this,LxbcActivity.class);
                        startActivityForResult(intent1,111);
                    }
                });
                endSjDialog.show();

                LocationStatusManager.getInstance().resetToInit(getApplicationContext());
                break;
            case R.id.iv_title://设置
                break;
            case R.id.open_lk://路况
                if (isOpenlk) {
                    ivLk.setBackground(getResources().getDrawable(R.drawable.lcc_lk));
                } else {
                    ivLk.setBackground(getResources().getDrawable(R.drawable.lk));
                }
                aMap.setTrafficEnabled(!isOpenlk);
                isOpenlk = !isOpenlk;
                break;
            case R.id.iv_location:
                Log.e("dianjidianji", locationSuccess + "");
                if(null!=locationLatLng){
                        aMap.animateCamera(CameraUpdateFactory.newLatLng(locationLatLng));
                } else {
                    showLocationFail();
                }
                break;

        }
    }


    //显示定位失败的提示
    private void showLocationFail() {

        //如果Activity不显示在前台，则不显示Dialog
        if (!isForeground) {
            return;
        }

        boolean connected = Utils.getInternet();
        boolean gpsEnabled = isGpsEnabled();

        showLocationTip = true;
        int msg;
        if (!connected && !gpsEnabled) {
            msg = R.string.tip_no_network_gps;
        } else if (connected && !gpsEnabled) {
            msg = R.string.tip_no_gps;
        } else if (!connected && gpsEnabled) {
            msg = R.string.tip_no_network;
        } else {
            msg = R.string.tip_network_gps;
        }

        mTipDialog = DialogUtils.buildTip(this);
        mTipDialog.setContent(msg);
        mTipDialog.show();
    }

    private boolean isGpsEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /***
     *  时间记录
     */
    private Timer timer;
    private TimerTask timerTask;
    private long baseTimer;

    final Handler startTimehandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (null != tvTime) {
                tvTime.setText((String) msg.obj);
            }
        }
    };

    private long restoreTime;

    private void startTimer() {
        baseTimer = SystemClock.elapsedRealtime() - restoreTime;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                int time = (int) ((SystemClock.elapsedRealtime() - baseTimer) / 1000);
                String hh = new DecimalFormat("00").format(time / 3600);
                String mm = new DecimalFormat("00").format(time % 3600 / 60);
                String ss = new DecimalFormat("00").format(time % 60);
                String timeFormat = new String(hh + ":" + mm + ":" + ss);
                Message msg = new Message();
                msg.obj = timeFormat;
                startTimehandler.sendMessage(msg);
            }
        };

        if (timer != null && timerTask != null) {
            timer.scheduleAtFixedRate(timerTask, 0, 1000L);
        }
    }


    /**
     * 返回到amap ac
     */
    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.hdsx.water.ui.MESSAGE_RECEIVED_ACTION";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                AMapLocation aMapLocation = intent.getParcelableExtra("data");
                // 设置到界面上去
                if (aMapLocation != null) {
                    // 速度
                    float speed = aMapLocation.getSpeed();
                    // gps信号
                    int gpsAccuracyStatus = aMapLocation.getGpsAccuracyStatus();
                    if (gpsAccuracyStatus == GPS_ACCURACY_BAD) {
                        tvGps.setText("gps弱");
                    } else if (gpsAccuracyStatus == GPS_ACCURACY_GOOD) {
                        tvGps.setText("gps强");
                    } else {
                        tvGps.setText("gps未知");
                    }

                    if (null != aMapLocation.getAddress() && !"".equals(aMapLocation.getAddress()))
                        ToastUtil.showToast(aMapLocation.getAddress() + "");


                    ToastUtil.showToast("开启定位");
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode==111){
                List<PathBean> list = App.getDao().getPathBeanDao().queryBuilder().list();
                List<LatLng> listLat = new ArrayList<>();
                if(null!=list){
                    PathBean pathBean = list.get(0);
                    String polygon = pathBean.getPolygon();
                    String[] latstirng = polygon.split(",");
                    for (int i = 0; i < latstirng.length; i++) {
                        String lat = latstirng[i];
                        String[] split = lat.split(" ");
                        LatLng latLng1 = wgsTogcj02(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
                        listLat.add(latLng1);
                    }
                }
                addPolyline(listLat,0xff0000CD,10,11);
            }
        }

    }

    private LatLng wgsTogcj02(double x, double y) {
        return coordinateConverter.coord(new LatLng(y, x)).convert();
    }

    //在地图上画线
    private Polyline addPolyline(List<LatLng> latLngs, int color, int width, int zIndex) {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(color)
                .width(width)
                .zIndex(zIndex)
                .setPoints(latLngs);
        return aMap.addPolyline(polylineOptions);
    }


    /**
     * 关闭服务
     * 先关闭守护进程，再关闭定位服务
     */
    private void stopLocationService() {
        sendBroadcast(com.hdsx.background.locationservice.Utils.getCloseBrodecastIntent());
    }

    private Marker addMarker(LatLng latLng, BitmapDescriptor icon, int zIndex) {
        return addMarker(latLng, icon, "", zIndex);
    }

    //添加Marker
    private Marker addMarker(LatLng latLng, BitmapDescriptor icon, String title, int zIndex) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .anchor(0.5f, 0.5f)
                .icon(icon)
                .zIndex(zIndex);

        if (!TextUtils.isEmpty(title)) {
            markerOptions.title(title);
            Marker marker = aMap.addMarker(markerOptions);
            marker.showInfoWindow();
            return marker;
        } else {
            return aMap.addMarker(markerOptions);
        }
    }

    private void closeTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }

        LocationChangBroadcastReceiver loc = App.getlocationChangeBoardcase();
        if (loc != null) {
            unregisterReceiver(loc);
        }

        if (mMessageReceiver != null) {
            unregisterReceiver(mMessageReceiver);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
        isForeground = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
        isForeground = false;
    }
}
