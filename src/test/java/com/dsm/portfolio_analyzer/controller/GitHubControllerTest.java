package com.dsm.portfolio_analyzer.controller;

import com.dsm.portfolio_analyzer.exception.UserNotFoundException;
import com.dsm.portfolio_analyzer.model.GitHubRepo;
import com.dsm.portfolio_analyzer.model.GitHubUser;
import com.dsm.portfolio_analyzer.service.GitHubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GitHubController.class)
class GitHubControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GitHubService gitHubService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public GitHubService gitHubService() {
            return Mockito.mock(GitHubService.class);
        }
    }

    @Test
    void getUser_returnsUser() throws Exception {
        GitHubUser user = new GitHubUser();
        when(gitHubService.getUser("dhruvin")).thenReturn(user);
        mockMvc.perform(get("/api/github/dhruvin"))
                .andExpect(status().isOk());
    }

    @Test
    void getUser_notFound() throws Exception {
        when(gitHubService.getUser("nouser")).thenThrow(new UserNotFoundException("not found"));
        mockMvc.perform(get("/api/github/nouser"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("not found"));
    }

    @Test
    void getUserRepos_returnsRepos() throws Exception {
        List<GitHubRepo> repos = Arrays.asList(new GitHubRepo("repo1", null, null, "Java", 1, null));
        when(gitHubService.getUserRepos(eq("dhruvin"), any(), any(), any())).thenReturn(repos);
        mockMvc.perform(get("/api/github/dhruvin/repos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("repo1"));
    }

    @Test
    void getUserRepoStats_returnsStats() throws Exception {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRepos", 2);
        Map<String, Integer> byLang = new HashMap<>();
        byLang.put("Java", 2);
        stats.put("byLanguage", byLang);
        when(gitHubService.getUserRepos("dhruvin")).thenReturn(Arrays.asList(
                new GitHubRepo("repo1", null, null, "Java", 1, null),
                new GitHubRepo("repo2", null, null, "Java", 2, null)));
        when(gitHubService.getRepoStats(any())).thenReturn(stats);
        mockMvc.perform(get("/api/github/dhruvin/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRepos").value(2))
                .andExpect(jsonPath("$.byLanguage.Java").value(2));
    }
} 