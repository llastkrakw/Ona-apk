package com.ona.linkapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ShortenLink implements Parcelable {

    private Link link;
    private String hash;
    private String linkShorten;

    public ShortenLink(){}

    public ShortenLink(Link link, String hash, String linkShorten) {
        this.link = link;
        this.hash = hash;
        this.linkShorten = linkShorten;
    }

    protected ShortenLink(Parcel in) {
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

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getLinkShorten() {
        return linkShorten;
    }

    public void setLinkShorten(String linkShorten) {
        this.linkShorten = linkShorten;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(link, i);
        parcel.writeString(hash);
        parcel.writeString(linkShorten);
    }
}
