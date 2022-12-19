package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.enums.RolesEnum;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;

public class UserDirector {
    public static User CreateInDTOToEntity(UserCreateInDTO userInDTO) {
        return User.builder()
                .email(userInDTO.email())
                .role(userInDTO.userRole() == null ? RolesEnum.USER : userInDTO.userRole())
                .build();
    }
}
