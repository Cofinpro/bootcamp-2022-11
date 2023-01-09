package com.cofinprobootcamp.backend.profile;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
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
public class ProfileIntegrationTest {
    @Autowired
    private MockMvc mvc;
    private JSONObject loginData;
    @BeforeEach
    public void initialize() throws Exception {
        //login as User
        MvcResult result = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"markus.kremer@cofinpro.de\", \"password\": \"mega_gutes_passwort1\" }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.access_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.refresh_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("markus.kremer@cofinpro.de"))
                .andReturn();
        loginData = new JSONObject(result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Test Profile Post endpoint with non existing email! Only success Criterium is to give back 404 error!")
    void testWithNonexistingProfile() throws Exception {
        mvc.perform(post("/api/v1/profiles")
                .header("authorization",
                        "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Markus\",\n" +
                        "    \"lastName\": \"Kremer\",\n" +
                        "    \"jobTitle\": \"Consultant\",\n" +
                        "    \"degree\": \"adaaas\",\n" +
                        "    \"primaryExpertise\": \"Technologie\",\n" +
                        "    \"referenceText\": \"afdaefnwprvgklrwnmgvwülärf\",\n" +
                        "    \"skills\": [\"adfasdasd\",\"afasfdas\"],\n" +
                        "    \"phoneNumber\": \"12345678901\",\n" +
                        "    \"email\": \"markus.kremer22@cofinpro.de\",\n" +
                        "    \"birthDate\": \"2020-10-10\"\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }
    @Test
    @DisplayName("Test Profile with jobTitle that is not in database! Only success Criterium is to give back 404 error!")
    void testWithNonexistingJobTitle() throws Exception {
        mvc.perform(post("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"Markus\",\n" +
                                "    \"lastName\": \"Kremer\",\n" +
                                "    \"jobTitle\": \"Baby\",\n" +
                                "    \"degree\": \"adaaas\",\n" +
                                "    \"primaryExpertise\": \"Technologie\",\n" +
                                "    \"referenceText\": \"afdaefnwprvgklrwnmgvwülärf\",\n" +
                                "    \"skills\": [\"adfasdasd\",\"afasfdas\"],\n" +
                                "    \"phoneNumber\": \"12345678901\",\n" +
                                "    \"email\": \"markus.kremer@cofinpro.de\",\n" +
                                "    \"birthDate\": \"2020-10-10\"\n" +
                                "}"))
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("Test Profile with Failing Validation! Only success Criterium is to give back 400 error!")
    void testWithNonValidBody() throws Exception {
        mvc.perform(post("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"Markus\",\n" +
                                "    \"lastName\": \"\",\n" +
                                "    \"jobTitle\": \"Consultant\",\n" +
                                "    \"degree\": \"adaaas\",\n" +
                                "    \"primaryExpertise\": \"Technologie\",\n" +
                                "    \"referenceText\": \"afdaefnwprvgklrwnmgvwülärf\",\n" +
                                "    \"skills\": [\"adfasdasd\",\"afasfdas\"],\n" +
                                "    \"phoneNumber\": \"12345678901\",\n" +
                                "    \"email\": \"markus.kremer@cofinpro.de\",\n" +
                                "    \"birthDate\": \"2020-10-10\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Test Profile POST with Everything entered correctly! Also tests GET of overviews and detail!")
    void testHappyPath() throws Exception {
        mvc.perform(post("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"Markus\",\n" +
                                "    \"lastName\": \"Kremer\",\n" +
                                "    \"jobTitle\": \"Consultant\",\n" +
                                "    \"degree\": \"adaaas\",\n" +
                                "    \"primaryExpertise\": \"Technologie\",\n" +
                                "    \"referenceText\": \"afdaefnwprvgklrwnmgvwülärf\",\n" +
                                "    \"skills\": [\"adfasdasd\",\"afasfdas\"],\n" +
                                "    \"phoneNumber\": \"12345678901\",\n" +
                                "    \"email\": \"markus.kremer@cofinpro.de\",\n" +
                                "    \"birthDate\": \"2020-10-10\"\n" +
                                "}"))
                .andExpect(status().isOk());
        String mvcResult = mvc.perform(get("/api/v1/profiles")
                .header("authorization",
                        "Bearer " + loginData.getJSONObject("tokens").get("access_token")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(mvcResult).contains("\"name\":\"Markus Kremer\"");
        assertThat(mvcResult).contains("\"jobTitle\":\"Consultant\"");
        assertThat(mvcResult).contains("\"primaryExpertise\":\"Technologie\"");
        String outerId = extractOuterIdFromMvcResult(mvcResult);
        mvcResult = mvc.perform(get("/api/v1/profiles/" + outerId).header("authorization",
                "Bearer " + loginData.getJSONObject("tokens").get("access_token")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(mvcResult).contains("\"id\":\"" + outerId + "\"");
        assertThat(mvcResult).contains("\"email\":\"markus.kremer@cofinpro.de\"");
        assertThat(mvcResult).contains("\"phoneNumber\":\"12345678901\"");
        assertThat(mvcResult).contains("\"jobTitle\":\"Consultant\"");
        assertThat(mvcResult).contains("\"primaryExpertise\":\"Technologie\"");
        assertThat(mvcResult).contains("\"skills\"");
        assertThat(mvcResult).contains("\"afasfdas\"");
        assertThat(mvcResult).contains("\"adfasdasd\"");
        assertThat(mvcResult).contains("\"firstName\":\"Markus\"");
        assertThat(mvcResult).contains("\"lastName\":\"Kremer\"");
        assertThat(mvcResult).contains("\"birthDate\":\"2020-10-10\"");
    }

    @Test
    @DisplayName("Test Profile PATCH with Everything entered correctly! Also tests GET of overviews!")
    void testPatchHappyPath() throws Exception {
        mvc.perform(post("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"Markus\",\n" +
                                "    \"lastName\": \"Kremer\",\n" +
                                "    \"jobTitle\": \"Consultant\",\n" +
                                "    \"degree\": \"adaaas\",\n" +
                                "    \"primaryExpertise\": \"Technologie\",\n" +
                                "    \"referenceText\": \"afdaefnwprvgklrwnmgvwülärf\",\n" +
                                "    \"skills\": [\"adfasdasd\",\"afasfdas\"],\n" +
                                "    \"phoneNumber\": \"12345678901\",\n" +
                                "    \"email\": \"markus.kremer@cofinpro.de\",\n" +
                                "    \"birthDate\": \"2020-10-10\"\n" +
                                "}"))
                .andExpect(status().isOk());
        String mvcResult = mvc.perform(get("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(mvcResult).contains("\"name\":\"Markus Kremer\"");
        assertThat(mvcResult).contains("\"jobTitle\":\"Consultant\"");
        assertThat(mvcResult).contains("\"primaryExpertise\":\"Technologie\"");
        String outerId = extractOuterIdFromMvcResult(mvcResult);
        mvc.perform(patch("/api/v1/profiles/" + outerId)
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"Markus\",\n" +
                                "    \"lastName\": \"Kremer\",\n" +
                                "    \"jobTitle\": \"Architect\",\n" +
                                "    \"degree\": \"asdas\",\n" +
                                "    \"primaryExpertise\": \"Technologie\",\n" +
                                "    \"referenceText\": \"afdaefnwprvgklrwnmgvwülärf\",\n" +
                                "    \"skills\": [\"adfasdasd\",\"afasfdas\"],\n" +
                                "    \"phoneNumber\": \"12345678901\",\n" +
                                "    \"email\": \"markus.kremer@cofinpro.de\",\n" +
                                "    \"birthDate\": \"2020-10-10\"\n" +
                                "}"))
                .andExpect(status().isOk());
        mvcResult = mvc.perform(get("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(mvcResult).contains("\"name\":\"Markus Kremer\"");
        assertThat(mvcResult).contains("\"jobTitle\":\"Architect\"");
        assertThat(mvcResult).contains("\"primaryExpertise\":\"Technologie\"");
    }

    @Test
    @DisplayName("Tests DELETE endpoint!")
    void testDeleteProfile() throws Exception{
        mvc.perform(post("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"Markus\",\n" +
                                "    \"lastName\": \"Kremer\",\n" +
                                "    \"jobTitle\": \"Consultant\",\n" +
                                "    \"degree\": \"adaaas\",\n" +
                                "    \"primaryExpertise\": \"Technologie\",\n" +
                                "    \"referenceText\": \"afdaefnwprvgklrwnmgvwülärf\",\n" +
                                "    \"skills\": [\"adfasdasd\",\"afasfdas\"],\n" +
                                "    \"phoneNumber\": \"12345678901\",\n" +
                                "    \"email\": \"markus.kremer@cofinpro.de\",\n" +
                                "    \"birthDate\": \"2020-10-10\"\n" +
                                "}"))
                .andExpect(status().isOk());
        String mvcResult = mvc.perform(get("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String outerId = extractOuterIdFromMvcResult(mvcResult);
        mvc.perform(delete("/api/v1/profiles/" + outerId)
                .header("authorization",
                        "Bearer " + loginData.getJSONObject("tokens").get("access_token")))
                .andExpect(status().isOk());
        mvcResult = mvc.perform(get("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(mvcResult).isEqualTo("[]");
    }

    private String extractOuterIdFromMvcResult(String mvcResult) {
        String tmp = mvcResult.split("[:,]")[1];
        StringBuilder builder = new StringBuilder(tmp);
        builder.deleteCharAt(0);
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
