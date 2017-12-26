package com.example.a10248.myweather_tang.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a10248.myweather_tang.util.MyAMapThread;
import com.example.a10248.myweather_tang.util.MyHeWeatherThread;
import com.example.a10248.myweather_tang.util.MyMessageType;
import com.example.a10248.myweather_tang.R;


public class MainActivity extends AppCompatActivity {

    private Button btn_getCity, btn_getWeather;

    private String city = "";

    //Handler监听
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            if (msg.what == MyMessageType.Return_Weather_Message) {
                Log.i("weather in main", msg.obj.toString());
            } else if (msg.what == MyMessageType.Return_City_Message) {
                city = (String) msg.obj;
                Log.i("city in main", city);
                Toast.makeText(getApplicationContext(), "当前城市:" + city, Toast.LENGTH_SHORT).show();
            }
        }
    };

    //初始化
    public void init() {
        btn_getCity = findViewById(R.id.btn_getCity);
        btn_getCity.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("onClick", "getCity");
                        getCity();
                    }
                }
        );
        btn_getWeather = findViewById(R.id.btn_getWeather);
        btn_getWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("onClick", "getWeather");
                if (city.equals("")) {
                    Toast.makeText(getApplicationContext(), "请先获取城市", Toast.LENGTH_SHORT).show();
                } else {
                    getWeather();
                }
            }
        });
    }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

//                getWeather();

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        mLocationClient.stopLocation();

//        if (mMapLocation != null) {
//            String city = mMapLocation.getCity();
//            Log.i("aMap mCity", city);
//        }


//        Log.i("SHA1", sHA1(this));//查看SHA1
    }

    //查看SHA1
//    public static String sHA1(Context context) {
//        try {
//            PackageInfo info = context.getPackageManager().getPackageInfo(
//                    context.getPackageName(), PackageManager.GET_SIGNATURES);
//            byte[] cert = info.signatures[0].toByteArray();
//            MessageDigest md = MessageDigest.getInstance("SHA1");
//            byte[] publicKey = md.digest(cert);
//            StringBuffer hexString = new StringBuffer();
//            for (int i = 0; i < publicKey.length; i++) {
//                String appendString = Integer.toHexString(0xFF & publicKey[i])
//                        .toUpperCase(Locale.US);
//                if (appendString.length() == 1)
//                    hexString.append("0");
//                hexString.append(appendString);
//                hexString.append(":");
//            }
//            String result = hexString.toString();
//            return result.substring(0, result.length() - 1);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
