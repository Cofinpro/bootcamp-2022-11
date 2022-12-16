package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.role.Role;
import com.cofinprobootcamp.backend.user.User;

import java.time.LocalDate;


public record UserOutDTO(
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthDate,
        int age,
        boolean locked,
        Role role,
        Profile profile
) {
    public UserOutDTO(User user) {
        this(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getBirthDate(),
                user.getAge(),
                user.isLocked(),
                user.getRole(),
                user.getProfile());
    }
}
