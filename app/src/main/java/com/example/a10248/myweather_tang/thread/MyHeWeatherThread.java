package com.example.a10248.myweather_tang.thread;

import android.content.Context;
import android.net.wifi.aware.PublishConfig;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.a10248.myweather_tang.R;
import com.example.a10248.myweather_tang.bean.weather.MyAir;
import com.example.a10248.myweather_tang.bean.weather.MyForecast;
import com.example.a10248.myweather_tang.bean.weather.MyNow;
import com.example.a10248.myweather_tang.util.MyMessageType;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNowCity;
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.Forecast;
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.ForecastBase;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

/**
 * Created by 10248 on 2017/12/18.
 */

public class MyHeWeatherThread implements Runnable {

    private String city;

    private Handler handler;

    private Context context;

    public MyHeWeatherThread() {
    }

    public MyHeWeatherThread(String city, Handler handler, Context context) {
        this.handler = handler;
        this.context = context;
        Log.i("in city", city);
        this.city = city;
    }

    @Override
    public void run() {
        HeConfig.init(context.getString(R.string.heWeatherId), context.getString(R.string.heWeatherKey));
        HeConfig.switchToFreeServerNode();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh");
        String date = sDateFormat.format(new java.util.Date()) + "%";

//        final MyNow[] tempNow = new MyNow[1];

        //获取实时天气
        //从数据库中查询
        List<MyNow> sqlNow = LitePal.where("uptime like ? AND serchloc = ?", date, city).limit(1).find(MyNow.class);
        if (sqlNow.size() >= 1) {//如果查询出数据
            Message msg = new Message();
            msg.what = MyMessageType.Return_WeatherNow_Message;
            msg.obj = sqlNow.get(0);
            handler.sendMessage(msg);
//            tempNow[0] = sqlNow.get(0);
        } else {//在线查询
            HeWeather.getWeatherNow(context, city, new HeWeather.OnResultWeatherNowBeanListener() {
                @Override
                public void onError(Throwable throwable) {
                    Message msg = new Message();
                    msg.what = MyMessageType.Return_WeatherNow_Message_Fail;
                    handler.sendMessage(msg);
                }

                @Override
                public void onSuccess(List<Now> list) {
                    Now now = list.get(0);

                    MyNow myNow = new MyNow();
                    myNow.setUptime(now.getUpdate().getLoc());
                    myNow.setLocation(now.getBasic().getLocation());
                    myNow.setSerchLoc(city);
                    myNow.setTemperature(now.getNow().getTmp());
                    myNow.setTemperature_feel(now.getNow().getFl());
                    myNow.setWeather(now.getNow().getCond_txt());
                    myNow.setWind_dir(now.getNow().getWind_dir());
                    myNow.setWind_sc(now.getNow().getWind_sc());
                    myNow.setPcpn(now.getNow().getPcpn());
                    myNow.save();

                    Message msg = new Message();
                    msg.what = MyMessageType.Return_WeatherNow_Message;
                    msg.obj = myNow;
                    handler.sendMessage(msg);

//                    tempNow[0] = myNow;
                }
            });
        }

        //获取未来天气
        //从数据库中查询
        List<MyForecast> sqlForecast = LitePal.where("uptime like ? AND serchloc = ?", date, city).limit(1).find(MyForecast.class);
        if (sqlForecast.size() >= 1) {//如果查询出数据
            Message msg = new Message();
            msg.what = MyMessageType.Return_WeatherForecast_Message;
            msg.obj = sqlForecast.get(0);
            handler.sendMessage(msg);
        } else {//在线查询
            HeWeather.getWeatherForecast(context, city, new HeWeather.OnResultWeatherForecastBeanListener() {
                @Override
                public void onError(Throwable throwable) {
                    Message msg = new Message();
                    msg.what = MyMessageType.Return_WeatherForecast_Message_Fail;
                    handler.sendMessage(msg);
                }

                @Override
                public void onSuccess(List<Forecast> list) {
                    Forecast forecast = list.get(0);
                    MyForecast myForecast = new MyForecast();
                    List<ForecastBase> forecastList = forecast.getDaily_forecast();
                    myForecast.setUptime(forecast.getUpdate().getLoc());
                    myForecast.setLocation(forecast.getBasic().getLocation());
                    myForecast.setSerchLoc(city);

                    myForecast.setDate1(forecastList.get(0).getDate());
                    myForecast.setCond1(forecastList.get(0).getCond_txt_d() + "/" + forecastList.get(0).getCond_txt_n());
                    myForecast.setTmp1(forecastList.get(0).getTmp_max() + "/" + forecastList.get(0).getTmp_min() + "°C");

                    myForecast.setDate2(forecastList.get(1).getDate());
                    myForecast.setCond2(forecastList.get(1).getCond_txt_d() + "/" + forecastList.get(1).getCond_txt_n());
                    myForecast.setTmp2(forecastList.get(1).getTmp_max() + "/" + forecastList.get(1).getTmp_min() + "°C");

                    myForecast.setDate3(forecastList.get(2).getDate());
                    myForecast.setCond3(forecastList.get(2).getCond_txt_d() + "/" + forecastList.get(2).getCond_txt_n());
                    myForecast.setTmp3(forecastList.get(2).getTmp_max() + "/" + forecastList.get(2).getTmp_min() + "°C");

                    myForecast.save();

                    Message msg = new Message();
                    msg.what = MyMessageType.Return_WeatherForecast_Message;
                    msg.obj = myForecast;
                    handler.sendMessage(msg);
                }
            });
        }

        //获取空气质量
        //从数据库中查询
        List<MyAir> sqlAir = LitePal.where("uptime like ? AND serchloc = ?", date, city).limit(1).find(MyAir.class);
        if (sqlAir.size() >= 1) {//如果查询出数据
            Message msg = new Message();
            msg.what = MyMessageType.Return_WeatherForecast_Message;
            msg.obj = sqlForecast.get(0);
            handler.sendMessage(msg);
        } else {//在线查询
            HeWeather.getAirNow(context, city, new HeWeather.OnResultAirNowBeansListener() {
                @Override
                public void onError(Throwable throwable) {

//                    MyAir myAir = new MyAir();
//                    myAir.setUptime(tempNow[0].getUptime());
//                    myAir.setSerchLoc(city);
//                    myAir.setPub_time(tempNow[0].getUptime());
//                    myAir.setLocation(tempNow[0].getLocation());
//                    myAir.setQlty("良");
//                    myAir.setMain("PM2.5");
//                    myAir.setAqi("74");
//                    myAir.setPm25("66");
//                    myAir.setPm10("78");
//                    myAir.setNo2("40");
//                    myAir.setSo2("30");
//                    myAir.setCo("33");
//                    myAir.setO3("20");
//
//                    myAir.save();

                    System.out.println(throwable.toString());
                    Message msg = new Message();
                    msg.what = MyMessageType.Return_Air_Message_Fail;
                    handler.sendMessage(msg);

//                    Message msg = new Message();
//                    msg.what = MyMessageType.Return_Air_Message_Fail;
//                    msg.obj = myAir;
//                    handler.sendMessage(msg);
                }

                @Override
                public void onSuccess(List<AirNow> list) {
                    AirNow airNow = list.get(0);
                    AirNowCity airNowCity = airNow.getAir_now_city();
                    MyAir myAir = new MyAir();
                    myAir.setUptime(airNow.getUpdate().getLoc());
                    myAir.setSerchLoc(city);
                    myAir.setPub_time(airNow.getAir_now_city().getPub_time());
                    myAir.setLocation(airNow.getBasic().getLocation());
                    myAir.setQlty(airNowCity.getQlty());
                    myAir.setMain(airNowCity.getMain());
                    myAir.setAqi(airNowCity.getAqi());
                    myAir.setPm25(airNowCity.getPm25());
                    myAir.setPm10(airNowCity.getPm10());
                    myAir.setNo2(airNowCity.getNo2());
                    myAir.setSo2(airNowCity.getSo2());
                    myAir.setCo(airNowCity.getCo());
                    myAir.setO3(airNowCity.getO3());

                    myAir.save();

                    Message msg = new Message();
                    msg.what = MyMessageType.Return_Air_Message;
                    msg.obj = myAir;
                    handler.sendMessage(msg);
                }
            });
        }
    }
}
