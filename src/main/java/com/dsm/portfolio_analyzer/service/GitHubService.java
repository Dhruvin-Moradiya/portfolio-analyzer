package com.dsm.portfolio_analyzer.service;

import java.util.Arrays;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

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

    public List<GitHubRepo> getUserRepos(String username, String sort, String order, String language) {
        List<GitHubRepo> repos = getUserRepos(username);

        // Filter by language
        if (language != null && !language.isBlank()) {
            repos = repos.stream()
                .filter(r -> r.getLanguage() != null && r.getLanguage().equalsIgnoreCase(language))
                .collect(Collectors.toList());
        }

        // Sort
        if (sort != null && !sort.isBlank()) {
            Comparator<GitHubRepo> comparator = null;
            switch (sort) {
                case "stars":
                    comparator = Comparator.comparingInt(GitHubRepo::getStars);
                    break;
                case "createdAt":
                    comparator = Comparator.comparing(GitHubRepo::getCreatedAt, Comparator.nullsLast(String::compareTo));
                    break;
                case "name":
                    comparator = Comparator.comparing(GitHubRepo::getName, Comparator.nullsLast(String::compareToIgnoreCase));
                    break;
            }
            if (comparator != null) {
                if (order != null && order.equalsIgnoreCase("asc")) {
                    repos = repos.stream().sorted(comparator).collect(Collectors.toList());
                } else {
                    repos = repos.stream().sorted(comparator.reversed()).collect(Collectors.toList());
                }
            }
        }
        return repos;
    }

    public Map<String, Object> getRepoStats(List<GitHubRepo> repos) {
        Map<String, Integer> byLanguage = new HashMap<>();
        for (GitHubRepo repo : repos) {
            String lang = repo.getLanguage();
            if (lang != null && !lang.isBlank()) {
                byLanguage.put(lang, byLanguage.getOrDefault(lang, 0) + 1);
            }
        }
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRepos", repos.size());
        stats.put("byLanguage", byLanguage);
        return stats;
    }
}
