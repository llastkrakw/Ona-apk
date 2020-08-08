package com.ona.linkapp.apiUtils;

import android.net.Uri;

public class Globals {

    public static final String BASE_URL = "https://ona-api.herokuapp.com/";

    /*
    *
    * Links
    *
    * */

    public static final String GET_LINKS_URL = Uri.parse(BASE_URL).buildUpon().appendPath("links").toString();


    /*
     *
     * Users
     *
     * */

    public static final String GET_USERS_URL = Uri.parse(BASE_URL).buildUpon().appendPath("users").toString();
    public static final String POST_USERS_URL = Uri.parse(BASE_URL).buildUpon().appendPath("users").appendPath("user").toString();

}
