package dev.gavin.devactivitydashboard.controller;

import dev.gavin.devactivitydashboard.model.RepositoryAnalysis;
import dev.gavin.devactivitydashboard.model.RepositorySummary;
import dev.gavin.devactivitydashboard.service.GitHubApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/dev_activity")
public class DevActivityController {
    private final GitHubApiService gitHubApiService;

    @Autowired
    public DevActivityController(GitHubApiService gitHubApiService) {
        this.gitHubApiService = gitHubApiService;
    }

    @GetMapping("/search_repos/{search_term}")
    public List<RepositorySummary> searchRepos(@PathVariable("search_term") String searchTerm) {
        return gitHubApiService.searchPublicRepos(searchTerm);
    }

    @GetMapping("/repo_details/{owner}/{name}")
    public RepositoryAnalysis getRepositoryAnalysis(@PathVariable String owner, @PathVariable String name) {
        return gitHubApiService.getRepositoryAnalysis(owner, name);
    }
}
