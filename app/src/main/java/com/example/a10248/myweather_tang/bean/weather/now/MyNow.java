package com.example.a10248.myweather_tang.bean.weather.now;

import com.example.a10248.myweather_tang.bean.basic.MyBasic;

import org.litepal.crud.LitePalSupport;

import interfaces.heweather.com.interfacesmodule.bean.basic.Basic;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;

public class MyNow extends LitePalSupport {
    private MyBasic basic;
    private String status;
    private MyNowBase now;
    private String uptime;

    public MyNow() {
    }

    public MyNow(MyBasic basic, String status, MyNowBase now, String uptime) {
        this.basic = basic;
        this.status = status;
        this.now = now;
        this.uptime = uptime;
    }

    public MyBasic getBasic() {
        return basic;
    }

    public void setBasic(MyBasic basic) {
        this.basic = basic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MyNowBase getNow() {
        return now;
    }

    public void setNow(MyNowBase now) {
        this.now = now;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }
}
