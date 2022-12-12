package com.cofinprobootcamp.backend.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.JsonObject;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.stream.Stream;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                Arguments.of("admin123", "password", 401)
        );
    }

    @BeforeEach
    void setUp() {
    }

    @ParameterizedTest
    @MethodSource("userCredentialsProvider")
    void logInWithUser(String username, String password, Integer expected) throws Exception {

        MockHttpServletResponse mvcResult = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\" : \""+username+"\" , \"password\" : \""+password+"\"}"))
                .andReturn().getResponse();

        assertThat(mvcResult.getStatus()).isEqualTo(expected);

    }

    @Test
    void rootWhenAuthenticatedThenSaysHelloWorld() throws Exception {
        MvcResult result = mvc.perform(post("/api/v1/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin\", \"password\": \"password\" }"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(asJsonString(content));

        /* this.mvc.perform(get("/api/v1/test")
                        .header("Authorization", "Bearer " + token))
                .andExpect(content().string("hello world!"));*/
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
