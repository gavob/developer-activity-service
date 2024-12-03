package dev.gavin.devactivitydashboard.service;

import dev.gavin.devactivitydashboard.model.RepositorySummary;
import dev.gavin.devactivitydashboard.model.RepositorySearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Service
public class GitHubApiService {
    private static final String SEARCH_REPOSITORIES_PATH = "/search/repositories?per_page=%d&q=%s&sort=stars";
    private static final int PAGE_SIZE = 5;

    private final WebClient gitHubClient;

    @Autowired
    public GitHubApiService(WebClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    public List<RepositorySummary> searchPublicRepos(String searchTerm) {
        final RepositorySearchResult searchResult = gitHubClient.get()
                .uri(String.format(SEARCH_REPOSITORIES_PATH, PAGE_SIZE, searchTerm))
                .retrieve()
                .bodyToMono(RepositorySearchResult.class)
                .block();

        if (searchResult == null || searchResult.getItems() == null) {
            return Collections.emptyList();
        }

        return searchResult.getItems();
    }
}
