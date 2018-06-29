package com.example.a10248.myweather_tang.bean.weather.now;

import org.litepal.crud.LitePalSupport;

public class MyNowBase extends LitePalSupport {
    private String fl;
    private String tmp;
    private String cond_code;
    private String cond_txt;
    private String hum;
    private String pcpn;
    private String pres;
    private String vis;
    private String cloud;
    private String wind_deg;
    private String wind_dir;
    private String wind_sc;
    private String wind_spd;

    private MyNow myNow;

    public MyNowBase() {
    }

    public MyNowBase(String fl, String tmp, String cond_code, String cond_txt, String hum, String pcpn, String pres, String vis, String cloud, String wind_deg, String wind_dir, String wind_sc, String wind_spd) {
        this.fl = fl;
        this.tmp = tmp;
        this.cond_code = cond_code;
        this.cond_txt = cond_txt;
        this.hum = hum;
        this.pcpn = pcpn;
        this.pres = pres;
        this.vis = vis;
        this.cloud = cloud;
        this.wind_deg = wind_deg;
        this.wind_dir = wind_dir;
        this.wind_sc = wind_sc;
        this.wind_spd = wind_spd;
    }

    public String getCloud() {
        return this.cloud;
    }

    public void setCloud(String var1) {
        this.cloud = var1;
    }

    public String getCond_code() {
        return this.cond_code;
    }

    public void setCond_code(String var1) {
        this.cond_code = var1;
    }

    public String getCond_txt() {
        return this.cond_txt;
    }

    public void setCond_txt(String var1) {
        this.cond_txt = var1;
    }

    public String getFl() {
        return this.fl;
    }

    public void setFl(String var1) {
        this.fl = var1;
    }

    public String getHum() {
        return this.hum;
    }

    public void setHum(String var1) {
        this.hum = var1;
    }

    public String getPcpn() {
        return this.pcpn;
    }

    public void setPcpn(String var1) {
        this.pcpn = var1;
    }

    public String getPres() {
        return this.pres;
    }

    public void setPres(String var1) {
        this.pres = var1;
    }

    public String getTmp() {
        return this.tmp;
    }

    public void setTmp(String var1) {
        this.tmp = var1;
    }

    public String getVis() {
        return this.vis;
    }

    public void setVis(String var1) {
        this.vis = var1;
    }

    public String getWind_deg() {
        return this.wind_deg;
    }

    public void setWind_deg(String var1) {
        this.wind_deg = var1;
    }

    public String getWind_dir() {
        return this.wind_dir;
    }

    public void setWind_dir(String var1) {
        this.wind_dir = var1;
    }

    public String getWind_sc() {
        return this.wind_sc;
    }

    public void setWind_sc(String var1) {
        this.wind_sc = var1;
    }

    public String getWind_spd() {
        return this.wind_spd;
    }

    public void setWind_spd(String var1) {
        this.wind_spd = var1;
    }

    public MyNow getMyNow() {
        return myNow;
    }

    public void setMyNow(MyNow myNow) {
        this.myNow = myNow;
    }
}
