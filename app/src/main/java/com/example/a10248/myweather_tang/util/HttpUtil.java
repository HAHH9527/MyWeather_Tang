package com.example.a10248.myweather_tang.util;

import com.example.a10248.myweather_tang.bean.User;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static User post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return new Gson().fromJson(response.body().string(), User.class);
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


}
