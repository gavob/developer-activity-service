package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Committer {
    @JsonProperty("date")
    private final LocalDateTime date;

    public Committer(LocalDateTime date) {
        this.date = date;
    }
}
