package com.cofinprobootcamp.backend.mails;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MailIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void changeProfileOfOtherUser() {

    }

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
