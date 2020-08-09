package com.ona.linkapp.datas.online;

import com.ona.linkapp.apiUtils.Globals;
import com.ona.linkapp.apiUtils.Repository;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.ona.linkapp.apiUtils.Globals.JSON;

public class UserDao {

    private Repository repository;

    public UserDao(){
        repository = new Repository();
    }


    public String getUsers() throws IOException {

        String usersJson = repository.get(Globals.GET_USERS_URL);

        return usersJson;

    }

    public String addUser(String user) throws IOException {

        String userJson = repository.post(Globals.POST_USERS_URL, user);

        return userJson;

    }

}
