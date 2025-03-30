package com.plewa.github.controller;

import com.plewa.github.dto.GitHubRepositoryDTO;
import com.plewa.github.exception.ErrorResponse;
import com.plewa.github.service.GitHubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/git")
public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }


    @GetMapping("/users/{username}/repos")
    public ResponseEntity<?> getUserRepositories(@PathVariable String username) {
        try {
            List<GitHubRepositoryDTO> repositories = gitHubService.getUserRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, "GitHub user not found"));
        }
    }
}
