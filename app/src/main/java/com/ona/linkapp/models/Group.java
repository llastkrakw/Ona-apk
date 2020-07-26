package com.ona.linkapp.models;

import java.util.Date;
import java.util.List;

public class Group extends UrlElement {

    private List<Link> links;
    private List<User> members;
    private List<Collection> collections;

    public Group(String title, String description, String author, Date createdAt, List<Link> links, List<User> members, List<Collection> collections) {
        super(title, description, author, createdAt);
        this.links = links;
        this.members = members;
        this.collections = collections;
    }

    public Group(String id, String title, String description, String author, Date createdAt, int likes, List<Link> links, List<User> members, List<Collection> collections) {
        super(id, title, description, author, createdAt, likes);
        this.links = links;
        this.members = members;
        this.collections = collections;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }
}
