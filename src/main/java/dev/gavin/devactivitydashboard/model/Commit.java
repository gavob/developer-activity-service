package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Commit {
    @JsonProperty("author")
    private final User author;
    @JsonProperty("commit")
    private final CommitData commit;

    public Commit(User author, CommitData commit) {
        this.author = author;
        this.commit = commit;
    }
}
