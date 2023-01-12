package com.cofinprobootcamp.backend.auth;

import com.cofinprobootcamp.backend.exceptions.CustomErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        BearerTokenAuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();
        delegate.commence(request, response, authException);

        CustomErrorMessage body = new CustomErrorMessage(
                authException.getMessage(),
                "uri=" + request.getRequestURI(),
                authException
        );
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        out.print(new ObjectMapper().writeValueAsString(body));
        out.flush();
    }
}
