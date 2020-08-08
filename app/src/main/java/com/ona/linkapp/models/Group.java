package com.ona.linkapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Group extends UrlElement implements Parcelable {

    private List<Link> links;
    private List<User> members;
    private List<Collection> collections;


    public Group(){}

    public Group(String title, String description, String author, String createAt, String url, List<Link> links, List<User> members, List<Collection> collections) {
        super(title, description, author, createAt, url);
        this.links = links;
        this.members = members;
        this.collections = collections;
    }

    public Group(String title, String description, String author, String createAt, List<Link> links, List<User> members, List<Collection> collections) {
        super(title, description, author, createAt);
        this.links = links;
        this.members = members;
        this.collections = collections;
    }

    public Group(String id, String title, String description, String author, String createAt, String url, int likes, List<Link> links, List<User> members, List<Collection> collections) {
        super(id, title, description, author, createAt, url, likes);
        this.links = links;
        this.members = members;
        this.collections = collections;
    }

    protected Group(Parcel in) {
        in.readList(links, Link.class.getClassLoader());
        in.readList(members, User.class.getClassLoader());
        in.readList(collections, Collection.class.getClassLoader());
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };


    @JsonProperty("links")
    public List<Link> getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @JsonProperty("members")
    public List<User> getMembers() {
        return members;
    }

    @JsonProperty("members")
    public void setMembers(List<User> members) {
        this.members = members;
    }

    @JsonProperty("collections")
    public List<Collection> getCollections() {
        return collections;
    }

    @JsonProperty("collections")
    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(links);
        parcel.writeList(members);
        parcel.writeList(collections);
    }


}
