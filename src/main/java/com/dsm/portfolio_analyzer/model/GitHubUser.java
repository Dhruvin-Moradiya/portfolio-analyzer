package com.dsm.portfolio_analyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubUser {

    @JsonProperty("login")
    private String username;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("html_url")
    private String htmlUrl;

    private String location;
    private String bio;
    private int followers;
    private int following;

    @JsonProperty("created_at")
    private String createdAt;

    public GitHubUser() {}

    public GitHubUser(String username, String avatarUrl, String htmlUrl, String location, String bio, int followers, int following, String createdAt) {
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.htmlUrl = htmlUrl;
        this.location = location;
        this.bio = bio;
        this.followers = followers;
        this.following = following;
        this.createdAt = createdAt;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getHtmlUrl() { return htmlUrl; }
    public void setHtmlUrl(String htmlUrl) { this.htmlUrl = htmlUrl; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public int getFollowers() { return followers; }
    public void setFollowers(int followers) { this.followers = followers; }

    public int getFollowing() { return following; }
    public void setFollowing(int following) { this.following = following; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
