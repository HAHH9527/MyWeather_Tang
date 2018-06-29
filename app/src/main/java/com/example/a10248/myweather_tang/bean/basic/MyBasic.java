package com.example.a10248.myweather_tang.bean.basic;

import com.example.a10248.myweather_tang.bean.weather.now.MyNow;

import org.litepal.crud.LitePalSupport;

public class MyBasic extends LitePalSupport {
    private String cid;
    private String location;
    private String parent_city;
    private String admin_area;
    private String cnty;
    private String lat;
    private String lon;
    private String tz;

    private MyNow myNow;

    public MyBasic() {
    }

    public MyBasic(String cid, String location, String parent_city, String admin_area, String cnty, String lat, String lon, String tz) {
        this.cid = cid;
        this.location = location;
        this.parent_city = parent_city;
        this.admin_area = admin_area;
        this.cnty = cnty;
        this.lat = lat;
        this.lon = lon;
        this.tz = tz;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String var1) {
        this.cid = var1;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String var1) {
        this.location = var1;
    }

    public String getParent_city() {
        return this.parent_city;
    }

    public void setParent_city(String var1) {
        this.parent_city = var1;
    }

    public String getAdmin_area() {
        return this.admin_area;
    }

    public void setAdmin_area(String var1) {
        this.admin_area = var1;
    }

    public String getCnty() {
        return this.cnty;
    }

    public void setCnty(String var1) {
        this.cnty = var1;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String var1) {
        this.lat = var1;
    }

    public String getLon() {
        return this.lon;
    }

    public void setLon(String var1) {
        this.lon = var1;
    }

    public String getTz() {
        return this.tz;
    }

    public void setTz(String var1) {
        this.tz = var1;
    }

    public MyNow getMyNow() {
        return myNow;
    }

    public void setMyNow(MyNow myNow) {
        this.myNow = myNow;
    }
}
