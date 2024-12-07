package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Release {
    @JsonProperty("published_at")
    private final LocalDateTime publishedAt;

    public Release(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }
}
