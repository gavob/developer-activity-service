package dev.gavin.devactivitydashboard.helper;

import dev.gavin.devactivitydashboard.model.analysis.RepositoryAnalysis;
import dev.gavin.devactivitydashboard.model.github.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestAnalysisHelper {

    @Test
    void buildRepositoryAnalysisBuildsDayCounterDataCorrectly() {
        Repository repository = new Repository(1, "", "", "", 0, 0, 0);
        List<Commit> commits = List.of(new Commit(new CommitData(new Committer(LocalDateTime.of(2024, 6, 6, 0, 0)))),
                new Commit(new CommitData(new Committer(LocalDateTime.of(2024, 6, 6, 0, 0)))),
                new Commit(new CommitData(new Committer(LocalDateTime.of(2024, 6, 7, 0, 0)))));
        List<Comment> comments = List.of(new Comment(LocalDateTime.of(2024, 6, 6, 0, 0)),
                new Comment(LocalDateTime.of(2024, 6, 6, 0, 0)),
                new Comment(LocalDateTime.of(2024, 4, 6, 0, 0)));
        List<PullRequest> pullRequests = List.of(new PullRequest(0, null, LocalDateTime.of(2024, 6, 6, 0, 0), null),
                new PullRequest(0, null, LocalDateTime.of(2024, 6, 6, 0, 0), null),
                new PullRequest(0, null, LocalDateTime.of(2024, 6, 9, 0, 0), null));
        List<Release> releases = List.of(new Release(LocalDateTime.of(2024, 6, 6, 0, 0)),
                new Release(LocalDateTime.of(2024, 6, 6, 0, 0)),
                new Release(LocalDateTime.of(2024, 6, 6, 0, 0)));
        List<Issue> issues = List.of(new Issue(0, LocalDateTime.of(2024, 6, 6, 0, 0), null, new ArrayList<>()),
                new Issue(0, LocalDateTime.of(2024, 6, 7, 0, 0), null, new ArrayList<>()),
                new Issue(0, LocalDateTime.of(2024, 6, 8, 0, 0), null, new ArrayList<>()));

        final RepositoryAnalysis result = AnalysisHelper.buildRepositoryAnalysis(repository, commits, comments, pullRequests, releases, issues, List.of(), List.of());

        assertEquals(2, result.getCommits().size());
        assertEquals(2, result.getComments().size());
        assertEquals(2, result.getPullRequests().size());
        assertEquals(1, result.getReleases().size());
        assertEquals(3, result.getIssues().size());
    }

    @Test
    void buildRepositoryAnalysisBuildsNameValueDataCorrectly() {
        Repository repository = new Repository(1, "", "", "", 0, 0, 0);
        List<Issue> issues = List.of(new Issue(0, LocalDateTime.of(2024, 6, 7, 0, 0), null, List.of(new Label("label1"))),
                new Issue(0, LocalDateTime.of(2024, 6, 7, 0, 0), null, List.of(new Label("label1"), new Label("label3"))),
                new Issue(0, LocalDateTime.of(2024, 6, 7, 0, 0), null, List.of(new Label("label1"), new Label("label2"))));
        List<PullRequest> closedPullRequests = List.of(new PullRequest(0, new User("user1"), null, null),
                new PullRequest(0, new User("user2"), null, null),
                new PullRequest(0, new User("user1"), null, null));

        final RepositoryAnalysis result = AnalysisHelper.buildRepositoryAnalysis(repository, List.of(), List.of(), List.of(), List.of(), issues, closedPullRequests, List.of());

        assertEquals(2, result.getActiveContributors().size());
        assertTrue(result.getActiveContributors().stream().anyMatch(contributor -> contributor.getName().equals("user1")));
        assertTrue(result.getActiveContributors().stream().anyMatch(contributor -> contributor.getName().equals("user2")));
        assertEquals(3, result.getIssueTypes().size());
        assertTrue(result.getIssueTypes().stream().anyMatch(issue -> issue.getName().equals("label1")));
        assertTrue(result.getIssueTypes().stream().anyMatch(issue -> issue.getName().equals("label2")));
        assertTrue(result.getIssueTypes().stream().anyMatch(issue -> issue.getName().equals("label3")));
    }



    @Test
    void buildRepositoryAnalysisBuildsTimeLengthDataCorrectly() {
        Repository repository = new Repository(1, "", "", "", 0, 0, 0);
        List<Issue> closedIssues = List.of(new Issue(1, LocalDateTime.of(2024, 6, 6, 0, 0), LocalDateTime.of(2024, 6, 6, 1, 0), null),
                new Issue(2, LocalDateTime.of(2024, 6, 6, 0, 0), LocalDateTime.of(2024, 6, 6, 2, 0), null),
                new Issue(3, LocalDateTime.of(2024, 6, 6, 0, 0), LocalDateTime.of(2024, 6, 6, 3, 0), null));
        List<PullRequest> closedPullRequests = List.of(new PullRequest(1, new User("user"), LocalDateTime.of(2024, 6, 6, 0, 0), LocalDateTime.of(2024, 6, 6, 2, 0)),
                new PullRequest(2, new User("user"), LocalDateTime.of(2024, 6, 6, 0, 0), LocalDateTime.of(2024, 6, 6, 4, 0)),
                new PullRequest(3, new User("user"), LocalDateTime.of(2024, 6, 6, 0, 0), LocalDateTime.of(2024, 6, 6, 6, 0)));

        final RepositoryAnalysis result = AnalysisHelper.buildRepositoryAnalysis(repository, List.of(), List.of(), List.of(), List.of(), List.of(), closedPullRequests, closedIssues);

        assertEquals(3, result.getPullRequestMergeTimes().size());
        assertEquals(2, result.getPullRequestMergeTimes().get(0).getTime());
        assertEquals(4, result.getPullRequestMergeTimes().get(1).getTime());
        assertEquals(6, result.getPullRequestMergeTimes().get(2).getTime());
        assertEquals(3, result.getIssueCloseTimes().size());
        assertEquals(1, result.getIssueCloseTimes().get(0).getTime());
        assertEquals(2, result.getIssueCloseTimes().get(1).getTime());
        assertEquals(3, result.getIssueCloseTimes().get(2).getTime());
    }
}