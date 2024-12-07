package dev.gavin.devactivitydashboard.model.github;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class PullRequest {
    @JsonProperty("number")
    private final Integer number;
    private final User user;
    @JsonProperty("created_at")
    private final LocalDateTime createdAt;
    @JsonProperty("merged_at")
    private final LocalDateTime mergedAt;

    public PullRequest(Integer number, User user, LocalDateTime createdAt, LocalDateTime mergedAt) {
        this.number = number;
        this.user = user;
        this.createdAt = createdAt;
        this.mergedAt = mergedAt;
    }

    public Integer getNumber() {
        return number;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getMergedAt() {
        return mergedAt;
    }
}
