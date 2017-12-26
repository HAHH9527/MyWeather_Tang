package com.example.a10248.myweather_tang.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by 10248 on 2017/12/18.
 */

public class MyAMapThread implements Runnable {

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mlocationOption = null;
    //    private AMapLocation mMapLocation = null;
    private String city = null;
    private Activity mainActivity;

    //Handler对象
    private Handler handler;

    //权限列表
    private String[] permissions = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.ACCESS_WIFI_STATE", "android.permission.CHANGE_WIFI_STATE", "android.permission.INTERNET", "android.permission.READ_PHONE_STATE",
            "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS", "android.permission.BLUETOOTH", "android.permission.BLUETOOTH_ADMIN"};

    public String getCity() {
        return city;
    }

    public AMapLocationClient getmLocationClient() {
        return mLocationClient;
    }

    public MyAMapThread(Activity activity, Handler handler) {
        this.mainActivity = activity;
        this.handler = handler;

        //初始化定位
        initLocation();
        //启动定位,改为放于run方法里启动
//        mLocationClient.startLocation();
    }

    //声明定位回调监听器
    AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                Log.i("aMap city", aMapLocation.getCity());
                city = aMapLocation.getCity();
                //Log显示当前城市
                Log.i("aMap this city", city);

                //将城市传回主线程
                Message msg = new Message();
                msg.what = MyMessageType.Return_City_Message;
                msg.obj = city;
                handler.sendMessage(msg);
            } else {
                Log.i("aMap", "aMapLocation == null");
            }
        }
    };

    //初始化定位
    private void initLocation() {
        //申请权限
        ActivityCompat.requestPermissions(mainActivity, permissions, 1);
        //初始化client
        mLocationClient = new AMapLocationClient(mainActivity.getApplicationContext());
        //初始化定位参数
        mlocationOption = new AMapLocationClientOption();
        //设置为单次定位
        mlocationOption.setOnceLocation(true);
        //设置定位参数
        mLocationClient.setLocationOption(mlocationOption);
        //设置定位监听
        mLocationClient.setLocationListener(mLocationListener);
        Log.i("aMap", "initLocationDown");
    }

    @Override
    public void run() {
        mLocationClient.startLocation();
    }
}
