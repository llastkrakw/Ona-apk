package com.ona.linkapp.models;

import java.util.Date;

public class UrlElement {

    private String id;
    private String title;
    private String description;
    private String author;
    private String createdAt;
    private int  likes;

    public UrlElement(){

    }

    public UrlElement(String title, String description, String author, String createdAt) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.createdAt = createdAt;
    }

    public UrlElement(String id, String title, String description, String author, String createdAt, int likes) {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
