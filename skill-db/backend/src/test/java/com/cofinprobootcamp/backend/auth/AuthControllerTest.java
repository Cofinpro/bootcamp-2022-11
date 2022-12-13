package com.cofinprobootcamp.backend.auth;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityConfig.class, TokenService.class})
@WebMvcTest({AuthController.class})
public class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    public static Stream<Arguments> userCredentialsProvider() {
        return Stream.of(
                Arguments.of("admin", "password", 200),
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

    public static Stream<Arguments> refreshTokenSource() {
        return Stream.of(
                Arguments.of(1),
                Arguments.of(12)
        );
    }

    @BeforeEach
    void setUp() {
    }

    /**
     * Tests if the status-codes of the login-route (where you get your tokens after a successful authentication)
     * are correct
     * @param username
     * @param password
     * @param expected
     * @throws Exception
     */
    @ParameterizedTest
    @MethodSource("userCredentialsProvider")
    void logInWithUser(String username, String password, Integer expected) throws Exception {

        MockHttpServletResponse mvcResult = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\" : \"" + username + "\" , \"password\" : \"" + password + "\"}"))
                .andReturn().getResponse();

        assertThat(mvcResult.getStatus()).isEqualTo(expected);

    }

    /**
     * Tests if the test-route for authentication works
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
     * Tests the verify-token-route
     * @param sleepTime
     * @param expected
     * @throws Exception
     */
    @ParameterizedTest
    @MethodSource("verifySource")
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
     * @param sleepTime
     * @throws Exception
     */
    @ParameterizedTest
    @MethodSource("refreshTokenSource")
    void refreshTokenTest(Integer sleepTime) throws Exception {
        JSONObject jsonObject = login();

        TimeUnit.SECONDS.sleep(sleepTime);

        if(sleepTime == 1) {
            mvc.perform(post("/api/v1/token/refresh")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"username\" : \"" + jsonObject.get("username") + "\" , \"refreshToken\" : \"" + jsonObject.getJSONObject("tokens").get("refresh_token") + "\"}"))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty())
                    .andReturn();
        } else {
            mvc.perform(post("/api/v1/token/refresh")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"username\" : \"" + jsonObject.get("username") + "\" , \"refreshToken\" : \"" + jsonObject.getJSONObject("tokens").get("refresh_token") + "\"}"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(MockMvcResultMatchers.content().string("This user is not logged in anymore!"))
                    .andReturn();
        }

    }

    /**
     * Calls the login-route and tries to authenticate with admin-user
     * @return JSON-object of the username, access- and refresh token
     * @throws Exception
     */
    private JSONObject login() throws Exception {
        MvcResult result = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin\", \"password\": \"password\" }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.access_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokens.refresh_token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("admin"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        return jsonObject;
    }

}
