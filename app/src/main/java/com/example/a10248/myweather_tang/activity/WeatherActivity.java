package com.example.a10248.myweather_tang.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.example.a10248.myweather_tang.R;
import com.example.a10248.myweather_tang.bean.weather.MyNow;
import com.example.a10248.myweather_tang.util.HttpUtil;
import com.example.a10248.myweather_tang.util.MyAMapThread;
import com.example.a10248.myweather_tang.util.MyHeWeatherThread;
import com.example.a10248.myweather_tang.util.MyMessageType;

import org.litepal.tablemanager.Connector;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

    private ImageView bing_pic_img;

    private boolean autoLocation = true;
    private boolean testLayout = true;
    private String city = "";
    private AMapLocation aMapLocation;
//    private Button btn_setAutoLocation;
//    private Button btn_setCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_weather);

        SQLiteDatabase db = Connector.getDatabase();

        initView();

        loadBingPic();

        if (!testLayout) updateWeather();
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

//        btn_setAutoLocation = (Button) findViewById(R.id.btn_setAutoLocation);
//        btn_setAutoLocation.setOnClickListener(this);
//        btn_setCity = (Button) findViewById(R.id.btn_setCity);
//        btn_setCity.setOnClickListener(this);
        bing_pic_img = (ImageView) findViewById(R.id.bing_pic_img);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == MyMessageType.Return_City_Message) {
                aMapLocation = (AMapLocation) msg.obj;
                Toast.makeText(getApplicationContext(), "当前城市:" + aMapLocation.getCity(), Toast.LENGTH_SHORT).show();
                tv_address.setText(aMapLocation.getCity());
                getWeather(aMapLocation.getLongitude() + "," + aMapLocation.getLatitude());
            } else if (msg.what == MyMessageType.Return_Weather_Message) {
                Log.i("weather in main", msg.obj.toString());
                MyNow myNow = (MyNow) msg.obj;
                tv_temperature.setText(myNow.getTemperature());
                tv_temperatureFell.setText(myNow.getTemperature_feel());
                tv_address.setText(myNow.getLocation());
                tv_weatherText.setText(myNow.getWeather());
                tv_windir.setText(myNow.getWind_dir() + " " + myNow.getWind_sc() + "级");
                tv_pcpn.setText(myNow.getPcpn());
                swipe_refresh.setRefreshing(false);
            } else if (msg.what == MyMessageType.Return_Weather_Message_Fail) {
                Toast.makeText(getApplicationContext(), "天气查询出错", Toast.LENGTH_SHORT).show();
                swipe_refresh.setRefreshing(false);
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
    private void getWeather(String city) {
        MyHeWeatherThread myHeWeather = new MyHeWeatherThread(city, handler, this);
        Thread myHeWeatherThread = new Thread(myHeWeather);
        myHeWeatherThread.start();
    }

    //更新天气
    private void updateWeather() {
        if (autoLocation) {
            getCity();
        } else {
            getWeather(city);
        }
    }

    /**
     * 加载必应每日一图
     */
    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bing_pic_img);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置城市
     */
    public void setCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WeatherActivity.this);
//        builder.setIcon(R.drawable.ic_launcher_foreground);
        builder.setTitle("请输入城市");
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.dialog_select_city, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);

        final EditText edt_city = (EditText) view.findViewById(R.id.edt_city);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                city = edt_city.getText().toString();
                autoLocation = false;
                updateWeather();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_setAutoLocation:
                autoLocation = true;
                break;
            case R.id.btn_setCity:
//                setCityDialog();
                break;
        }
    }


    @Override
    public void onRefresh() {
        Toast.makeText(getApplicationContext(), "下拉", Toast.LENGTH_SHORT).show();
//        getCity();
        updateWeather();
    }
}
