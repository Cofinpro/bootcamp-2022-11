package com.cofinprobootcamp.backend.user;

import org.junit.jupiter.api.BeforeEach;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserIntegrationTest {
    @Autowired
    private MockMvc mvc;
    private JSONObject loginData;

    @BeforeEach
    void setUp() throws Exception {
        mvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@test.de\"," +
                                " \"password\": \"password1\" ," +
                                "\"userRole\":  \"ADMIN\"}"))
                .andExpect(status().isOk());
        //login as User just created
        MvcResult result = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test@test.de\", \"password\": \"password1\" }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.access_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.refresh_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("test@test.de"))
                .andReturn();
        loginData = new JSONObject(result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Test for creation of user whose email already exists")
    void testEmailExists() {
        //TODO: Throw custom Excreption, else not integration-testable --> new branch for exception throwing
    }
    @Test
    @DisplayName("Test for getting all users")
    void testGetAllUsers() throws Exception{
        String mvcResult = mvc.perform(get("/api/v1/users")
                .header("authorization",
                        "Bearer " + loginData.getJSONObject("tokens").get("access_token")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(mvcResult).contains(
                "\"email\":\"test@test.de\"",
                "\"locked\":false",
                "\"identifier\":\"ADMIN\"",
                "\"displayName\":\"Administrator\""
        );
    }
}
