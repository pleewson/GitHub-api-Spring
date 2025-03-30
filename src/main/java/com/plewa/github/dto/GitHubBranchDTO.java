package com.plewa.github.dto;

public record GitHubBranchDTO(
        String name,
        Commit commit
) {
    public record Commit(String sha) {}
}
