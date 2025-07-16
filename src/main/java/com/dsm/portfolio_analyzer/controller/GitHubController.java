package com.dsm.portfolio_analyzer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.dsm.portfolio_analyzer.model.GitHubRepo;
import com.dsm.portfolio_analyzer.model.GitHubUser;
import com.dsm.portfolio_analyzer.exception.UserNotFoundException;
import com.dsm.portfolio_analyzer.service.GitHubService;

@RestController
@ControllerAdvice
@RequestMapping("/api/github")
public class GitHubController {
    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException e) {
        return e.getMessage();
    }

    @GetMapping("/{username}")
    public GitHubUser getUser(@PathVariable String username) {
        return gitHubService.getUser(username);
    }

    @GetMapping("/{username}/repos")
    public List<GitHubRepo> getUserRepos(@PathVariable String username) {
        return gitHubService.getUserRepos(username);
    }
}
