package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Comment {
    @JsonProperty("user")
    private final User user;
    @JsonProperty("created_at")
    private final LocalDateTime createdAt;

    public Comment(User user, LocalDateTime createdAt) {
        this.user = user;
        this.createdAt = createdAt;
    }
}
