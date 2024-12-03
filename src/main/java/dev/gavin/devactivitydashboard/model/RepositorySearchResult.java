package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RepositorySearchResult {
    @JsonProperty("total_count")
    private final Integer totalCount;
    @JsonProperty("incomplete_results")
    private final Boolean incompleteResults;
    @JsonProperty("items")
    private final List<RepositorySummary> items;

    public RepositorySearchResult(Integer totalCount, Boolean incompleteResults, List<RepositorySummary> items) {
        this.totalCount = totalCount;
        this.incompleteResults = incompleteResults;
        this.items = items;
    }

    public List<RepositorySummary> getItems() {
        return items;
    }
}
