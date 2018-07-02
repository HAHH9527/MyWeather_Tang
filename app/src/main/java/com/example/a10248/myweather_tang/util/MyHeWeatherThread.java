package com.example.a10248.myweather_tang.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.a10248.myweather_tang.bean.weather.MyNow;

import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.basic.Basic;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;


/**
 * Created by 10248 on 2017/12/18.
 */

public class MyHeWeatherThread implements Runnable {

    private String city;
    private final String heWeatherId = "HE1712180908151990";
    private final String heWeatherKey = "1c702b48348b4d728dd762dc68fd1877";

    private Handler handler;

    private Context context;

    public MyHeWeatherThread() {
    }

    public MyHeWeatherThread(String city, Handler handler, Context context) {
        this.handler = handler;
        this.context = context;
        Log.i("in city", city);
        this.city = city;
//        try {
//            this.city = URLEncoder.encode(city, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        Log.i("UTF-8 city", this.city);
    }

//    public WeatherBean getWeatherBean(String weatherJson) {
//        WeatherBean weatherBean = new Gson().fromJson(weatherJson, WeatherBean.class);
//        return weatherBean;
//    }

    @Override
    public void run() {
        HeConfig.init(heWeatherId, heWeatherKey);
        HeConfig.switchToFreeServerNode();
        HeWeather.getWeatherNow(context, city, new HeWeather.OnResultWeatherNowBeanListener() {
            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                Message msg = new Message();
                msg.what = MyMessageType.Return_Weather_Message_Fail;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(List<Now> list) {
                Now now = list.get(0);

                MyNow myNow = new MyNow();
                myNow.setUptime(now.getUpdate().getUtc());
                myNow.setLocation(now.getBasic().getLocation());
                myNow.setTemperature(now.getNow().getTmp());
                myNow.setTemperature_feel(now.getNow().getFl());
                myNow.setWeather(now.getNow().getCond_txt());
                myNow.setWind_dir(now.getNow().getWind_dir());
                myNow.setWind_sc(now.getNow().getWind_sc());
                myNow.setPcpn(now.getNow().getPcpn());
                myNow.save();

                Message msg = new Message();
                msg.what = MyMessageType.Return_Weather_Message;
                msg.obj = myNow;
                handler.sendMessage(msg);
            }
        });
//        String param = "key=" + heWeatherKey + "&location=" + city;
//        if (city == null) {
//            Log.i("city is null", "null");
//            return;
//        } else if (city.equals("")) {
//            Log.i("city is null", city);
//            return;
//        }
//        StringBuilder sb = new StringBuilder();
//        InputStream is = null;
//        BufferedReader br = null;
//        PrintWriter out = null;
//        try {
//            //接口地址
//            String url = "https://free-api.heweather.com/s6/weather";
//            URL uri = new URL(url);
//            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setReadTimeout(5000);
//            connection.setConnectTimeout(10000);
//            connection.setRequestProperty("accept", "*/*");
//            //发送参数
//            connection.setDoOutput(true);
//            out = new PrintWriter(connection.getOutputStream());
//            out.print(param);
//            out.flush();
//            //接收结果
//            is = connection.getInputStream();
//            br = new BufferedReader(new InputStreamReader(is));
//            String line;
//            //缓冲逐行读取
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//            //测试结果
//            Log.i("weather", sb.toString());
//            Log.i("get weather down", "down");
//            System.out.println(sb.toString());
//            Message msg = new Message();
//            msg.what = MyMessageType.Return_Weather_Message;
//            msg.obj = getWeatherBean(sb.toString());
//            handler.sendMessage(msg);
//        } catch (Exception ignored) {
//            Log.i("get weather err", "err");
//            Log.i("err is", "");
//            ignored.printStackTrace();
//        } finally {
//            //关闭流
//            try {
//                if (is != null) {
//                    is.close();
//                }
//                if (br != null) {
//                    br.close();
//                }
//                if (out != null) {
//                    out.close();
//                }
//            } catch (IOException e2) {
//            }
//        }
    }
}
