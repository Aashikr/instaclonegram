package com.instaclonegram.models;

public class User {
    private String id;
    private String username;
    private String name;
    private String description;
    private String url;
    private String link;
    private String email;
    private String privacy;
    private String followersCnt;
    private String followingCnt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getFollowersCnt() {
        return followersCnt;
    }

    public void setFollowersCnt(String followersCnt) {
        this.followersCnt = followersCnt;
    }

    public String getFollowingCnt() {
        return followingCnt;
    }

    public void setFollowingCnt(String followingCnt) {
        this.followingCnt = followingCnt;
    }

}
