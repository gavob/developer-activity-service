package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class RepositoryAnalysis {
    @JsonProperty("repository")
    private final Repository repository;
    @JsonProperty("commits")
    private final List<DateFrequency> commits = new ArrayList<>();
    @JsonProperty("comments")
    private final List<DateFrequency> comments = new ArrayList<>();
    @JsonProperty("pull_requests")
    private final List<DateFrequency> pullRequests = new ArrayList<>();
    @JsonProperty("issues")
    private final List<DateFrequency> issues = new ArrayList<>();
    @JsonProperty("releases")
    private final List<DateFrequency> releases = new ArrayList<>();
    @JsonProperty("pull_request_merge_times")
    private final List<MergeTime> pullRequestMergeTimes = new ArrayList<>();
    @JsonProperty("issue_close_times")
    private final List<IssueTime> issueCloseTimes = new ArrayList<>();
    @JsonProperty("active_contributors")
    private final List<Contributor> activeContributors = new ArrayList<>();
    @JsonProperty("issue_types")
    private final List<IssueType> issueTypes = new ArrayList<>();


    public RepositoryAnalysis(Repository repository) {
        this.repository = repository;
    }

    public Repository getRepository() {
        return repository;
    }

    public List<DateFrequency> getCommits() {
        return commits;
    }

    public List<DateFrequency> getComments() {
        return comments;
    }

    public List<DateFrequency> getPullRequests() {
        return pullRequests;
    }

    public List<DateFrequency> getIssues() {
        return issues;
    }

    public List<DateFrequency> getReleases() {
        return releases;
    }

    public List<MergeTime> getPullRequestMergeTimes() {
        return pullRequestMergeTimes;
    }

    public List<IssueTime> getIssueCloseTimes() {
        return issueCloseTimes;
    }

    public List<Contributor> getActiveContributors() {
        return activeContributors;
    }

    public List<IssueType> getIssueTypes() {
        return issueTypes;
    }
}
