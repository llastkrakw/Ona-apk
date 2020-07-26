package com.ona.linkapp.models;

import java.util.Date;

public class UrlElement {

    private String id;
    private String title;
    private String description;
    private String author;
    private Date createdAt;
    private int  likes;

    public UrlElement(){

    }

    public UrlElement(String title, String description, String author, Date createdAt) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.createdAt = createdAt;
    }

    public UrlElement(String id, String title, String description, String author, Date createdAt, int likes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.createdAt = createdAt;
        this.likes = likes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
