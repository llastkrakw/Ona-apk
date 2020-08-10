package com.ona.linkapp.apiUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginObject {

    private String username;
    private String password;

    public LoginObject(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }
}
