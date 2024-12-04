package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Issue {
    @JsonProperty("state")
    private final String state;
    @JsonProperty("created_at")
    private final LocalDateTime createdAt;
    @JsonProperty("closed_at")
    private final LocalDateTime closedAt;

    public Issue(String state, LocalDateTime createdAt, LocalDateTime closedAt) {
        this.state = state;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
    }
}
