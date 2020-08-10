package com.ona.linkapp.datas.online;

import android.net.Uri;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ona.linkapp.apiUtils.Globals;
import com.ona.linkapp.apiUtils.LoginObject;
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

    public String getUser(String id) throws IOException {

        String userJson = repository.get(Uri.parse(Globals.GET_USERS_URL).buildUpon().appendPath(id).toString());

        return userJson;

    }


    public String Login(String username, String password) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        LoginObject logger = new LoginObject(username, password);
        String json = mapper.writeValueAsString(logger);

        String userJson = repository.Login(Globals.LOGIN_USERS_URL, json);

        return userJson;

    }


    public String addUser(String user) throws IOException {

        String userJson = repository.post(Globals.POST_USERS_URL, user);

        return userJson;

    }

}
