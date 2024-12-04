package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class PullRequest {
    @JsonProperty("number")
    private final Integer number;
    @JsonProperty("user")
    private final User user;
    @JsonProperty("state")
    private final String state;
    @JsonProperty("created_at")
    private final LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private final LocalDateTime updatedAt;
    @JsonProperty("closed_at")
    private final LocalDateTime closedAt;
    @JsonProperty("merged_at")
    private final LocalDateTime mergedAt;

    public PullRequest(Integer number,
                       User user,
                       String state,
                       LocalDateTime createdAt,
                       LocalDateTime updatedAt,
                       LocalDateTime closedAt,
                       LocalDateTime mergedAt) {
        this.number = number;
        this.user = user;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.closedAt = closedAt;
        this.mergedAt = mergedAt;
    }
}
