package com.dsm.portfolio_analyzer.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.HttpClientErrorException;

import com.dsm.portfolio_analyzer.model.GitHubRepo;
import com.dsm.portfolio_analyzer.model.GitHubUser;
import com.dsm.portfolio_analyzer.exception.UserNotFoundException;

@Service
public class GitHubService {
    private final RestTemplate restTemplate;

    public GitHubService() {
        this.restTemplate = new RestTemplate();
    }

    public GitHubUser getUser(String username) {
        String url = UriComponentsBuilder
            .fromUriString("https://api.github.com/users/" + username)
            .toUriString();
            
        try {
            return restTemplate.getForObject(url, GitHubUser.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User '" + username + "' not found");
        }
    }

    public List<GitHubRepo> getUserRepos(String username) {
        String url = UriComponentsBuilder
            .fromUriString("https://api.github.com/users/" + username + "/repos")
            .queryParam("per_page", 100)
            .toUriString();

        GitHubRepo[] repos = restTemplate.getForObject(url, GitHubRepo[].class);
        return Arrays.asList(repos != null ? repos : new GitHubRepo[0]);
    }
}
