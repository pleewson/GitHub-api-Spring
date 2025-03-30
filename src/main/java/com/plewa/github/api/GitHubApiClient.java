package com.plewa.github.api;

import com.plewa.github.dto.GitHubBranchDTO;
import com.plewa.github.dto.GitHubRepositoryDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class GitHubApiClient {
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://api.github.com";


    public GitHubApiClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    public List<GitHubRepositoryDTO> getUserRepositories(String username) {
        String url = BASE_URL + "/users/" + username + "/repos";

        GitHubRepositoryDTO[] repositories = restTemplate.getForObject(url, GitHubRepositoryDTO[].class);
        return repositories != null ? Arrays.asList(repositories) : List.of();
    }


    public List<GitHubBranchDTO> getRepositoryBranches(String owner, String repo) {
        String url = BASE_URL + "/repos/" + owner + "/" + repo + "/branches";

        GitHubBranchDTO[] branches = restTemplate.getForObject(url, GitHubBranchDTO[].class);
        return branches != null ? Arrays.asList(branches) : List.of();
    }
}
