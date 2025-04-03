package com.plewa.github;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class GitHubControllerFullIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("github.api.url", wireMockServer::baseUrl);
    }


    @BeforeEach
    void setUp() {
        wireMockServer.stubFor(
                WireMock.get(
                                urlMatching("/users/testUser/repos")
                        )
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBody("""
                                                    [
                                                        {"name": "repo1", "owner": {"login": "testUser"}, "fork": false},
                                                        {"name": "repo2", "owner": {"login": "testUser"}, "fork": true},
                                                        {"name": "repo3", "owner": {"login": "testUser"}, "fork": false}
                                                    ]
                                                """)
                        ));


        wireMockServer.stubFor(
                WireMock.get(
                                urlMatching("/repos/testUser/repo1/branches")
                        )
                        .willReturn(
                                WireMock.aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withStatus(200)
                                        .withBody("""
                                                    [
                                                        {
                                                            "name": "develop",
                                                            "commit": {
                                                                "sha": "123456789"
                                                            }
                                                        },
                                                        {
                                                            "name": "main",
                                                            "commit": {
                                                                "sha": "987654321"
                                                            }
                                                        }
                                                    ]
                                                """)
                        ));

        wireMockServer.stubFor(
                WireMock.get(
                                urlMatching("/repos/testUser/repo2/branches")
                        )
                        .willReturn(
                                WireMock.aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withStatus(200)
                                        .withBody("""
                                                    [
                                                        {
                                                            "name": "testBranch",
                                                            "commit": {
                                                                "sha": "111111111"
                                                            }
                                                        },
                                                        {
                                                            "name": "main",
                                                            "commit": {
                                                                "sha": "333333333"
                                                            }
                                                        }
                                                    ]
                                                """)
                        ));

        wireMockServer.stubFor(
                WireMock.get(
                                urlMatching("/repos/testUser/repo3/branches")
                        )
                        .willReturn(
                                WireMock.aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withStatus(200)
                                        .withBody("""
                                                    [
                                                        {
                                                            "name": "test",
                                                            "commit": {
                                                                "sha": "55555"
                                                            }
                                                        },
                                                        {
                                                            "name": "main",
                                                            "commit": {
                                                                "sha": "555555"
                                                            }
                                                        }
                                                    ]
                                                """)
                        ));
    }


    @Test
    void givenExistingUserWithForkedAndNonForkedRepositories_whenGetUserRepositories_thenReturnNonForkedRepositories() throws Exception {
        mockMvc.perform(get("/git/users/testUser/repos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("repo1"))
                .andExpect(jsonPath("$[0].branches.length()").value(2))
                .andExpect(jsonPath("$[0].branches[0].name").value("develop"))
                .andExpect(jsonPath("$[0].branches[0].commit.sha").value("123456789"));
    }

}