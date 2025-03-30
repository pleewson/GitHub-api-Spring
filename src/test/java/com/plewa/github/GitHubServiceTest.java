package com.plewa.github;

import com.plewa.github.api.GitHubApiClient;
import com.plewa.github.dto.GitHubBranchDTO;
import com.plewa.github.dto.GitHubRepositoryDTO;
import com.plewa.github.service.GitHubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class GitHubServiceTest {

    @MockitoBean
    private GitHubApiClient gitHubApiClient;

    @Autowired
    private GitHubService gitHubService;


    @Test
    void  givenRepositoriesWithForkedAndNonForked_whenFilterForkedRepositories_thenReturnOnlyNonForked() {
        List<GitHubRepositoryDTO> repositories = List.of(
                new GitHubRepositoryDTO("repo1", new GitHubRepositoryDTO.Owner("testUser"), false, List.of(new GitHubBranchDTO("main", new GitHubBranchDTO.Commit("testCommit")))),
                new GitHubRepositoryDTO("repo2", new GitHubRepositoryDTO.Owner("testUser"), true, List.of(new GitHubBranchDTO("main", new GitHubBranchDTO.Commit("testCommit"))))
        );

        when(gitHubApiClient.getUserRepositories("testUser")).thenReturn(repositories);

        List<GitHubRepositoryDTO> filteredRepositories = gitHubService.getUserRepositories("testUser");

        assertEquals(1, filteredRepositories.size());
        assertEquals("repo1", filteredRepositories.get(0).name());
        assertFalse(filteredRepositories.get(0).fork());
    }
}