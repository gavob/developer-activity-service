package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RepositoryDetail {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("description")
    private String description;
    @JsonProperty("open_issues")
    private Integer openIssues;
    @JsonProperty("watchers")
    private Integer watchers;
    @JsonProperty("forks")
    private Integer forks;
}
