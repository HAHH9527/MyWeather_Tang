package com.example.a10248.myweather_tang.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.example.a10248.myweather_tang.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestActivity extends AppCompatActivity {

    //最近几天天气
    private ListView list_daily_forecast;
    private ScrollView scrollView;

    ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();

//    WeatherBean.HeWeather6Bean.DailyForecastBean dailyForecastBean = new WeatherBean.HeWeather6Bean.DailyForecastBean();

//    /**
//     * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题
//     *
//     * @param listView
//     */
//    public void setListViewHeight(ListView listView) {
//
//        // 获取ListView对应的Adapter
//
//        ListAdapter listAdapter = listView.getAdapter();
//
//        if (listAdapter == null) {
//            return;
//        }
//        int totalHeight = 0;
//        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, 0); // 计算子项View 的宽高
//            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }

    private void init_dailyForecastBean() {
//        dailyForecastBean.setDate("2013-12-30");
//        dailyForecastBean.setCond_code_d("100");
//        dailyForecastBean.setCond_txt_d("晴");
//        dailyForecastBean.setTmp_max("4");
//        dailyForecastBean.setTmp_min("-5");
    }

    //初始化最近几天天气
    private void init_list_daily_forecast() {
//        Map<String, Object> item = new HashMap<String, Object>();
//        item.put("date", dailyForecastBean.getDate());
//        item.put("cond_code_d", R.drawable.weather100);
//        item.put("cond_txt_d", dailyForecastBean.getCond_txt_d());
//        item.put("tmp", dailyForecastBean.getTmp_max() + "/" + dailyForecastBean.getTmp_min());
        for (int i = 0; i < 10; i++) {
//            mData.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, mData, R.layout.arraylist_daily_forecast_layout, new String[]{"date", "cond_code_d", "cond_txt_d", "tmp"}, new int[]{R.id.text_theDay, R.id.img_theDayWeather, R.id.tv_theDayWeather, R.id.text_temperature});
        list_daily_forecast.setAdapter(adapter);
//        setListViewHeight(list_daily_forecast);
    }

    //初始化控件
    private void init() {
        list_daily_forecast = findViewById(R.id.list_daily_forecast);
//        scrollView = findViewById(R.id.scroll_parent);
//        list_daily_forecast.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                scrollView.requestDisallowInterceptTouchEvent(true); 
//                return false;
//            }
//        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //初始化界面
        init();

        init_dailyForecastBean();
        init_list_daily_forecast();
//        super.onCreate(savedInstanceState);
    }
}
