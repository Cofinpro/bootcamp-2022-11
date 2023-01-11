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
import org.springframework.test.web.servlet.ResultActions;
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
    @DisplayName("Test Profile Post endpoint with email that doesnt correspond to users email!" +
            " Only success Criterium is to give back 403 error!")
    void testWithNonexistingProfile() throws Exception {
        ToPost toPost = new ToPost("Markus",
                "Kremer",
                "Consultant",
                "a",
                "Technologie",
                "ref",
                "[\"skill\"]",
                "12345678901",
                "luis.geyer@cofinpro.de",
                "2020-10-10");
        postProfile(toPost)
                .andExpect(status().isForbidden());
    }
    @Test
    @DisplayName("Test Profile with jobTitle that is not in database! " +
            "Only success Criterium is to give back 404 error!")
    void testWithNonexistingJobTitle() throws Exception {
        ToPost toPost = new ToPost("Markus",
                "dycy",
                "god",
                "a",
                "Technologie",
                "ref",
                "[\"skill\"]",
                "12345678901",
                "markus.kremer@cofinpro.de",
                "2020-10-10");
                postProfile(toPost)
                        .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("Test Profile with Failing Validation! " +
            "Only success Criterium is to give back 400 error!")
    void testWithNonValidBody() throws Exception {
        ToPost toPost = new ToPost("Markus",
                "",
                "Consultant",
                "a",
                "Technologie",
                "ref",
                "[\"skill\"]",
                "12345678901",
                "markus.kremer@cofinpro.de",
                "2020-10-10");
        postProfile(toPost)
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Test Profile POST with Everything entered correctly! " +
            "Also tests GET of overviews and detail!")
    void testHappyPath() throws Exception {
        ToPost toPost = new ToPost("Markus",
                "Kremer",
                "Consultant",
                "a",
                "Technologie",
                "ref",
                "[\"skill\"]",
                "12345678901",
                "markus.kremer@cofinpro.de",
                "2020-10-10");
        postProfile(toPost)
                .andExpect(status().isCreated());
        String mvcResult = mvc.perform(get("/api/v1/profiles")
                .header("authorization",
                        "Bearer " + loginData.getJSONObject("tokens").get("access_token")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(mvcResult).contains("\"name\":\"Markus Kremer\"");
        assertThat(mvcResult).contains("\"jobTitle\":\""+ toPost.jobTitle() + "\"");
        assertThat(mvcResult).contains("\"primaryExpertise\":\""+ toPost.primaryExpertise()+ "\"");
        String outerId = extractOuterIdFromMvcResult(mvcResult);
        mvcResult = mvc.perform(get("/api/v1/profiles/" + outerId).header("authorization",
                "Bearer " + loginData.getJSONObject("tokens").get("access_token")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(mvcResult).contains("\"id\":\"" + outerId + "\"");
        assertThat(mvcResult).contains("\"email\":\"" +toPost.email() + "\"");
        assertThat(mvcResult).contains("\"phoneNumber\":\""+ toPost.phoneNumber() +"\"");
        assertThat(mvcResult).contains("\"jobTitle\":\"" + toPost.jobTitle()+ "\"");
        assertThat(mvcResult).contains("\"primaryExpertise\":\"" + toPost.primaryExpertise()+ "\"");
        assertThat(mvcResult).contains("\"skills\"");
        assertThat(mvcResult).contains(toPost.skills());
        assertThat(mvcResult).contains("\"firstName\":\"" +toPost.firstName() +"\"");
        assertThat(mvcResult).contains("\"lastName\":\""+ toPost.lastName() +"\"");
        assertThat(mvcResult).contains("\"birthDate\":\""+ toPost.birthDate() +"\"");
    }

    @Test
    @DisplayName("Test Profile PATCH with Everything entered correctly!" +
            " Also tests GET of overviews!")
    void testPatchHappyPath() throws Exception {
        ToPost toPost = new ToPost("Markus",
                "Kremer",
                "Consultant",
                "a",
                "Technologie",
                "ref",
                "[\"skill\"]",
                "12345678901",
                "markus.kremer@cofinpro.de",
                "2020-10-10");
        postProfile(toPost)
                .andExpect(status().isCreated());
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
        toPost = new ToPost("Markus",
                "Kremer",
                "Architect",
                "a",
                "Technologie",
                "ref",
                "[\"skill\"]",
                "12345678901",
                "markus.kremer@cofinpro.de",
                "2020-10-10");
        patchProfile(toPost, outerId, loginData)
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

    @DisplayName("Test patching other users profile as USER")
    @Test
    void testPatchOfOtherUsersProfile() throws Exception {
        ToPost toPost = new ToPost("Markus",
                "Kremer",
                "Consultant",
                "a",
                "Technologie",
                "ref",
                "[\"skill\"]",
                "12345678901",
                "markus.kremer@cofinpro.de",
                "2020-10-10");
        postProfile(toPost)
                .andExpect(status().isCreated());
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

        mvcResult = mvc.perform(get("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(mvcResult).contains("\"name\":\"Markus Kremer\"");
        assertThat(mvcResult).contains("\"jobTitle\":\"Consultant\"");
        assertThat(mvcResult).contains("\"primaryExpertise\":\"Technologie\"");


        MvcResult result = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"luis.geyer@cofinpro.de\", \"password\": \"mega_gutes_passwort1\" }"))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject otherLoginData = new JSONObject(result.getResponse().getContentAsString());

        patchProfile(toPost,outerId,otherLoginData)
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Tests DELETE endpoint with own Profile!")
    void testDeleteProfile() throws Exception{
        ToPost toPost = new ToPost("Markus",
                "Kremer",
                "Architect",
                "a",
                "Technologie",
                "ref",
                "[\"skill\"]",
                "12345678901",
                "markus.kremer@cofinpro.de",
                "2020-10-10");
        postProfile(toPost)
                .andExpect(status().isCreated());
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

    private ResultActions postProfile(ToPost toPost) throws Exception {
        return mvc.perform(post("/api/v1/profiles")
                .header("authorization",
                        "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"" + toPost.firstName() + "\",\n" +
                        "    \"lastName\": \"" + toPost.lastName()+ "\",\n" +
                        "    \"jobTitle\": \""+ toPost.jobTitle() + "\",\n" +
                        "    \"degree\": \"" + toPost.degree() +"\",\n" +
                        "    \"primaryExpertise\": \"" + toPost.primaryExpertise() + "\",\n" +
                        "    \"referenceText\": \""+ toPost.referenceText()+ "\",\n" +
                        "    \"skills\":  "+ toPost.skills() + ",\n" +
                        "    \"phoneNumber\": \""+ toPost.phoneNumber() +"\",\n" +
                        "    \"email\": \"" + toPost.email()+  "\",\n" +
                        "    \"birthDate\": \""+ toPost.birthDate() + "\"\n" +
                        "}"));
    }

    private ResultActions patchProfile(ToPost toPost, String outerId, JSONObject loginData) throws Exception {
        return mvc.perform(patch("/api/v1/profiles/" + outerId)
                .header("authorization",
                        "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"" + toPost.firstName() + "\",\n" +
                        "    \"lastName\": \"" + toPost.lastName()+ "\",\n" +
                        "    \"jobTitle\": \""+ toPost.jobTitle() + "\",\n" +
                        "    \"degree\": \"" + toPost.degree() +"\",\n" +
                        "    \"primaryExpertise\": \"" + toPost.primaryExpertise() + "\",\n" +
                        "    \"referenceText\": \""+ toPost.referenceText()+ "\",\n" +
                        "    \"skills\": "+ toPost.skills() + ",\n" +
                        "    \"phoneNumber\": \""+ toPost.phoneNumber() +"\",\n" +
                        "    \"email\": \"" + toPost.email()+  "\",\n" +
                        "    \"birthDate\": \""+ toPost.birthDate() + "\"\n" +
                        "}"));
    }
    private String extractOuterIdFromMvcResult(String mvcResult) {
        String tmp = mvcResult.split("[:,]")[1];
        StringBuilder builder = new StringBuilder(tmp);
        builder.deleteCharAt(0);
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}

record ToPost(
        String firstName,
        String lastName,
        String jobTitle,
        String degree,
        String primaryExpertise,
        String referenceText,
        String skills,
        String phoneNumber,
        String email,
        String birthDate
) {

}
