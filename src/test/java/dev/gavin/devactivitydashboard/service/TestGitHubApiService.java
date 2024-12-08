package dev.gavin.devactivitydashboard.service;

import dev.gavin.devactivitydashboard.model.github.RepositorySearchResult;
import dev.gavin.devactivitydashboard.model.github.RepositorySummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestGitHubApiService {
    private WebClient.ResponseSpec responseSpec;
    private GitHubApiService gitHubApiService;

    @BeforeEach
    void setUp() {
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient webClient = mock(WebClient.class);

        responseSpec = mock(WebClient.ResponseSpec.class);
        gitHubApiService = new GitHubApiService(webClient);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void testSearchPublicReposReturnsItems() {
        RepositorySummary repositorySummary1 = new RepositorySummary(1, "repo 1", "", "");
        RepositorySummary repositorySummary2 = new RepositorySummary(2, "repo 2", "", "");
        RepositorySearchResult searchResult = new RepositorySearchResult(List.of(repositorySummary1, repositorySummary2));

        when(responseSpec.bodyToMono(RepositorySearchResult.class)).thenReturn(Mono.just(searchResult));

        List<RepositorySummary> result = gitHubApiService.searchPublicRepos("search term");

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testSearchPublicReposReturnsEmptyListOnNullResults() {
        RepositorySearchResult searchResult = new RepositorySearchResult(null);

        when(responseSpec.bodyToMono(RepositorySearchResult.class)).thenReturn(Mono.just(searchResult));

        List<RepositorySummary> result = gitHubApiService.searchPublicRepos("search term");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        when(responseSpec.bodyToMono(RepositorySearchResult.class)).thenReturn(Mono.empty());

        result = gitHubApiService.searchPublicRepos("search term");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}