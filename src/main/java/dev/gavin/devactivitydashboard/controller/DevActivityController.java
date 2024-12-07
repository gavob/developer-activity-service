package dev.gavin.devactivitydashboard.controller;

import dev.gavin.devactivitydashboard.model.analysis.RepositoryAnalysis;
import dev.gavin.devactivitydashboard.model.github.RepositorySummary;
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

    /**
     * GET endpoint for searching GitHub repositories
     * @param searchTerm    String to search against repository names
     * @return List of Repository Summaries
     */
    @GetMapping("/search_repos/{search_term}")
    public List<RepositorySummary> searchRepos(@PathVariable("search_term") String searchTerm) {
        return gitHubApiService.searchPublicRepos(searchTerm);
    }

    /**
     * GET endpoint for returning repository data formatted for analysis
     * @param owner     The name of the repository owner
     * @param name      The name of the repository
     * @return Repository Analysis
     */
    @GetMapping("/repo_details/{owner}/{name}")
    public RepositoryAnalysis getRepositoryAnalysis(@PathVariable String owner, @PathVariable String name) {
        return gitHubApiService.getRepositoryAnalysis(owner, name);
    }
}
