package dev.gavin.devactivitydashboard.model.github;

public class Commit {
    private final CommitData commit;

    public Commit(CommitData commit) {
        this.commit = commit;
    }

    public CommitData getCommit() {
        return commit;
    }
}
