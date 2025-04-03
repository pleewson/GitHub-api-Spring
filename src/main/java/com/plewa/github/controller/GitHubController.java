package com.plewa.github.controller;

import com.plewa.github.dto.GitHubRepositoryDTO;
import com.plewa.github.exception.ErrorResponse;
import com.plewa.github.service.GitHubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;


@RestController
@RequestMapping("/git")
public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }


    @GetMapping("/users/{username}/repos")
    public ResponseEntity<List<GitHubRepositoryDTO>> getUserRepositories(@PathVariable String username) {
        List<GitHubRepositoryDTO> repositories = gitHubService.getUserRepositories(username);
        return ResponseEntity.ok(repositories);
    }


    @ExceptionHandler(HttpClientErrorException.class)
    ResponseEntity<ErrorResponse> handleExceptionWhenUserNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "GitHub user not found"));
    }
}
