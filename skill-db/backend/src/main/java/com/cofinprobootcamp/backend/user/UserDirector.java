package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;

public class UserDirector {
    public static User CreateInDTOToEntity(UserCreateInDTO userInDTO, String encodedPassword, Role role) {
        return User.builder()
                .username(userInDTO.email())
                .password(encodedPassword)
                .role(role)
                .build();
    }
}
