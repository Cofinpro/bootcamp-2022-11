package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.role.Role;

import java.time.LocalDate;

public record UserDTO(
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

    public UserDTO(User user) {
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
