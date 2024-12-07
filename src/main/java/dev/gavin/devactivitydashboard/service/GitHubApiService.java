package dev.gavin.devactivitydashboard.service;

import dev.gavin.devactivitydashboard.helper.AnalysisHelper;
import dev.gavin.devactivitydashboard.model.analysis.RepositoryAnalysis;
import dev.gavin.devactivitydashboard.model.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Collections;

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

    /**
     * Queries GitHub API for list of repositories which contain the
     * search term parameter in their name
     * @param searchTerm   String containing the search term
     * @return List of RepositorySummary
     */
    public List<RepositorySummary> searchPublicRepos(String searchTerm) {
        final RepositorySearchResult searchResult = queryApiObjectData(String.format(SEARCH_REPOSITORIES_PATH, SEARCH_PAGE_SIZE, searchTerm),
                RepositorySearchResult.class);

        if (searchResult == null || searchResult.getItems() == null) {
            return Collections.emptyList();
        }

        return searchResult.getItems();
    }

    /**
     * Makes multiple API calls to GitHub getting repository activity data and returns
     * the data formatted for analysis
     * @param owner     String representing repo owners name
     * @param name      String representing repo name
     * @return RepositoryAnalysis instance
     */
    public RepositoryAnalysis getRepositoryAnalysis(String owner, String name) {
        final Repository repository = queryApiObjectData(String.format(REPOSITORY_PATH, owner, name), Repository.class);
        final List<Commit> commits = queryApiListData(String.format(COMMITS_PATH, owner, name, PAGE_SIZE), Commit.class);
        final List<Comment> comments = queryApiListData(String.format(COMMENTS_PATH, owner, name, PAGE_SIZE), Comment.class);
        final List<Release> releases = queryApiListData(String.format(RELEASES_PATH, owner, name, PAGE_SIZE), Release.class);
        final List<PullRequest> pullRequests = queryApiListData(String.format(PULL_REQUESTS_PATH, owner, name, PAGE_SIZE), PullRequest.class);
        final List<Issue> issues = queryApiListData(String.format(ISSUES_PATH, owner, name, PAGE_SIZE), Issue.class);
        final List<PullRequest> closedPullRequests = queryApiListData(String.format(CLOSED_PULL_REQUESTS_PATH, owner, name, PAGE_SIZE), PullRequest.class);
        final List<Issue> closedIssues = queryApiListData(String.format(CLOSED_ISSUES_PATH, owner, name, PAGE_SIZE), Issue.class);

        return AnalysisHelper.buildRepositoryAnalysis(repository, commits, comments, pullRequests, releases, issues, closedPullRequests, closedIssues);
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
}
