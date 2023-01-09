package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.role.RoleDirector;
import com.cofinprobootcamp.backend.role.StandardRoles;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;
import com.cofinprobootcamp.backend.user.dto.UserOutDTO;

public class UserDirector {
    public static User CreateInDTOToEntity(UserCreateInDTO userInDTO, String encodedPassword, StandardRoles role) {
        return User.builder()
                .username(userInDTO.email())
                .password(encodedPassword)
                .role(role != null ? role : StandardRoles.USER)
                .build();
    }

    public static UserOutDTO EntityToUserOutDTO(User user) {
        return new UserOutDTO(
                user.getOuterId(), // ID in OutDTO is User entity's id
                user.getUsername(), // email in OutDTO is User entity's username
                user.isLocked(),
                RoleDirector.roleOverviewFromEnumType(user.getRole()), // Create sub-DTO for role
                user.getProfile() != null ? user.getProfile().getId() : null // Get String profileId or null
        );
    }
}
