package com.cofinprobootcamp.backend.user;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
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
        //login as Admin
        MvcResult result = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"lennart.rehmer@cofinpro.de\", \"password\": \"mega_gutes_passwort1\" }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.access_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.refresh_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("lennart.rehmer@cofinpro.de"))
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
        assertThat(mvcResult)
                .contains(
                        "\"email\":\"lennart.rehmer@cofinpro.de\"," +
                        "\"locked\":false," +
                        "\"role\":{\"identifier\":\"ADMIN\",\"displayName\":\"Administrator\""
                );
    }

    @Test
    @DisplayName("Test for locking an ADMIN")
    void lockAdmin() throws Exception{

        // Get all users to find id of ADMIN "Lennart" and HR "Theresa"
        MockHttpServletResponse mvcResult = mvc.perform(get("/api/v1/users/")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        JSONArray users = new JSONArray(mvcResult.getContentAsString());
        String outerId = "";
        String nextId = "";
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            String userEmail = user.getString("email");
            if ("lennart.rehmer@cofinpro.de".equals(userEmail)) {
                outerId = user.getString("id");
            } else if ("theresa.riesterer@cofinpro.de".equals(userEmail)) {
                nextId = user.getString("id");
            }
        }

        // lock ADMIN "Lennart"
        mvcResult = mvc.perform(patch("/api/v1/users/{id}/lock", outerId)
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(202);

        // make "Theresa" ADMIN (possible because still not locked)
        mvcResult = mvc.perform(patch("/api/v1/users/{id}/{role}", nextId, "Administrator")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(200);

        //login as second ADMIN
        MvcResult result = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"theresa.riesterer@cofinpro.de\", \"password\": \"mega_gutes_passwort1\" }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.access_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.refresh_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("theresa.riesterer@cofinpro.de"))
                .andReturn();
        loginData = new JSONObject(result.getResponse().getContentAsString());

        //lock "Lennart" a second time
        mvcResult = mvc.perform(patch("/api/v1/users/{id}/lock", outerId)
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(200);

        //try to log in as "Lennart"
        mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"lennart.rehmer@cofinpro.de\", \"password\": \"mega_gutes_passwort1\" }"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test for changing the role of an ADMIN")
    void changeRoleOfAdmin() throws Exception{

        // Get all users to find id of HR "Theresa"
        MockHttpServletResponse mvcResult = mvc.perform(get("/api/v1/users/")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        JSONArray users = new JSONArray(mvcResult.getContentAsString());
        String nextId = "";
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            String userEmail = user.getString("email");
            if ("theresa.riesterer@cofinpro.de".equals(userEmail)) {
                nextId = user.getString("id");
            }
        }

        // make "Theresa" ADMIN
        mvcResult = mvc.perform(patch("/api/v1/users/{id}/{role}", nextId, "Administrator")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(200);

        // make "Theresa" USER
        mvcResult = mvc.perform(patch("/api/v1/users/{id}/{role}", nextId, "Nutzer")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(202);

        //login as second ADMIN
        MvcResult result = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"theresa.riesterer@cofinpro.de\", \"password\": \"mega_gutes_passwort1\" }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.access_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.refresh_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("theresa.riesterer@cofinpro.de"))
                .andReturn();
        loginData = new JSONObject(result.getResponse().getContentAsString());

        // change role to USER a second time (possible because still ADMIN)
        mvcResult = mvc.perform(patch("/api/v1/users/{id}/{role}", nextId, "Nutzer")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(200);

        // try to change it again
        mvcResult = mvc.perform(patch("/api/v1/users/{id}/{role}", nextId, "Nutzer")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(401);
    }
}
