package com.plewa.github.service;

import com.plewa.github.api.GitHubApiClient;
import com.plewa.github.dto.GitHubBranchDTO;
import com.plewa.github.dto.GitHubRepositoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GitHubService {

    private final GitHubApiClient gitHubApiClient;

    public GitHubService(GitHubApiClient gitHubApiClient) {
        this.gitHubApiClient = gitHubApiClient;
    }

    public List<GitHubRepositoryDTO> getUserRepositories(String username) {
        return gitHubApiClient.getUserRepositories(username).stream()
                .filter(repo -> !repo.fork())
                .map(repo -> new GitHubRepositoryDTO(
                        repo.name(),
                        repo.owner(),
                        repo.fork(),
                        getBranchesForRepository(repo.owner().login(), repo.name())
                ))
                .toList();
    }


    private List<GitHubBranchDTO> getBranchesForRepository(String owner, String repo) {
        return gitHubApiClient.getRepositoryBranches(owner, repo);
    }

}
