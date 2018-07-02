package com.example.a10248.myweather_tang.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.example.a10248.myweather_tang.R;
import com.example.a10248.myweather_tang.bean.weather.MyNow;
import com.example.a10248.myweather_tang.util.MyAMapThread;
import com.example.a10248.myweather_tang.util.MyHeWeatherThread;
import com.example.a10248.myweather_tang.util.MyMessageType;

import org.litepal.tablemanager.Connector;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView tv_temperature;
    private TextView tv_temperatureFell;
    private TextView tv_address;
    private TextView tv_weatherText;
    private TextView tv_windir;
    private TextView tv_pcpn;
    private ScrollView weather_layout;
    private SwipeRefreshLayout swipe_refresh;
    private DrawerLayout drawable_layout;

    private String city = "北京";
    private AMapLocation aMapLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_weather);

        SQLiteDatabase db = Connector.getDatabase();

        initView();

        getCity();
    }

    private void initView() {
        tv_temperature = (TextView) findViewById(R.id.tv_temperature);
        tv_temperatureFell = (TextView) findViewById(R.id.tv_temperatureFell);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_weatherText = (TextView) findViewById(R.id.tv_weatherText);
        tv_windir = (TextView) findViewById(R.id.tv_windir);
        tv_pcpn = (TextView) findViewById(R.id.tv_pcpn);
//        btn_quality = (Button) findViewById(R.id.btn_quality);
//        btn_forecast = (Button) findViewById(R.id.btn_forecast);
        weather_layout = (ScrollView) findViewById(R.id.weather_layout);
        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        drawable_layout = (DrawerLayout) findViewById(R.id.drawable_layout);

        swipe_refresh.setOnRefreshListener(this);

//        btn_quality.setOnClickListener(this);
//        btn_forecast.setOnClickListener(this);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == MyMessageType.Return_City_Message) {
                Log.i("获得城市信息", "123");
                aMapLocation = (AMapLocation) msg.obj;
//                Log.i("city in main", aMapLocation.getCity());
                Toast.makeText(getApplicationContext(), "当前城市:" + aMapLocation.getCity(), Toast.LENGTH_SHORT).show();
                Log.i("city in main", aMapLocation.getCity());
                tv_address.setText(aMapLocation.getCity());
                getWeather();
                swipe_refresh.setRefreshing(false);
            } else if (msg.what == MyMessageType.Return_Weather_Message) {
                Log.i("weather in main", msg.obj.toString());
                MyNow myNow = (MyNow) msg.obj;
                tv_temperature.setText(myNow.getTemperature());
                tv_temperatureFell.setText(myNow.getTemperature_feel());
                tv_address.setText(tv_address.getText() + " " + myNow.getLocation());
                tv_weatherText.setText(myNow.getWeather());
                tv_windir.setText(myNow.getWind_dir() + " " + myNow.getWind_sc() + "级");
                tv_pcpn.setText(myNow.getPcpn());
            } else if (msg.what == MyMessageType.Return_Weather_Message_Fail) {
                Toast.makeText(getApplicationContext(), "天气查询出错", Toast.LENGTH_SHORT).show();
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
        MyHeWeatherThread myHeWeather = new MyHeWeatherThread(aMapLocation.getLongitude() + "," + aMapLocation.getLatitude(), handler, this);
        Thread myHeWeatherThread = new Thread(myHeWeather);
        myHeWeatherThread.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


    @Override
    public void onRefresh() {
        Toast.makeText(getApplicationContext(), "下拉", Toast.LENGTH_SHORT).show();
        getCity();
    }
}
