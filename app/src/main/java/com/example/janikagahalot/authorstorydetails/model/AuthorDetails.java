package com.example.janikagahalot.authorstorydetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



/**
 * Created by janikagahalot on 12/13/15.
 */
public class AuthorDetails{
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("followers")
    @Expose
    private Integer followers;
    @SerializedName("following")
    @Expose
    private Integer following;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("handle")
    @Expose
    private String handle;
    @SerializedName("is_following")
    @Expose
    private Boolean isFollowing = false;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("verb")
    @Expose
    private String verb;
    @SerializedName("db")
    @Expose
    private String db;

    public String getSi() {
        return si;
    }

    public String getAbout() {
        return about;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Integer getFollowers() {
        return followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getHandle() {
        return handle;
    }

    public void setIsFollowing(Boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    public Boolean getIsFollowing() {
        return isFollowing;
    }

    public String getDescription() {
        return description;
    }

    public String getVerb() {
        return verb;
    }

    public String getDb() {
        return db;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getLikeFlag() {
        return likeFlag;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    @SerializedName("si")
    @Expose
    private String si;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("like_flag")
    @Expose
    private Boolean likeFlag;
    @SerializedName("likes_count")
    @Expose
    private Integer likesCount;
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;

}
