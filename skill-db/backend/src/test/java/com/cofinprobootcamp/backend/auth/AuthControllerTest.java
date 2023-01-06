package com.cofinprobootcamp.backend.auth;

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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Import({SecurityConfig.class, TokenService.class, UserDetailsServiceImpl.class})
//@WebMvcTest({AuthController.class})
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    public static Stream<Arguments> userCredentialsProvider() {
        return Stream.of(
                Arguments.of("luis.geyer@cofinpro.de", "mega_gutes_passwort1", 200),
                Arguments.of("admin123", "password", 401),
                Arguments.of("", "password", 401),
                Arguments.of("admin", "", 401),
                Arguments.of("", "", 401),
                Arguments.of(null, null, 401),
                Arguments.of(null, "password", 401),
                Arguments.of("admin", null, 401)
        );
    }

    public static Stream<Arguments> verifySource() {
        return Stream.of(
                Arguments.of(1, "true"),
                Arguments.of(12, "false")
        );
    }

    @BeforeEach
    void setUp() {
    }

    /**
     * Tests if the status-codes of the login-route (where you get your tokens after a successful authentication)
     * are correct
     *
     * @param username
     * @param password
     * @param expected
     * @throws Exception
     */
    @ParameterizedTest
    @MethodSource("userCredentialsProvider")
    @DisplayName("Tests the login with wrong- and correct user-credentials")
    void logInWithUser(String username, String password, Integer expected) throws Exception {

        MockHttpServletResponse mvcResult = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\" : \"" + username + "\" , \"password\" : \"" + password + "\"}"))
                .andReturn().getResponse();

        assertThat(mvcResult.getStatus()).isEqualTo(expected);
    }

    /**
     * Tests if the test-route for authentication works
     *
     * @throws Exception
     */
    @Test
    void rootWhenAuthenticatedThenSaysHelloWorld() throws Exception {
        JSONObject jsonObject = login();

        this.mvc.perform(get("/api/v1/token/test")
                        .header("authorization", "Bearer " + jsonObject.getJSONObject("tokens").get("access_token")))
                .andExpect(content().string("hello world!"));
    }

    /**
     * Tests the verify-token-route. For testing purposes, the valid-time of access- and refresh token has to be changed
     * Access token: 2min
     * Refresh token: 10s
     *
     * @param sleepTime
     * @param expected
     * @throws Exception
     */
    @ParameterizedTest
    @MethodSource("verifySource")
    @DisplayName("Tests the verify-endpoint for the refresh-token. Fails, in case the REFRESH_TOKEN_DURATION_SECONDS is not set to 10 seconds")
    void verifyRefreshToken(Integer sleepTime, String expected) throws Exception {
        JSONObject jsonObject = login();

        TimeUnit.SECONDS.sleep(sleepTime);

        MvcResult result2 = mvc.perform(post("/api/v1/token/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\" : \"" + jsonObject.get("username") + "\" , \"refreshToken\" : \"" + jsonObject.getJSONObject("tokens").get("refresh_token") + "\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected))
                .andReturn();

        System.out.println(result2.getResponse().getContentAsString());

    }
    // TODO change token time to 1h (refresh token) and 2 min (access token)

    /**
     * Tests the refresh token-route
     *
     * @throws Exception
     */
    @Test
    void refreshTokenTest() throws Exception {
        JSONObject jsonObject = login();

        TimeUnit.SECONDS.sleep(1);

        mvc.perform(post("/api/v1/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\" : \"" + jsonObject.get("username") + "\" , \"refreshToken\" : \"" + jsonObject.getJSONObject("tokens").get("refresh_token") + "\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty())
                .andReturn();
    }


    /**
     * Tests the refresh token-route
     * For testing, the REFRESH_TOKEN_DURATION_SECONDS in class ProfileConfiguration have to be set to > 12 seconds (otherwise the test fails)
     *
     * @throws Exception
     */
    @Test
    @DisplayName("For testing, the REFRESH_TOKEN_DURATION_SECONDS in class ProfileConfiguration have to be set to > 12 seconds (otherwise the test fails)")
    void whenRefreshTokenExpiredThenAnswerCodeUnauthorized() throws Exception {
        JSONObject jsonObject = login();

        TimeUnit.SECONDS.sleep(12);

        mvc.perform(post("/api/v1/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\" : \"" + jsonObject.get("username") + "\" , \"refreshToken\" : \"" + jsonObject.getJSONObject("tokens").get("refresh_token") + "\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("This user is not logged in anymore!"))
                .andReturn();

    }

    /**
     * Calls the login-route and tries to authenticate with admin-user
     *
     * @return JSON-object of the username, access- and refresh token
     * @throws Exception
     */
    private JSONObject login() throws Exception {
        MvcResult result = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"luis.geyer@cofinpro.de\", \"password\": \"mega_gutes_passwort1\" }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.access_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.refresh_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("luis.geyer@cofinpro.de"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        return new JSONObject(content);
    }

}
