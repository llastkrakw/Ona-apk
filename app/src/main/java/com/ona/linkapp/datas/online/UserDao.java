package com.ona.linkapp.datas.online;

import com.ona.linkapp.apiUtils.Globals;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserDao {

    private static OkHttpClient client;

    public UserDao(){
        client = new OkHttpClient();
    }

    private String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String getUsers() throws IOException {

        String usersJson = run(Globals.GET_USERS_URL);

        return usersJson;

    }

}
