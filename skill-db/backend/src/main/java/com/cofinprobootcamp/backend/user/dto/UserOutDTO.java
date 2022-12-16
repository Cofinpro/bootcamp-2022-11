package com.cofinprobootcamp.backend.user.dto;

import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.role.Role;
import com.cofinprobootcamp.backend.user.User;

import java.time.LocalDate;


public record UserOutDTO(
        String email,
        boolean locked,
        Role role,
        Profile profile
) {
    public UserOutDTO(User user) {
        this(
                user.getEmail(),
                user.isLocked(),
                user.getRole(),
                user.getProfile());
    }
}
