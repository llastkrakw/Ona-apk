package com.ona.linkapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UrlElement implements Parcelable {


    private String _id;
    private String title;
    private String description;
    private String author;
    private String createAt;
    private String url;
    private int  likes;

    public UrlElement(){

    }

    public UrlElement(String title, String description, String author, String createdAt, String url) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.createAt = createdAt;
        this.url = url;
    }

    public UrlElement(String title, String description, String author){

        this.title = title;
        this.description = description;
        this.author = author;

    }


    public UrlElement(String id, String title, String description, String author, String createdAt, String url, int likes) {
        this._id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.createAt = createdAt;
        this.url = url;
        this.likes = likes;
    }

    protected UrlElement(Parcel in) {
        _id = in.readString();
        title = in.readString();
        description = in.readString();
        author = in.readString();
        createAt = in.readString();
        url = in.readString();
        likes = in.readInt();
    }

    public static final Creator<UrlElement> CREATOR = new Creator<UrlElement>() {
        @Override
        public UrlElement createFromParcel(Parcel in) {
            return new UrlElement(in);
        }

        @Override
        public UrlElement[] newArray(int size) {
            return new UrlElement[size];
        }
    };

    public UrlElement(String title, String description, String url, String author) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.url = url;
    }

    @JsonProperty("_id")
    public String getId() {
        return _id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this._id = id;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("author")
    public String getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonProperty("createAt")
    public String getCreateAt() {
        return createAt;
    }

    @JsonProperty("createAt")
    public void setCreateAt(String createdAt) {
        this.createAt = createdAt;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("likes")
    public int getLikes() {
        return likes;
    }

    @JsonProperty("likes")
    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(author);
        parcel.writeString(createAt);
        parcel.writeString(url);
        parcel.writeInt(likes);
    }
}
