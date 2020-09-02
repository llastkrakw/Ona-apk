package com.ona.linkapp.apiUtils;

import android.net.Uri;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Globals {

    public static final String BASE_URL = "https://ona-api.herokuapp.com/";
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");


    /*
    *
    * Links
    *
    * */

    public static final String GET_LINKS_URL = Uri.parse(BASE_URL).buildUpon().appendPath("links").toString();
    public static final String POST_LINKS_URL = Uri.parse(BASE_URL).buildUpon().appendPath("links").appendPath("link").toString();

    /*
     *
     * Collections
     *
     * */

    public static final String GET_COLLECTIONS_URL = Uri.parse(BASE_URL).buildUpon().appendPath("collections").toString();
    public static final String POST_COLLECTIONS_URL = Uri.parse(BASE_URL).buildUpon().appendPath("collections").appendPath("collection").toString();
    public static final String POST_FORK_COLLECTIONS_URL = Uri.parse(BASE_URL).buildUpon().appendPath("collections").appendPath("fork").toString();

    /*
     *
     * Users
     *
     * */

    public static final String GET_USERS_URL = Uri.parse(BASE_URL).buildUpon().appendPath("users").toString();
    public static final String POST_USERS_URL = Uri.parse(BASE_URL).buildUpon().appendPath("users").appendPath("user").toString();
    public static final String LOGIN_USERS_URL = Uri.parse(BASE_URL).buildUpon().appendPath("login").toString();

    /*
     *
     * Shorten
     *
     * */

    public static final String GET_SHORT_URL = Uri.parse(BASE_URL).buildUpon().appendPath("shorts").toString();
    public static final String POST_SHORT_URL = Uri.parse(BASE_URL).buildUpon().appendPath("shorts").appendPath("short").toString();

}
