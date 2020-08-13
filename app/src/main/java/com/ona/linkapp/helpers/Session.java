package com.ona.linkapp.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.usb.UsbDevice;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ona.linkapp.models.User;

public class Session {

    private SharedPreferences prefs;
    private ObjectMapper mapper;

    public Session(Context context){

        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        mapper = new ObjectMapper();

    }

    public void setUser(User user) throws JsonProcessingException {

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("linkFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept("title", "description", "url", "author", "createAt", "visibility", "_id"));

        filterProvider.addFilter("colFilter", SimpleBeanPropertyFilter.filterOutAllExcept("title", "description", "url", "author", "createAt", "visibility","links", "_id"));

        mapper.setFilterProvider(filterProvider);

        String userJson = mapper.writeValueAsString(user);
        Log.d("Value", userJson);
        prefs.edit().putString("User", userJson).apply();

    }

    public User getUser() throws JsonProcessingException {

        String userJson = prefs.getString("User", "");
        User user = mapper.readValue(userJson, User.class);

        return user;

    }

}
