package com.plewa.github.api;

import com.plewa.github.dto.GitHubBranchDTO;
import com.plewa.github.dto.GitHubRepositoryDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class GitHubApiClient {
    private final RestTemplate restTemplate;
    private final String BASE_URL;


    public GitHubApiClient(RestTemplateBuilder restTemplateBuilder, @Value("${github.api.url}") String BASE_URL) {
        this.restTemplate = restTemplateBuilder.build();
        this.BASE_URL = BASE_URL;
    }


    public List<GitHubRepositoryDTO> getUserRepositories(String username) throws HttpClientErrorException {
        String url = BASE_URL + "/users/" + username + "/repos";
        GitHubRepositoryDTO[] repositories = restTemplate.getForObject(url, GitHubRepositoryDTO[].class);

        return Arrays.asList(repositories);
    }


    public List<GitHubBranchDTO> getRepositoryBranches(String owner, String repo) throws HttpClientErrorException {
        String url = BASE_URL + "/repos/" + owner + "/" + repo + "/branches";
        GitHubBranchDTO[] branches = restTemplate.getForObject(url, GitHubBranchDTO[].class);

        return Arrays.asList(branches);
    }
}
