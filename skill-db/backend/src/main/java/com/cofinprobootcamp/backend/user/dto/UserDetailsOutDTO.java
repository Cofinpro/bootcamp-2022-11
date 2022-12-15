package com.cofinprobootcamp.backend.user.dto;

import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.role.Role;
import com.cofinprobootcamp.backend.user.User;

import java.time.LocalDate;

public record UserDetailsOutDTO(
        Long id,
        String email,
        String firstName,
        String lastName,
        LocalDate birthDate,
        int age,
        String jobTitle,
        boolean locked,
        Role role,
        Profile profile
) {

    public UserDetailsOutDTO(User user) {
        this(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getAge(),
                user.getJobTitle(),
                user.isLocked(),
                user.getRole(),
                user.getProfile());
    }

}
