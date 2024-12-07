package dev.gavin.devactivitydashboard.model.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.gavin.devactivitydashboard.model.github.Repository;

import java.util.ArrayList;
import java.util.List;

public class RepositoryAnalysis {
    @JsonProperty("repository")
    private final Repository repository;
    @JsonProperty("commits")
    private final List<DayCounter> commits = new ArrayList<>();
    @JsonProperty("comments")
    private final List<DayCounter> comments = new ArrayList<>();
    @JsonProperty("pull_requests")
    private final List<DayCounter> pullRequests = new ArrayList<>();
    @JsonProperty("issues")
    private final List<DayCounter> issues = new ArrayList<>();
    @JsonProperty("releases")
    private final List<DayCounter> releases = new ArrayList<>();
    @JsonProperty("pull_request_merge_times")
    private final List<TimeLength> pullRequestMergeTimes = new ArrayList<>();
    @JsonProperty("issue_close_times")
    private final List<TimeLength> issueCloseTimes = new ArrayList<>();
    @JsonProperty("active_contributors")
    private final List<NameValue> activeContributors = new ArrayList<>();
    @JsonProperty("issue_types")
    private final List<NameValue> issueTypes = new ArrayList<>();


    public RepositoryAnalysis(Repository repository) {
        this.repository = repository;
    }

    public Repository getRepository() {
        return repository;
    }

    public List<DayCounter> getCommits() {
        return commits;
    }

    public List<DayCounter> getComments() {
        return comments;
    }

    public List<DayCounter> getPullRequests() {
        return pullRequests;
    }

    public List<DayCounter> getIssues() {
        return issues;
    }

    public List<DayCounter> getReleases() {
        return releases;
    }

    public List<TimeLength> getPullRequestMergeTimes() {
        return pullRequestMergeTimes;
    }

    public List<TimeLength> getIssueCloseTimes() {
        return issueCloseTimes;
    }

    public List<NameValue> getActiveContributors() {
        return activeContributors;
    }

    public List<NameValue> getIssueTypes() {
        return issueTypes;
    }
}
