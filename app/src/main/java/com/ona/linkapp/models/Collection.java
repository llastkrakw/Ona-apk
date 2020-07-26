package com.ona.linkapp.models;

import java.util.Date;
import java.util.List;

public class Collection extends UrlElement {

    private List<Link> links;

    public Collection(String title, String description, String author, Date createdAt, List<Link> links) {
        super(title, description, author, createdAt);
        this.links = links;
    }

    public Collection(String id, String title, String description, String author, Date createdAt, int likes, List<Link> links) {
        super(id, title, description, author, createdAt, likes);
        this.links = links;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
