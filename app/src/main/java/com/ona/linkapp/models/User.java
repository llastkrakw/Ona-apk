package com.ona.linkapp.models;

import java.util.Date;
import java.util.List;

public class User {

    private String id;
    private String username;
    private String password;
    private String email;
    private List<Collection> collections;
    private List<Link> links;
    private List<Group> groups;
    private Date createdAt;

    public User(){

    }

    public User(String username, String password, String email, List<Collection> collections, List<Link> links, List<Group> groups, Date createdAt) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.collections = collections;
        this.links = links;
        this.groups = groups;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
