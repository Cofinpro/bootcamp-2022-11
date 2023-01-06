package com.cofinprobootcamp.backend.mails;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MailIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private JSONObject loginData;

    @BeforeEach
    void login() throws Exception {
        MvcResult result = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"luis.geyer@cofinpro.de\", \"password\": \"mega_gutes_passwort1\" }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.access_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.refresh_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("luis.geyer@cofinpro.de"))
                .andReturn();

        loginData = new JSONObject(result.getResponse().getContentAsString());
    }

    @Test
    void changeProfileOfOtherUser() throws Exception {
        // creation of profile for user from login-function
        createProfile();

        String outerId = getProfileOuterId();

        // login as other user
        MvcResult result = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"lennart.rehmer@cofinpro.de\", \"password\": \"mega_gutes_passwort1\" }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.access_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.refresh_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("lennart.rehmer@cofinpro.de"))
                .andReturn();

        JSONObject loginDataOfOtherUser = new JSONObject(result.getResponse().getContentAsString());

        // performing update for profile of user from login-function with credentials (access-token) of a different user
        mvc.perform(patch("/api/v1/profiles/" + outerId)
                        .header("authorization",
                                "Bearer " + loginDataOfOtherUser.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"Luis\",\n" +
                                "    \"lastName\": \"Geyer\",\n" +
                                "    \"jobTitle\": \"Architect\",\n" +
                                "    \"degree\": \"asdas\",\n" +
                                "    \"primaryExpertise\": \"Technologie\",\n" +
                                "    \"referenceText\": \"afdaefnwprvgklrwnmgvwülärf\",\n" +
                                "    \"skills\": [\"adfasdasd\",\"afasfdas\"],\n" +
                                "    \"phoneNumber\": \"12345678901\",\n" +
                                "    \"email\": \"luis.geyer@cofinpro.de\",\n" +
                                "    \"birthDate\": \"2020-10-10\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    void createProfile() throws Exception {
        mvc.perform(post("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"Luis\",\n" +
                                "    \"lastName\": \"Geyer\",\n" +
                                "    \"jobTitle\": \"Consultant\",\n" +
                                "    \"degree\": \"B. Sc.\",\n" +
                                "    \"primaryExpertise\": \"Technologie\",\n" +
                                "    \"referenceText\": \"abc\",\n" +
                                "    \"skills\": [\"Spring Boot\",\"Typescript\"],\n" +
                                "    \"phoneNumber\": \"12345678901\",\n" +
                                "    \"email\": \"luis.geyer@cofinpro.de\",\n" +
                                "    \"birthDate\": \"2020-10-10\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    private String getProfileOuterId() throws Exception {
        String mvcResult = mvc.perform(get("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(mvcResult).contains("\"name\":\"Luis Geyer\"");
        assertThat(mvcResult).contains("\"jobTitle\":\"Consultant\"");
        assertThat(mvcResult).contains("\"primaryExpertise\":\"Technologie\"");
        return extractOuterIdFromMvcResult(mvcResult);
    }

    private String extractOuterIdFromMvcResult(String mvcResult) {
        String tmp = mvcResult.split("[:,]")[1];
        StringBuilder builder = new StringBuilder(tmp);
        builder.deleteCharAt(0);
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

}
