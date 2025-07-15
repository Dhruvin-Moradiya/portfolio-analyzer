package com.dsm.portfolio_analyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubRepo {
    private String name;

    @JsonProperty("html_url")
    private String htmlUrl;
    private String description;
    private String language;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("stargazers_count")
    private int stars;

    public GitHubRepo() {}

    public GitHubRepo(String name, String htmlUrl, String description, String language, int stars, String createdAt) {
        this.name = name;
        this.htmlUrl = htmlUrl;
        this.description = description;
        this.language = language;
        this.stars = stars;
        this.createdAt = createdAt;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getHtmlUrl() { return htmlUrl; }
    public void setHtmlUrl(String htmlUrl) { this.htmlUrl = htmlUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public int getStars() { return stars; }
    public void setStars(int stars) { this.stars = stars; }
}
