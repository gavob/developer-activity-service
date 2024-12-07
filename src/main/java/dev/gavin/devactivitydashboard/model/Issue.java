package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class Issue {
    @JsonProperty("number")
    private final Integer number;
    @JsonProperty("created_at")
    private final LocalDateTime createdAt;
    @JsonProperty("closed_at")
    private final LocalDateTime closedAt;
    private final List<Label> labels;

    public Issue(Integer number, LocalDateTime createdAt, LocalDateTime closedAt, List<Label> labels) {
        this.number = number;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.labels = labels;
    }

    public Integer getNumber() {
        return number;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public List<Label> getLabels() {
        return labels;
    }
}
