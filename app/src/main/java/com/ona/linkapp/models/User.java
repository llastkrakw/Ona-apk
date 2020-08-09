package com.ona.linkapp.models;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import androidx.databinding.BindingAdapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Parcelable {

    private String id;
    private String username;
    private String password;
    private String email;
    private List<Collection> collections;
    private List<Link> links;
    private List<Group> groups;
    private Date createAt;

    public User(){

    }

    public User(String username, String password, String email, List<Collection> collections, List<Link> links, List<Group> groups, Date createAt) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.collections = collections;
        this.links = links;
        this.groups = groups;
        this.createAt = createAt;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.links = new ArrayList<>();
        this.collections = new ArrayList<>();
        this.groups = new ArrayList<>();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy:HH:mm:ss");
        this.createAt = new Date();
    }

    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        password = in.readString();
        email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("collections")
    public List<Collection> getCollections() {
        return collections;
    }

    @JsonProperty("collections")
    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    @JsonProperty("links")
    public List<Link> getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @JsonProperty("groups")
    public List<Group> getGroups() {
        return groups;
    }

    @JsonProperty("groups")
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @JsonProperty("createAt")
    public Date getCreateAt() {
        return createAt;
    }

    @JsonProperty("createAt")
    public void setCreatedAt(Date createdAt) {
        this.createAt = createdAt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(email);
    }

    @BindingAdapter({"android:src"})
    public static void LoadImage(CircularImageView view, String email){

        Picasso.get().
                load((Uri.parse("https://unavatar.now.sh/").buildUpon().appendPath(email).toString()))
        .into(view);

    }
}
