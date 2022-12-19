package com.cofinprobootcamp.backend.user.dto;

import com.cofinprobootcamp.backend.user.User;


public record UserOutDTO(
        String email,
        boolean locked,
        String role,
        long profileId
) {
    public UserOutDTO(User user) {
        this(
                user.getEmail(),
                user.isLocked(),
                user.getRole().toString(),
                user.getProfile().getId());
    }
}
