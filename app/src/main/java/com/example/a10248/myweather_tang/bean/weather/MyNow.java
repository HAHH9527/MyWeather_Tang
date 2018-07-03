package com.example.a10248.myweather_tang.bean.weather;

import org.litepal.crud.LitePalSupport;

/**
 * 实时天气实体类
 */

public class MyNow extends LitePalSupport {

    private String uptime;
    private String location;
    private String temperature;
    private String temperature_feel;
    private String weather;
    private String wind_dir;
    private String wind_sc;
    private String pcpn;

    public MyNow() {
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTemperature_feel() {
        return temperature_feel;
    }

    public void setTemperature_feel(String temperature_feel) {
        this.temperature_feel = temperature_feel;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getWind_sc() {
        return wind_sc;
    }

    public void setWind_sc(String wind_sc) {
        this.wind_sc = wind_sc;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }
}
