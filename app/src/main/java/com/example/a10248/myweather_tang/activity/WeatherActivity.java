package com.example.a10248.myweather_tang.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10248.myweather_tang.R;
import com.example.a10248.myweather_tang.bean.WeatherBean;
import com.example.a10248.myweather_tang.util.MyAMapThread;
import com.example.a10248.myweather_tang.util.MyHeWeatherThread;
import com.example.a10248.myweather_tang.util.MyMessageType;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_temperature;
    private TextView tv_address;
    private TextView tv_weatherText;
    private Button btn_quality;
    private Button btn_forecast;
    private ScrollView weather_layout;
    private SwipeRefreshLayout swipe_refresh;
    private DrawerLayout drawable_layout;

    private String city = "北京";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();

        getCity();
    }

    private void initView() {
        tv_temperature = (TextView) findViewById(R.id.tv_temperature);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_weatherText = (TextView) findViewById(R.id.tv_weatherText);
        btn_quality = (Button) findViewById(R.id.btn_quality);
        btn_forecast = (Button) findViewById(R.id.btn_forecast);
        weather_layout = (ScrollView) findViewById(R.id.weather_layout);
        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        drawable_layout = (DrawerLayout) findViewById(R.id.drawable_layout);

        btn_quality.setOnClickListener(this);
        btn_forecast.setOnClickListener(this);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            if (msg.what == MyMessageType.Return_Weather_Message) {
                Log.i("weather in main", msg.obj.toString());
                WeatherBean weatherBean = (WeatherBean) msg.obj;
                tv_temperature.setText(weatherBean.getHeWeather6().get(0).getNow().getTmp());
            } else if (msg.what == MyMessageType.Return_City_Message) {
                city = (String) msg.obj;
                Log.i("city in main", city);
                Toast.makeText(getApplicationContext(), "当前城市:" + city, Toast.LENGTH_SHORT).show();
                getWeather();
            }
        }
    };

    //启动获得城市
    private void getCity() {
        MyAMapThread myAMap = new MyAMapThread(this, handler);
        Thread myAMapThread = new Thread(myAMap);
        myAMapThread.start();
    }

    //启动获得天气
    private void getWeather() {
        MyHeWeatherThread myHeWeather = new MyHeWeatherThread(city, handler);
        Thread myHeWeatherThread = new Thread(myHeWeather);
        myHeWeatherThread.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_quality:

                break;
            case R.id.btn_forecast:

                break;
        }
    }
}
