package dev.gavin.devactivitydashboard.model;

public class CommitData {
    private final Committer committer;

    public CommitData(Committer committer) {
        this.committer = committer;
    }

    public Committer getCommitter() {
        return committer;
    }
}
