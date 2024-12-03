package dev.gavin.devactivitydashboard.controller;

import dev.gavin.devactivitydashboard.model.RepositorySummary;
import dev.gavin.devactivitydashboard.service.GitHubApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/dev_activity")
@RestController
public class DevActivityController {
    private final GitHubApiService gitHubApiService;

    @Autowired
    public DevActivityController(GitHubApiService gitHubApiService) {
        this.gitHubApiService = gitHubApiService;
    }

    @GetMapping("/search_repos/{query}")
    public List<RepositorySummary> searchRepos(@PathVariable String query) {
        return gitHubApiService.searchPublicRepos(query);
    }
}
