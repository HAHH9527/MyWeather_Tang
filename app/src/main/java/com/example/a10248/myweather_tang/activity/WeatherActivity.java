package com.example.a10248.myweather_tang.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.example.a10248.myweather_tang.R;
import com.example.a10248.myweather_tang.bean.weather.MyForecast;
import com.example.a10248.myweather_tang.bean.weather.MyNow;
import com.example.a10248.myweather_tang.thread.MyAMapThread;
import com.example.a10248.myweather_tang.thread.MyHeWeatherThread;
import com.example.a10248.myweather_tang.util.HttpUtil;
import com.example.a10248.myweather_tang.util.MyMessageType;

import org.litepal.tablemanager.Connector;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, NavigationView.OnNavigationItemSelectedListener {

    //测试开关
    private boolean testLayout = true;

    //变量
    private String city = "";
    private AMapLocation aMapLocation;
    private boolean autoLocation = true;
    //BGM有关
    private int nowBgmId = R.raw.forest;

    //布局
    private SwipeRefreshLayout swipe_refresh;
    private DrawerLayout drawable_layout;
    private ImageView bing_pic_img;
    private NavigationView navView;

    //侧滑菜单
    private View headerView;
    private TextView tv_user_name;

    //实时天气
    private TextView tv_temperature;
    private TextView tv_temperatureFell;
    private TextView tv_address;
    private TextView tv_weatherText;
    private TextView tv_windir;
    private TextView tv_pcpn;

    //未来天气
    private TextView tv_forecast1_date;
    private TextView tv_forecast1_weather;
    private TextView tv_forecast1_temperature;
    private TextView tv_forecast2_date;
    private TextView tv_forecast2_weather;
    private TextView tv_forecast2_temperature;
    private TextView tv_forecast3_date;
    private TextView tv_forecast3_weather;
    private TextView tv_forecast3_temperature;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //操作刷新数据库
        SQLiteDatabase db = Connector.getDatabase();
        //获取视图控件
        initView();
        //加载必应每日一图作为背景
        loadBingPic();

        //更新天气
        if (!testLayout) updateWeather();
    }

    /**
     * 获取视图控件
     */
    private void initView() {

        bing_pic_img = (ImageView) findViewById(R.id.bing_pic_img);
        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipe_refresh.setOnRefreshListener(this);
        drawable_layout = (DrawerLayout) findViewById(R.id.drawable_layout);
        navView = (NavigationView) findViewById(R.id.navView);
        navView.setNavigationItemSelectedListener(this);
        headerView = navView.getHeaderView(0);
        tv_user_name = (TextView) headerView.findViewById(R.id.tv_user_name);
        tv_user_name.setOnClickListener(this);

        tv_temperature = (TextView) findViewById(R.id.tv_temperature);
        tv_temperatureFell = (TextView) findViewById(R.id.tv_temperatureFell);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_weatherText = (TextView) findViewById(R.id.tv_weatherText);
        tv_windir = (TextView) findViewById(R.id.tv_windir);
        tv_pcpn = (TextView) findViewById(R.id.tv_pcpn);

        tv_forecast1_date = (TextView) findViewById(R.id.tv_forecast1_date);
        tv_forecast1_weather = (TextView) findViewById(R.id.tv_forecast1_weather);
        tv_forecast1_temperature = (TextView) findViewById(R.id.tv_forecast1_temperature);
        tv_forecast2_date = (TextView) findViewById(R.id.tv_forecast2_date);
        tv_forecast2_weather = (TextView) findViewById(R.id.tv_forecast2_weather);
        tv_forecast2_temperature = (TextView) findViewById(R.id.tv_forecast2_temperature);
        tv_forecast3_date = (TextView) findViewById(R.id.tv_forecast3_date);
        tv_forecast3_weather = (TextView) findViewById(R.id.tv_forecast3_weather);
        tv_forecast3_temperature = (TextView) findViewById(R.id.tv_forecast3_temperature);
    }

    /**
     * 设置当前BGM
     *
     * @param id
     */
    private void setNowBgmId(int id) {
        nowBgmId = id;
    }

    /**
     * 播放BGM
     */
    private void playBGM() {
        if (mediaPlayer != null) mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(this, nowBgmId);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    /**
     * 停止BGM
     */
    private void stopBGM() {
        mediaPlayer.stop();
    }

    /**
     * 获得城市
     */
    private void getCity() {
        MyAMapThread myAMap = new MyAMapThread(this, handler);
        Thread myAMapThread = new Thread(myAMap);
        myAMapThread.start();
    }

    /**
     * 获得天气
     *
     * @param city
     */
    private void getWeather(String city) {
        MyHeWeatherThread myHeWeather = new MyHeWeatherThread(city, handler, this);
        Thread myHeWeatherThread = new Thread(myHeWeather);
        myHeWeatherThread.start();
    }

    /**
     * 更新天气
     */
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
    private void displaySetCityDialog() {
        AlertDialog dialogSetCity = new AlertDialog.Builder(WeatherActivity.this).create();
        dialogSetCity.setTitle("请输入城市");
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.dialog_select_city, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        dialogSetCity.setView(view);

        final EditText edt_city = (EditText) view.findViewById(R.id.edt_city);

        dialogSetCity.setButton(AlertDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                city = edt_city.getText().toString();
                autoLocation = false;
                updateWeather();
                dialogInterface.dismiss();
            }
        });

        dialogSetCity.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogSetCity.show();
    }

    /**
     * 登录
     */
    private void displayLoginDialog() {
        AlertDialog dialogLogin = new AlertDialog.Builder(this).create();//创建对话框
//        dialog.setIcon(R.mipmap.ic_launcher);//设置对话框icon
        dialogLogin.setTitle("欢迎使用和天气");//设置对话框标题
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.dialog_login, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        dialogLogin.setView(view);

        //分别设置三个button
        dialogLogin.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();//关闭对话框
            }
        });

        dialogLogin.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();//关闭对话框
            }
        });

        dialogLogin.setButton(DialogInterface.BUTTON_NEUTRAL, "注册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                dialogInterface.dismiss();
                displayRegisterDialog();
            }
        });
        dialogLogin.show();//显示对话框
    }

    /**
     * 注册
     */
    private void displayRegisterDialog() {
        AlertDialog dialogRegister = new AlertDialog.Builder(WeatherActivity.this).create();
        dialogRegister.setTitle("注册");
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.dialog_register, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        dialogRegister.setView(view);

        dialogRegister.setButton(AlertDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });


        dialogRegister.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialogRegister.show();
    }

    /**
     * 监听Handler
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyMessageType.Return_City_Message:
                    aMapLocation = (AMapLocation) msg.obj;
                    Toast.makeText(getApplicationContext(), "当前城市:" + aMapLocation.getCity(), Toast.LENGTH_SHORT).show();
                    tv_address.setText(aMapLocation.getCity());
                    getWeather(aMapLocation.getLongitude() + "," + aMapLocation.getLatitude());
                    break;
                case MyMessageType.Return_WeatherNow_Message:
                    Log.i("weather in main", msg.obj.toString());
                    MyNow myNow = (MyNow) msg.obj;
                    tv_temperature.setText(myNow.getTemperature());
                    tv_temperatureFell.setText(myNow.getTemperature_feel());
                    tv_address.setText(myNow.getLocation());
                    tv_weatherText.setText(myNow.getWeather());
                    tv_windir.setText(myNow.getWind_dir() + " " + myNow.getWind_sc() + "级");
                    tv_pcpn.setText(myNow.getPcpn());
                    swipe_refresh.setRefreshing(false);
                    break;
                case MyMessageType.Return_WeatherNow_Message_Fail:
                    Toast.makeText(getApplicationContext(), "当前天气查询出错", Toast.LENGTH_SHORT).show();
                    swipe_refresh.setRefreshing(false);
                    break;
                case MyMessageType.Return_WeatherForecast_Message:
                    MyForecast myForecast = (MyForecast) msg.obj;
                    tv_forecast1_date.setText(myForecast.getDate1());
                    tv_forecast1_weather.setText(myForecast.getCond1());
                    tv_forecast1_temperature.setText(myForecast.getTmp1());
                    tv_forecast2_date.setText(myForecast.getDate2());
                    tv_forecast2_weather.setText(myForecast.getCond2());
                    tv_forecast2_temperature.setText(myForecast.getTmp2());
                    tv_forecast3_date.setText(myForecast.getDate3());
                    tv_forecast3_weather.setText(myForecast.getCond3());
                    tv_forecast3_temperature.setText(myForecast.getTmp3());
                    swipe_refresh.setRefreshing(false);
                    break;
                case MyMessageType.Return_WeatherForecast_Message_Fail:
                    Toast.makeText(getApplicationContext(), "未来天气查询出错", Toast.LENGTH_SHORT).show();
                    swipe_refresh.setRefreshing(false);
                    break;
            }
        }
    };

    /**
     * 监听点击
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_user_name:
                Log.i("点击", "tv_user_name");
                displayLoginDialog();
                drawable_layout.closeDrawer(Gravity.LEFT);
                break;
        }
    }


    /**
     * 监听下拉
     */
    @Override
    public void onRefresh() {
        Toast.makeText(getApplicationContext(), "下拉", Toast.LENGTH_SHORT).show();
        updateWeather();
    }


    /**
     * 监听侧滑菜单
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_setAutoLocation:
                autoLocation = true;
                updateWeather();
                break;
            case R.id.nav_item_setCity:
                displaySetCityDialog();
                break;
            case R.id.bgm_play_pause:
                playBGM();
                break;
            case R.id.bgm_stop:
                stopBGM();
                break;
            case R.id.bgm_forest:
                setNowBgmId(R.raw.forest);
                if (mediaPlayer.isPlaying()) playBGM();
                break;
            case R.id.bgm_summer_night:
                setNowBgmId(R.raw.summer_night);
                if (mediaPlayer.isPlaying()) playBGM();
                break;
            case R.id.bgm_beach:
                setNowBgmId(R.raw.beach);
                if (mediaPlayer.isPlaying()) playBGM();
                break;
            case R.id.bgm_spring_rain:
                setNowBgmId(R.raw.spring_rain);
                if (mediaPlayer.isPlaying()) playBGM();
                break;
            case R.id.bgm_stove_fire:
                setNowBgmId(R.raw.stove_fire);
                if (mediaPlayer.isPlaying()) playBGM();
                break;
        }
        drawable_layout.closeDrawer(Gravity.LEFT);
        return false;
    }

    /**
     * 监听返回键
     */
    @Override
    public void onBackPressed() {
        if (drawable_layout.isDrawerOpen(Gravity.LEFT)) drawable_layout.closeDrawer(Gravity.LEFT);
        else super.onBackPressed();
    }
}
