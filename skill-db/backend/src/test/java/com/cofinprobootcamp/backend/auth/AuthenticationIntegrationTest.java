package com.cofinprobootcamp.backend.auth;

import com.cofinprobootcamp.backend.exceptions.UserAlreadyExistsException;
import com.cofinprobootcamp.backend.role.StandardRoles;
import com.cofinprobootcamp.backend.user.UserService;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthenticationIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    private JSONObject loginData;
    private final String email = "lars.testermann@cofinpro.de";
    private final String password = "mega_gutes_passwort2";
    private final StandardRoles userRole = StandardRoles.ADMIN;

    @BeforeEach
    void setUpTestAdmin() throws Exception {
        try {
            userService.createUser(new UserCreateInDTO(
                    email,
                    password,
                    userRole.name()
            ));
        } catch (UserAlreadyExistsException exception) {
            assertThat(exception).isInstanceOf(UserAlreadyExistsException.class);
        }
        login();
    }

    @ParameterizedTest
    @MethodSource(value = "validCredentialsProvider")
    @DisplayName("Test authentication for valid credentials")
    void logIn_with_User_When_Valid(String username, String password) throws Exception {
        MockHttpServletResponse mvcResult = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\" : \"" + username + "\" , \"password\" : \"" + password + "\"}"))
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(200);
    }

    @Test
    @DisplayName("Test fail login, when invalid credentials")
    void logIn_with_invalid_credentials() throws Exception {
        MockHttpServletResponse mvcResult = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\" : \"" + "xyz@cofinpro.de" + "\" , \"password\" : \"" + password + "\"}"))
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(401);
        mvcResult = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\" : \"" + email + "\" , \"password\" : \"" + "test_pw" + "\"}"))
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(401);
    }

    @Test
    @DisplayName("Test succeed with accessing profile overview as admin, with valid credentials")
    void access_profileOverview_as_admin() throws Exception {
        MockHttpServletResponse mvcResult = mvc.perform(get("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(200);
    }

    @Test
    @DisplayName("Test succeed with accessing profile overview as basic user, with valid credentials")
    void access_profileOverview_as_user() throws Exception {
        login("markus.kremer@cofinpro.de", "mega_gutes_passwort1");
        MockHttpServletResponse mvcResult = mvc.perform(get("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(200);
    }

    @Test
    @DisplayName("Test succeed with accessing user overview as admin, with valid credentials")
    void access_userOverview_as_admin() throws Exception {
        MockHttpServletResponse mvcResult = mvc.perform(get("/api/v1/users")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(200);
    }

    @Test
    @DisplayName("Test fail with accessing user overview as basic user, with valid credentials")
    void access_userOverview_as_user() throws Exception {
        login("markus.kremer@cofinpro.de", "mega_gutes_passwort1");
        MockHttpServletResponse mvcResult = mvc.perform(get("/api/v1/users")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(403);
    }

    @Test
    @DisplayName("Test succeed with creating profile for self as basic user, with valid credentials")
    void create_new_profile_for_self_as_user() throws Exception {
        login("luis.geyer@cofinpro.de", "mega_gutes_passwort1");
        MockHttpServletResponse mvcResult = mvc.perform(post("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\n" +
                                "\"email\": \"luis.geyer@cofinpro.de\",\n" +
                                "\"firstName\": \"Luis\",\n" +
                                "\"lastName\": \"Geyer\",\n" +
                                "\"jobTitle\": \"Consultant\",\n" +
                                "\"degree\": \"B.Sc.\",\n" +
                                "\"primaryExpertise\": \"Technologie\",\n" +
                                "\"referenceText\": \"\",\n" +
                                "\"skills\": [\n" +
                                "\"C#\",\n" +
                                "\"Unity\"\n" +
                                "],\n" +
                                "\"phoneNumber\": \"5347959055566\",\n" +
                                "\"birthDate\": \"1993-04-21\"\n" +
                                "}"
                        )
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(201);
    }

    @Test
    @DisplayName("Test fail with creating profile for other as basic user, with valid credentials")
    void create_new_profile_for_other_as_user() throws Exception {
        login("luis.geyer@cofinpro.de", "mega_gutes_passwort1");
        MockHttpServletResponse mvcResult = mvc.perform(post("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\n" +
                                "\"email\": \"lennart.rehmer@cofinpro.de\",\n" +
                                "\"firstName\": \"Lennart\",\n" +
                                "\"lastName\": \"Rehmer\",\n" +
                                "\"jobTitle\": \"Consultant\",\n" +
                                "\"degree\": \"M.Sc.\",\n" +
                                "\"primaryExpertise\": \"Technologie\",\n" +
                                "\"referenceText\": \"\",\n" +
                                "\"skills\": [\n" +
                                "\"C#\",\n" +
                                "\"Unity\"\n" +
                                "],\n" +
                                "\"phoneNumber\": \"5347959055566\",\n" +
                                "\"birthDate\": \"1993-04-21\"\n" +
                                "}"
                        )
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(403);
    }

    @Test
    @DisplayName("Test succeed with creating profile for other as admin, with valid credentials")
    void create_new_profile_for_other_as_admin() throws Exception {
        MockHttpServletResponse mvcResult = mvc.perform(post("/api/v1/profiles")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\n" +
                                "\"email\": \"thorben.dreier@cofinpro.de\",\n" +
                                "\"firstName\": \"Thorben\",\n" +
                                "\"lastName\": \"Dreier\",\n" +
                                "\"jobTitle\": \"Consultant\",\n" +
                                "\"degree\": \"B.Sc.\",\n" +
                                "\"primaryExpertise\": \"Technologie\",\n" +
                                "\"referenceText\": \"\",\n" +
                                "\"skills\": [\n" +
                                "\"C#\",\n" +
                                "\"Unity\"\n" +
                                "],\n" +
                                "\"phoneNumber\": \"5347959055566\",\n" +
                                "\"birthDate\": \"1994-04-21\"\n" +
                                "}"
                        )
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(201);
    }

    @Test
    @DisplayName("Test succeed with creating profile for other as admin, with valid credentials")
    void lock_a_user_as_admin() throws Exception {
        // Successful login as USER
        MockHttpServletResponse mvcResult = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"username\": \"%s\", \"password\": \"%s\"}", "luis.geyer@cofinpro.de", "mega_gutes_passwort1")))
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(200);

        // Get all users to find id of "Luis"
        mvcResult = mvc.perform(get("/api/v1/users/")
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        JSONArray users = new JSONArray(mvcResult.getContentAsString());
        String outerId = "";
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            String userEmail = user.getString("email");
            if ("luis.geyer@cofinpro.de".equals(userEmail)) {
                outerId = user.getString("id");
            }
        }

        // Lock user "Luis" as admin
        mvcResult = mvc.perform(patch("/api/v1/users/{id}/lock", outerId)
                        .header("authorization",
                                "Bearer " + loginData.getJSONObject("tokens").get("access_token"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(200);

        // Fail login as USER
        mvcResult = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"username\": \"%s\", \"password\": \"%s\"}", "luis.geyer@cofinpro.de", "mega_gutes_passwort1")))
                .andReturn().getResponse();
        assertThat(mvcResult.getStatus()).isEqualTo(401);
    }

    public static Stream<Arguments> validCredentialsProvider() {
        return Stream.of(
                Arguments.of("lennart.rehmer@cofinpro.de", "mega_gutes_passwort1"),
                Arguments.of("luis.geyer@cofinpro.de", "mega_gutes_passwort1"),
                Arguments.of("theresa.riesterer@cofinpro.de", "mega_gutes_passwort1"),
                Arguments.of("markus.kremer@cofinpro.de", "mega_gutes_passwort1"),
                Arguments.of("thorben.dreier@cofinpro.de", "mega_gutes_passwort1")
        );
    }

    private void login() throws Exception {
        // login with default admin user
        MvcResult loginResult = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"username\": \"%s\", \"password\": \"%s\"}", email, password)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.access_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.refresh_token").isNotEmpty())
                .andReturn();
        loginData = new JSONObject(loginResult.getResponse().getContentAsString());
    }

    private void login(String email, String password) throws Exception {
        // login with default admin user
        MvcResult loginResult = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"username\": \"%s\", \"password\": \"%s\"}", email, password)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.access_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.refresh_token").isNotEmpty())
                .andReturn();
        loginData = new JSONObject(loginResult.getResponse().getContentAsString());
    }
}
