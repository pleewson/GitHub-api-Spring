package com.plewa.github.dto;

import java.util.List;

public record GitHubRepositoryDTO(
        String name,
        Owner owner,
        boolean fork,
        List<GitHubBranchDTO> branches
) {
    public record Owner(String login) {}
}
