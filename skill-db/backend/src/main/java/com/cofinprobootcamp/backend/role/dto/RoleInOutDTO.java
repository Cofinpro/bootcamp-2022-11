package com.cofinprobootcamp.backend.role.dto;

import com.cofinprobootcamp.backend.enums.UserRights;
import com.cofinprobootcamp.backend.role.Role;

import java.util.List;

public record RoleInOutDTO(
        Long id,
        String name,
        List<UserRights> userRights
) {

    public RoleInOutDTO(Role role) {
        this(role.getId(), role.getName(), role.getUserRights());
    }
}
