package dev.gavin.devactivitydashboard.service;

import dev.gavin.devactivitydashboard.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GitHubApiService {
    private static final String REPOSITORY_PATH = "/repos/%s/%s";
    private static final String COMMITS_PATH = "/repos/%s/%s/commits?per_page=%d";
    private static final String COMMENTS_PATH = "/repos/%s/%s/pulls/comments?per_page=%d&sort=created&direction=desc";
    private static final String RELEASES_PATH = "/repos/%s/%s/releases?per_page=%d";
    private static final String PULL_REQUESTS_PATH = "/repos/%s/%s/pulls?per_page=%d&state=all";
    private static final String ISSUES_PATH = "/repos/%s/%s/issues?per_page=%d&state=all";
    private static final String CLOSED_PULL_REQUESTS_PATH = "/repos/%s/%s/pulls?per_page=%d&state=closed";
    private static final String CLOSED_ISSUES_PATH = "/repos/%s/%s/issues?per_page=%d&state=closed";
    private static final String SEARCH_REPOSITORIES_PATH = "/search/repositories?per_page=%d&q=%s&sort=stars";
    private static final int SEARCH_PAGE_SIZE = 5;
    private static final int PAGE_SIZE = 100;

    private final WebClient gitHubClient;

    @Autowired
    public GitHubApiService(WebClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    public List<RepositorySummary> searchPublicRepos(String searchTerm) {
        final RepositorySearchResult searchResult = queryApiObjectData(String.format(SEARCH_REPOSITORIES_PATH, SEARCH_PAGE_SIZE, searchTerm),
                RepositorySearchResult.class);

        if (searchResult == null || searchResult.getItems() == null) {
            return Collections.emptyList();
        }

        return searchResult.getItems();
    }

    public RepositoryAnalysis getRepositoryAnalysis(String owner, String name) {
        final Repository repository = queryApiObjectData(String.format(REPOSITORY_PATH, owner, name), Repository.class);
        final List<Commit> commits = queryApiListData(String.format(COMMITS_PATH, owner, name, PAGE_SIZE), Commit.class);
        final List<Comment> comments = queryApiListData(String.format(COMMENTS_PATH, owner, name, PAGE_SIZE), Comment.class);
        final List<Release> releases = queryApiListData(String.format(RELEASES_PATH, owner, name, PAGE_SIZE), Release.class);
        final List<PullRequest> pullRequests = queryApiListData(String.format(PULL_REQUESTS_PATH, owner, name, PAGE_SIZE), PullRequest.class);
        final List<Issue> issues = queryApiListData(String.format(ISSUES_PATH, owner, name, PAGE_SIZE), Issue.class);
        final List<PullRequest> closedPullRequests = queryApiListData(String.format(CLOSED_PULL_REQUESTS_PATH, owner, name, PAGE_SIZE), PullRequest.class);
        final List<Issue> closedIssues = queryApiListData(String.format(CLOSED_ISSUES_PATH, owner, name, PAGE_SIZE), Issue.class);

        return buildRepositoryAnalysis(repository, commits, comments, pullRequests, releases, issues, closedPullRequests, closedIssues);
    }

    private <T> T queryApiObjectData(String uri, Class<T> classType) {
        return gitHubClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(classType)
                .block();
    }

    private <T> List<T> queryApiListData(String uri, Class<T> classType) {
        return gitHubClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(classType)
                .collectList()
                .block();
    }

    private RepositoryAnalysis buildRepositoryAnalysis(Repository repository,
                                                       List<Commit> commits,
                                                       List<Comment> comments,
                                                       List<PullRequest> pullRequests,
                                                       List<Release> releases,
                                                       List<Issue> issues,
                                                       List<PullRequest> closedPullRequests,
                                                       List<Issue> closedIssues) {
        final RepositoryAnalysis repositoryAnalysis = new RepositoryAnalysis(repository);

        final Map<LocalDate, Integer> commitFrequencies = commits.stream()
                .collect(Collectors.toMap(commit -> commit.getCommit().getCommitter().getDate().toLocalDate(),
                        (counter) -> 1, Integer::sum));
        repositoryAnalysis.getCommits().addAll(commitFrequencies.keySet().stream()
                .map(date -> new DateFrequency(date, commitFrequencies.get(date)))
                .sorted(Comparator.comparing(DateFrequency::getDate))
                .toList());

        final Map<LocalDate, Integer> commentFrequencies = comments.stream()
                .collect(Collectors.toMap(comment -> comment.getCreatedAt().toLocalDate(),
                        (counter) -> 1, Integer::sum));
        repositoryAnalysis.getComments().addAll(commentFrequencies.keySet().stream()
                .map(date -> new DateFrequency(date, commentFrequencies.get(date)))
                .sorted(Comparator.comparing(DateFrequency::getDate))
                .toList());

        final Map<LocalDate, Integer> pullRequestFrequencies = pullRequests.stream()
                .collect(Collectors.toMap(pullRequest -> pullRequest.getCreatedAt().toLocalDate(),
                        (counter) -> 1, Integer::sum));
        repositoryAnalysis.getPullRequests().addAll(pullRequestFrequencies.keySet().stream()
                .map(date -> new DateFrequency(date, pullRequestFrequencies.get(date)))
                .sorted(Comparator.comparing(DateFrequency::getDate))
                .toList());

        final Map<LocalDate, Integer> issueFrequencies = issues.stream()
                .collect(Collectors.toMap(issue -> issue.getCreatedAt().toLocalDate(),
                        (counter) -> 1, Integer::sum));
        repositoryAnalysis.getIssues().addAll(issueFrequencies.keySet().stream()
                .map(date -> new DateFrequency(date, issueFrequencies.get(date)))
                .sorted(Comparator.comparing(DateFrequency::getDate))
                .toList());

        final Map<LocalDate, Integer> releaseFrequencies = releases.stream()
                .collect(Collectors.toMap(release -> release.getPublishedAt().toLocalDate(),
                        (counter) -> 1, Integer::sum));
        repositoryAnalysis.getReleases().addAll(releaseFrequencies.keySet().stream()
                .map(date -> new DateFrequency(date, releaseFrequencies.get(date)))
                .sorted(Comparator.comparing(DateFrequency::getDate))
                .toList());

        repositoryAnalysis.getPullRequestMergeTimes().addAll(closedPullRequests.stream()
                .filter(pull -> pull.getMergedAt() != null)
                .sorted(Comparator.comparing(PullRequest::getNumber))
                .map(pull -> new MergeTime(pull.getNumber(), ChronoUnit.HOURS.between(pull.getCreatedAt(), pull.getMergedAt())))
                .toList());

        repositoryAnalysis.getIssueCloseTimes().addAll(closedIssues.stream()
                .sorted(Comparator.comparing(Issue::getNumber))
                .map(issue -> new IssueTime(issue.getNumber(), ChronoUnit.HOURS.between(issue.getCreatedAt(), issue.getClosedAt())))
                .toList());

        final Map<String, Integer> activeContributors = closedPullRequests.stream()
                .map(pullRequest -> pullRequest.getUser().getLogin())
                .collect(Collectors.toMap(login -> login, (counter) -> 1, Integer::sum));

        repositoryAnalysis.getActiveContributors().addAll(activeContributors.keySet().stream()
                .sorted((n1, n2) -> activeContributors.get(n2).compareTo(activeContributors.get(n1)))
                .limit(15)
                .map(name -> new Contributor(name, activeContributors.get(name)))
                .toList());

        final Map<String, Integer> issueTypes = issues.stream()
                .flatMap(issue -> issue.getLabels().stream())
                .map(Label::getName)
                .collect(Collectors.toMap(label -> label, (counter) -> 1, Integer::sum));

        repositoryAnalysis.getIssueTypes().addAll(issueTypes.keySet().stream()
                .sorted((l1, l2) -> issueTypes.get(l2).compareTo(issueTypes.get(l1)))
                .limit(15)
                .map(label -> new IssueType(label, issueTypes.get(label)))
                .toList());

        return repositoryAnalysis;
    }
}
