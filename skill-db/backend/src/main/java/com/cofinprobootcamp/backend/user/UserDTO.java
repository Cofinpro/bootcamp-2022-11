package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.role.Role;

import java.util.Date;
import java.util.List;

public record UserDTO(
        String email,
        String firstName,
        String lastName,
        Date birthDate,
        int age,
        String jobTitle,
        boolean locked,
        boolean emailConfirmed,
        Role role,
        Profile primaryProfile,
        List<Profile> ownedProfiles,
        List<Profile> editableProfiles
) {

    public UserDTO(User user) {
        this(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getAge(),
                user.getJobTitle(),
                user.isLocked(),
                user.isEmailConfirmed(),
                user.getRole(),
                user.getPrimaryProfile(),
                user.getOwnedProfiles(),
                user.getEditableProfiles());
    }

}
