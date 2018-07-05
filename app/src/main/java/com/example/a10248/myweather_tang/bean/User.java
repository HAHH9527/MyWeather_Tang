package com.example.a10248.myweather_tang.bean;

public class User {

    public static final int Login_success = 1;
    public static final int Login_failed = 2;
    public static final int Register_success = 3;
    public static final int Register_failed = 4;

    //用户姓名
    private String UserName;
    //用户密码
    private String Password;
    //用户昵称
    private String UserNick;

    private int Result;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserNick() {
        return UserNick;
    }

    public void setUserNick(String userNick) {
        UserNick = userNick;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }
}
