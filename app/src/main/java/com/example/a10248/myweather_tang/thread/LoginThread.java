package com.example.a10248.myweather_tang.thread;

import com.example.a10248.myweather_tang.bean.User;
import com.example.a10248.myweather_tang.util.HttpUtil;
import com.example.a10248.myweather_tang.util.MyMessageType;
import com.google.gson.Gson;

import java.io.IOException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginThread implements Runnable {

    private User user;
    private Handler handler;
    private String url = "";

    @Override
    public void run() {
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user, User.class);
        try {
            User retUser = HttpUtil.post(url, jsonUser);
            Log.i("login", retUser.getResult() + "");
            Message msg = new Message();
            if (retUser.getResult() == User.Login_success) {
                msg.what = MyMessageType.Login_success;
                msg.obj = retUser.getUserNick();
            } else if (retUser.getResult() == User.Login_failed) {
                msg.what = MyMessageType.Login_failed;
            }
            handler.sendMessage(msg);
        } catch (IOException e) {
            Log.e("LoginErr", e.toString());
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url + "/login";
    }
}
