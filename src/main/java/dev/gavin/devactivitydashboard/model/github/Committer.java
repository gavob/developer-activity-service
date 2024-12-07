package dev.gavin.devactivitydashboard.model.github;

import java.time.LocalDateTime;

public class Committer {
    private final LocalDateTime date;

    public Committer(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
