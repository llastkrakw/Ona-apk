package com.ona.linkapp.models;

import java.util.Date;

public class Link extends UrlElement{

    private int clicked;

    public Link(String title, String description,  String author, Date createdAt, int clicked) {
        super(title, description, author, createdAt);
        this.clicked = clicked;
    }

    public Link(String id, String title, String description, String author, Date createdAt, int likes, int clicked) {
        super(id, title, description, author, createdAt, likes);
        this.clicked = clicked;
    }

    public int getClicked() {
        return clicked;
    }

    public void setClicked(int clicked) {
        this.clicked = clicked;
    }
}
