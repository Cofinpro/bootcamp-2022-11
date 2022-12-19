package com.cofinprobootcamp.backend.user;

import com.cofinprobootcamp.backend.enums.RolesEnum;
import com.cofinprobootcamp.backend.user.dto.UserCreateInDTO;

public class UserDirector {
    public static User CreateInDTOToEntity(UserCreateInDTO userInDTO) {
        RolesEnum role = userInDTO.userRole() != null ? RolesEnum.fromDisplayName(userInDTO.userRole()) : RolesEnum.USER;
        if (!role.equals(RolesEnum.UNDEFINED)) {
            return User.builder()
                    .email(userInDTO.email())
                    .role(role)
                    .build();
        } else {
            throw new RuntimeException("Invalid user role specified!"); // Custom exception would be desired here
        }
    }
}
