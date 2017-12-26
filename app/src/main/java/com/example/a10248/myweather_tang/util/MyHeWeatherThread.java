package com.example.a10248.myweather_tang.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.a10248.myweather_tang.bean.WeatherBean;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by 10248 on 2017/12/18.
 */

public class MyHeWeatherThread implements Runnable {

    private String city;
    private final String heWeatherKey = "1c702b48348b4d728dd762dc68fd1877";
    private Handler handler;

    public MyHeWeatherThread() {
    }

    public MyHeWeatherThread(String city, Handler handler) {
        this.handler = handler;
        Log.i("in city", city);
        try {
            this.city = URLEncoder.encode(city, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("UTF-8 city", this.city);
    }

    public WeatherBean getWeatherBean(String weatherJson) {
        WeatherBean weatherBean = new Gson().fromJson(weatherJson, WeatherBean.class);
        return weatherBean;
    }

    @Override
    public void run() {
        String param = "key=" + heWeatherKey + "&location=" + city;
        if (city == null) {
            Log.i("city is null", "null");
            return;
        } else if (city.equals("")) {
            Log.i("city is null", city);
            return;
        }
        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        BufferedReader br = null;
        PrintWriter out = null;
        try {
            //接口地址
            String url = "https://free-api.heweather.com/s6/weather";
            URL uri = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.setRequestProperty("accept", "*/*");
            //发送参数
            connection.setDoOutput(true);
            out = new PrintWriter(connection.getOutputStream());
            out.print(param);
            out.flush();
            //接收结果
            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            //缓冲逐行读取
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            //测试结果
            Log.i("weather", sb.toString());
            Log.i("get weather down", "down");
            System.out.println(sb.toString());
            Message msg = new Message();
            msg.what = MyMessageType.Return_Weather_Message;
            msg.obj = sb;
            handler.sendMessage(msg);
        } catch (Exception ignored) {
            Log.i("get weather err", "err");
            Log.i("err is", "");
            ignored.printStackTrace();
        } finally {
            //关闭流
            try {
                if (is != null) {
                    is.close();
                }
                if (br != null) {
                    br.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e2) {
            }
        }
    }
}
