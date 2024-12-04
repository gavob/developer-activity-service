package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommitData {
    @JsonProperty("committer")
    private final Committer committer;

    public CommitData(Committer committer) {
        this.committer = committer;
    }
}
