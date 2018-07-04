package com.example.a10248.myweather_tang.bean.weather;

import org.litepal.crud.LitePalSupport;

/**
 * 未来天气实体类
 */
public class MyForecast extends LitePalSupport {

    private String uptime;
    private String location;
    private String serchLoc;

    private String date1;
    private String tmp1;
    private String cond1;

    private String date2;
    private String tmp2;
    private String cond2;

    private String date3;
    private String tmp3;
    private String cond3;

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

    public String getSerchLoc() {
        return serchLoc;
    }

    public void setSerchLoc(String serchLoc) {
        this.serchLoc = serchLoc;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getTmp1() {
        return tmp1;
    }

    public void setTmp1(String tmp1) {
        this.tmp1 = tmp1;
    }

    public String getCond1() {
        return cond1;
    }

    public void setCond1(String cond1) {
        this.cond1 = cond1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getTmp2() {
        return tmp2;
    }

    public void setTmp2(String tmp2) {
        this.tmp2 = tmp2;
    }

    public String getCond2() {
        return cond2;
    }

    public void setCond2(String cond2) {
        this.cond2 = cond2;
    }

    public String getDate3() {
        return date3;
    }

    public void setDate3(String date3) {
        this.date3 = date3;
    }

    public String getTmp3() {
        return tmp3;
    }

    public void setTmp3(String tmp3) {
        this.tmp3 = tmp3;
    }

    public String getCond3() {
        return cond3;
    }

    public void setCond3(String cond3) {
        this.cond3 = cond3;
    }
}
