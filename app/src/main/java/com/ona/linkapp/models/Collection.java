package com.ona.linkapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFilter("colFilter")
public class  Collection extends UrlElement implements Parcelable {

    private List<Link> links;
    private Boolean visibility;


    public Collection(){}


    public Collection(String title, String description, String author, String createAt, String url, List<Link> links, Boolean visibility) {
        super(title, description, author, createAt, url);
        this.links = links;
        this.visibility = visibility;
    }

    public Collection(String title, String description, String author, String createAt, List<Link> links) {
        super(title, description, author, createAt);
        this.links = links;
    }

    public Collection(String title, String description, String author){
        super(title, description, author);
    }

    public Collection(String id, String title, String description, String author, String createAt, String url, int likes, List<Link> links, Boolean visibility) {
        super(id, title, description, author, createAt, url, likes);
        this.links = links;
        this.visibility = visibility;
    }


    protected Collection(Parcel in) {
        super(in);
        links = in.createTypedArrayList(Link.CREATOR);
        byte tmpVisibility = in.readByte();
        visibility = tmpVisibility == 0 ? null : tmpVisibility == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(links);
        dest.writeByte((byte) (visibility == null ? 0 : visibility ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Collection> CREATOR = new Creator<Collection>() {
        @Override
        public Collection createFromParcel(Parcel in) {
            return new Collection(in);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
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

    @JsonProperty("visibility")
    public Boolean getVisibility() {
        return visibility;
    }

    @JsonProperty("visibility")
    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }


}
