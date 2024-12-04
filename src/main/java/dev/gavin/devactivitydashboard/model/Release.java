package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Release {
    @JsonProperty("name")
    private final String name;
    @JsonProperty("author")
    private final User author;
    @JsonProperty("created_at")
    private final LocalDateTime createdAt;
    @JsonProperty("published_at")
    private final LocalDateTime publishedAt;

    public Release(String name, User author, LocalDateTime createdAt, LocalDateTime publishedAt) {
        this.name = name;
        this.author = author;
        this.createdAt = createdAt;
        this.publishedAt = publishedAt;
    }
}
