package com.dsm.portfolio_analyzer.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.dsm.portfolio_analyzer.model.GitHubRepo;
import com.dsm.portfolio_analyzer.service.GitHubService;

@RestController
@RequestMapping("/api/github")
public class GitHubController {
    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/{username}")
    public List<GitHubRepo> getUserRepos(@PathVariable String username) {
        return gitHubService.getUserRepos(username);
    }
}
