package com.ona.linkapp.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Link extends UrlElement implements Parcelable {

    private int clicked;
    private String shorten_url;
    private Boolean visibility;

    public Link(){

    }

    protected Link(Parcel in) {
        clicked = in.readInt();
        shorten_url = in.readString();
        visibility = in.readByte() != 0;
    }

    public static final Creator<Link> CREATOR = new Creator<Link>() {
        @Override
        public Link createFromParcel(Parcel in) {
            return new Link(in);
        }

        @Override
        public Link[] newArray(int size) {
            return new Link[size];
        }
    };

    public Link(String title, String description, String author, String createAt, int clicked, String url, String shorten_url, Boolean visibility) {
        super(title, description, author, createAt, url);
        this.clicked = clicked;
        this.shorten_url = shorten_url;
        this.visibility = visibility;
    }

    public Link(String title, String description,  String author, String createAt, int clicked) {
        super(title, description, author, createAt);
        this.clicked = clicked;
    }

    public Link(String id, String title, String description, String author, String createAt, int likes, int clicked, String url, String shorten_url, Boolean visibility) {
        super(id, title, description, author, createAt, url, likes);
        this.clicked = clicked;
        this.shorten_url = shorten_url;
        this.visibility = visibility;
    }

    @JsonProperty("cliked")
    public int getClicked() {
        return clicked;
    }

    @JsonProperty("cliked")
    public void setClicked(int clicked) {
        this.clicked = clicked;
    }


    @JsonProperty("visibility")
    public Boolean getVisibility() {
        return visibility;
    }

    @JsonProperty("visibility")
    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(clicked);
        parcel.writeString(shorten_url);
        parcel.writeByte((byte) (visibility ? 1 : 0));

    }
}
