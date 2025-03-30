package com.plewa.github;

import com.plewa.github.service.GitHubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class GitHubControllerFullIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenExistingUserWithForkedAndNonForkedRepositories_whenGetUserRepositories_thenReturnNonForkedRepositories() throws Exception {
        mockMvc.perform(get("/git/users/octocat/repos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6))
                .andExpect(jsonPath("$[0].owner.login").value("octocat"))
                .andExpect(jsonPath("$[0].fork").value(false))
                .andExpect(jsonPath("$[1].fork").value(false))
                .andExpect(jsonPath("$[2].fork").value(false))
                .andExpect(jsonPath("$[3].fork").value(false))
                .andExpect(jsonPath("$[4].fork").value(false))
                .andExpect(jsonPath("$[5].fork").value(false));
    }

}
