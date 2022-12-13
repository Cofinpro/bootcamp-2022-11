package com.cofinprobootcamp.backend.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfileConfiguration {

    public static final String FRONTEND_URL = "http://localhost:5173/";
    public static final Integer ACCESS_TOKEN_DURATION_MINUTES = 2;
    public static final Integer REFRESH_TOKEN_DURATION_HOURS = 1;

}