package com.plewa.github;

import com.plewa.github.controller.GitHubController;
import com.plewa.github.dto.GitHubBranchDTO;
import com.plewa.github.dto.GitHubRepositoryDTO;
import com.plewa.github.service.GitHubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(GitHubController.class)
class GitHubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GitHubService gitHubService;


    @Test
    void givenExistingUser_whenGetUserRepositories_thenReturnCorrect() throws Exception {

        List<GitHubRepositoryDTO> repositories = List.of(
                new GitHubRepositoryDTO("repo1",
                        new GitHubRepositoryDTO.Owner("testUser"),
                        false,
                        List.of(new GitHubBranchDTO("main", new GitHubBranchDTO.Commit("testCommit"))))
        );

        when(gitHubService.getUserRepositories("testUser")).thenReturn(repositories);


        mockMvc.perform(get("/git/users/testUser/repos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("repo1"))
                .andExpect(jsonPath("$[0].owner.login").value("testUser"))
                .andExpect(jsonPath("$[0].fork").value("false"))
                .andExpect(jsonPath("$[0].branches[0].name").value("main"))
                .andExpect(jsonPath("$[0].branches[0].commit.sha").value("testCommit"));
    }


    @Test
    void givenNonExistingUser_whenGetUserRepositories_thenReturn404NotFound() throws Exception {

        when(gitHubService.getUserRepositories("nonExistingUser")).thenThrow(new RuntimeException("User not found"));


        mockMvc.perform(get("/git/users/nonExistingUser/repos"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("GitHub user not found"));
    }
}

