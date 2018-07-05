package com.example.a10248.myweather_tang.thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.a10248.myweather_tang.bean.User;
import com.example.a10248.myweather_tang.util.HttpUtil;
import com.example.a10248.myweather_tang.util.MyMessageType;
import com.google.gson.Gson;

import java.io.IOException;

public class RegisterThread implements Runnable {

    private User user;
    private Handler handler;
    private String url = "";

    @Override
    public void run() {
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user, User.class);
        try {
            User retUser = HttpUtil.post(url, jsonUser);
            Log.i("register", retUser.getResult() + "");
            Message msg = new Message();
            if (retUser.getResult() == User.Register_success) {
                msg.what = MyMessageType.Register_success;
            } else if (retUser.getResult() == User.Register_failed) {
                msg.what = MyMessageType.Register_failed;
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
        this.url = url + "/register";
    }
}
