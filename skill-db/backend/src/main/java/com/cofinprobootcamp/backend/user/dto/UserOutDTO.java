package com.cofinprobootcamp.backend.user.dto;

import com.cofinprobootcamp.backend.role.Role;
import com.cofinprobootcamp.backend.user.User;


public record UserOutDTO(
        String email,
        boolean locked,
        Role role,
        long profileId
) {
    // TODO: email zu username ändern?
    public UserOutDTO(User user) {
        this(
                user.getUsername(),
                user.isLocked(),
                user.getRole(),
                user.getProfile().getId());
    }
}
