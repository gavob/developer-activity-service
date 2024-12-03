package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RepositorySummary {
    @JsonProperty("id")
    private final Integer id;
    @JsonProperty("full_name")
    private final String fullName;
    @JsonProperty("html_url")
    private final String htmlUrl;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("language")
    private final String language;

    public RepositorySummary(Integer id, String fullName, String htmlUrl, String description, String language) {
        this.id = id;
        this.fullName = fullName;
        this.htmlUrl = htmlUrl;
        this.description = description;
        this.language = language;
    }
}
