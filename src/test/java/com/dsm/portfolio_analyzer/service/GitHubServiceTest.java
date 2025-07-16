package com.dsm.portfolio_analyzer.service;

import com.dsm.portfolio_analyzer.exception.UserNotFoundException;
import com.dsm.portfolio_analyzer.model.GitHubRepo;
import com.dsm.portfolio_analyzer.model.GitHubUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GitHubServiceTest {
    private RestTemplate restTemplate;
    private GitHubService gitHubService;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        gitHubService = new GitHubService(restTemplate);
    }

    @Test
    void getUser_returnsUser() {
        GitHubUser user = new GitHubUser();
        when(restTemplate.getForObject(anyString(), eq(GitHubUser.class))).thenReturn(user);
        assertEquals(user, gitHubService.getUser("testuser"));
    }

    @Test
    void getUser_throwsUserNotFound() {
        when(restTemplate.getForObject(anyString(), eq(GitHubUser.class)))
                .thenThrow(HttpClientErrorException.NotFound.class);
        assertThrows(UserNotFoundException.class, () -> gitHubService.getUser("nouser"));
    }

    @Test
    void getUserRepos_returnsRepos() {
        GitHubRepo[] repos = { new GitHubRepo("repo1", null, null, "Java", 1, null) };
        when(restTemplate.getForObject(anyString(), eq(GitHubRepo[].class))).thenReturn(repos);
        List<GitHubRepo> result = gitHubService.getUserRepos("testuser");
        assertEquals(1, result.size());
        assertEquals("repo1", result.get(0).getName());
    }

    @Test
    void getUserRepos_withSortingAndFiltering() {
        List<GitHubRepo> repos = Arrays.asList(
                new GitHubRepo("A", null, null, "Java", 5, "2020-01-01"),
                new GitHubRepo("B", null, null, "Go", 2, "2021-01-01"),
                new GitHubRepo("C", null, null, "Java", 10, "2019-01-01")
        );
        GitHubService spyService = spy(gitHubService);
        doReturn(repos).when(spyService).getUserRepos("testuser");
        List<GitHubRepo> sorted = spyService.getUserRepos("testuser", "stars", "desc", "Java");
        assertEquals(2, sorted.size());
        assertEquals("C", sorted.get(0).getName());
    }

    @Test
    void getRepoStats_returnsStats() {
        List<GitHubRepo> repos = Arrays.asList(
                new GitHubRepo("A", null, null, "Java", 5, null),
                new GitHubRepo("B", null, null, "Go", 2, null),
                new GitHubRepo("C", null, null, "Java", 10, null)
        );
        Map<String, Object> stats = gitHubService.getRepoStats(repos);
        assertEquals(3, stats.get("totalRepos"));
        @SuppressWarnings("unchecked")
        Map<String, Integer> byLang = (Map<String, Integer>) stats.get("byLanguage");
        assertEquals(2, byLang.get("Java"));
        assertEquals(1, byLang.get("Go"));
    }
} 