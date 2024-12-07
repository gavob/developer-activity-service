package dev.gavin.devactivitydashboard.model.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Repository {
    @JsonProperty("id")
    private final Integer id;
    @JsonProperty("full_name")
    private final String fullName;
    @JsonProperty("html_url")
    private final String htmlUrl;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("open_issues")
    private final Integer openIssues;
    @JsonProperty("watchers")
    private final Integer watchers;
    @JsonProperty("forks")
    private final Integer forks;

    public Repository(Integer id, String fullName, String htmlUrl, String description, Integer openIssues, Integer watchers, Integer forks) {
        this.id = id;
        this.fullName = fullName;
        this.htmlUrl = htmlUrl;
        this.description = description;
        this.openIssues = openIssues;
        this.watchers = watchers;
        this.forks = forks;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public Integer getOpenIssues() {
        return openIssues;
    }

    public Integer getWatchers() {
        return watchers;
    }

    public Integer getForks() {
        return forks;
    }
}
