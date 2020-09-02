package com.ona.linkapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFilter("shortFilter")
public class ShortenLink implements Parcelable {

    private String id;
    private Link link;
    private String hash;
    private String linkShorten;

    public ShortenLink(){}

    public ShortenLink(String id, Link link, String hash, String linkShorten) {
        this.id = id;
        this.link = link;
        this.hash = hash;
        this.linkShorten = linkShorten;
    }

    public ShortenLink(Link link, String hash, String linkShorten) {
        this.link = link;
        this.hash = hash;
        this.linkShorten = linkShorten;
    }

    protected ShortenLink(Parcel in) {
        id = in.readString();
        link = in.readParcelable(Link.class.getClassLoader());
        hash = in.readString();
        linkShorten = in.readString();
    }

    public static final Creator<ShortenLink> CREATOR = new Creator<ShortenLink>() {
        @Override
        public ShortenLink createFromParcel(Parcel in) {
            return new ShortenLink(in);
        }

        @Override
        public ShortenLink[] newArray(int size) {
            return new ShortenLink[size];
        }
    };

    @JsonProperty("url")
    public Link getLink() {
        return link;
    }

    @JsonProperty("url")
    public void setLink(Link link) {
        this.link = link;
    }

    @JsonProperty("hash")
    public String getHash() {
        return hash;
    }

    @JsonProperty("hash")
    public void setHash(String hash) {
        this.hash = hash;
    }

    @JsonProperty("shortenUrl")
    public String getLinkShorten() {
        return linkShorten;
    }

    @JsonProperty("shortenUrl")
    public void setLinkShorten(String linkShorten) {
        this.linkShorten = linkShorten;
    }

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeParcelable(link, i);
        parcel.writeString(hash);
        parcel.writeString(linkShorten);
    }
}
