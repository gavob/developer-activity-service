package dev.gavin.devactivitydashboard.model.github;

import java.util.List;

public class RepositorySearchResult {
    private final List<RepositorySummary> items;

    public RepositorySearchResult(List<RepositorySummary> items) {
        this.items = items;
    }

    public List<RepositorySummary> getItems() {
        return items;
    }
}
